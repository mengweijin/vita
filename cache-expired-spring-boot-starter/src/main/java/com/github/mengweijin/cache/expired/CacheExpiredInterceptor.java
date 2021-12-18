package com.github.mengweijin.cache.expired;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;

/**
 * Question: is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
 * 如果遇到这个问题，参考以下思路：
 * 实现 ApplicationContextAware 接口和 @Autowired 的区别：
 * 注解的方式的依赖注入发生在当前 bean 实例化完成后，然后根据 @Autowired 实例化依赖的 bean。
 * 假如你自定义了一个后置处理器（实现了 BeanPostProcessor 接口），然而，你又在这个后置处理器中需要依赖一些 bean 来完成功能，
 * 这个时候就体现出来实现 ApplicationContextAware 接口和 @Autowired 的区别。
 * 1. 如果你在后置处理器中使用 @Autowired 注入 bean, 这就导致注入的 bean 会在你这个后置处理器中提前被实例化，而我们知道， AOP 就是通过后置处理器实现的，
 * 一旦注入的 bean 在这里提前实例化，就会导致一些其他的后置处理器无法对注入的 bean 做后置处理了，于是就出现了上面的问题。
 * 2. 如果你在后置处理器中使用实现 ApplicationContextAware 接口的方式来获取 bean，那么就不会导致注入的 bean 会在你这个后置处理器中提前被实例化，
 * 这样就避免了使用 @Autowired 带来的问题。
 * 3. @Bean 和 @Autowired 中的依赖注入同理，所有，如果出现这个问题，在 @Bean 中也不要依赖其他 bean，可以改为实现 Aware 接口的方式。
 * 当然也可以设置 @Configuration(proxyBeanMethods = false) 不让这个配置类中的所有 @Bean 被代理。
 * 总结：
 * 如果普通使用，只做业务处理的 Controller, Service 等，使用 @Autowired 方式很方便。
 * 如果要对 Spring 功能做扩展，优先使用各种 Aware 接口来获取 bean 可以避免一些问题。
 * 如果以上两个不能解决问题，使用 @Configuration(proxyBeanMethods = false) 来阻止配置类中的所有 @Bean 被代理。
 * <p>
 * <p>
 * 如果需要 @CacheExpired 在 @Cacheable 之前执行, @EnableCaching 中指定的 order 需要小于配置中的 order。
 * 如果配置是 @CacheExpired 在 @Cacheable 之后执行，而 @Cacheable 的业务逻辑是如果缓存命中，就不进行火炬传递了，也就是说此时 @CacheExpired 不会执行。
 * 此时需要在 CacheExpiredInterceptor 中处理当使用存储到磁盘的缓存管理器时，应用重启后，已经缓存的数据无法使其缓存过期的问题。
 * 此时建议当使用存储到磁盘的缓存管理器时，设置好缓存过期时间，达到双重清理缓存的目的。这样就能同时兼容使用保存在内存或者磁盘的缓存管理器。
 *
 * @author mengweijin
 */
@Slf4j
public class CacheExpiredInterceptor implements MethodInterceptor {

    @Getter
    @Setter
    private CacheExpiredOperationSource cacheExpiredOperationSource;

    @Getter
    @Setter
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("Enter CacheExpiredInterceptor");
        Method method = invocation.getMethod();
        CacheExpired cacheExpired = method.getAnnotation(CacheExpired.class);
        if (cacheExpired.expire() > 0 || CronExpression.isValidExpression(cacheExpired.cron())) {
            CacheExpiredTask cacheExpiredTask = null;
            if (method.isAnnotationPresent(Cacheable.class)) {
                cacheExpiredTask = cacheExpiredOperationSource.parseCacheableAnnotation(invocation);
            } else if (method.isAnnotationPresent(CachePut.class)) {
                cacheExpiredTask = cacheExpiredOperationSource.parseCachePutAnnotation(invocation);
            } else {
                // do nothing
            }

            // 不为空，才进行下面的操作。也无需把 cacheExpiredTask 维护到一个容器中来避免重复任务，因为不可能发生。
            // 原因是配置了 cacheExpireAdvisor 的 order 为最低优先级，即 cacheExpireAdvisor 在 cacheAdvisor 之后执行
            // 那么当执行 cacheAdvisor 命中缓存时，就不会进行火炬传递的，因此不会执行 cacheExpireAdvisor 了。
            // 所有就不会给线程池中提交多个相同的 cacheExpiredTask 任务。
            if (cacheExpiredTask != null) {
                this.submitExpireTask(cacheExpired, cacheExpiredTask);
            }
        } else {
            String message = "Invalid @CacheExpired parameters. expire=" + cacheExpired.expire()
                    + ", chronoUnit=" + cacheExpired.chronoUnit()
                    + ", cron=" + cacheExpired.cron();
            log.warn(message);
        }

        return invocation.proceed();
    }

    public void submitExpireTask(CacheExpired cacheExpired, CacheExpiredTask cacheExpiredTask) {
        if (cacheExpired.expire() > 0) {
            log.debug("Submitted a delay execute expire cache task!");
            this.submitDelayTask(cacheExpired, cacheExpiredTask);
        } else if (CronExpression.isValidExpression(cacheExpired.cron())) {
            log.debug("Submitted a cron execute expire cache task!");
            this.submitCronTask(cacheExpired, cacheExpiredTask);
        }
    }

    private ScheduledFuture<?> submitDelayTask(CacheExpired cacheExpired, CacheExpiredTask cacheExpiredTask) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpiredTask), Instant.now().plus(cacheExpired.expire(), cacheExpired.chronoUnit()));
    }

    /**
     * 1. 判断 cron 是否合法可以使用：CronExpression.isValidExpression(cron)；
     * 2. 也可以直接 CronExpression.parse(cron)；不正确的话会抛异常。
     * 3. CronExpression cronExpression = CronExpression.parse(cron);
     * 4. LocalDateTime nextExecuteTime = cronExpression.next(LocalDateTime.now());
     *
     * @param cacheExpired CacheExpired
     */
    private ScheduledFuture<?> submitCronTask(CacheExpired cacheExpired, CacheExpiredTask cacheExpiredTask) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpiredTask), new CronTrigger(cacheExpired.cron()));
    }

    private void deleteCache(CacheExpiredTask cacheExpiredTask) {
        CacheManager cacheManager = cacheExpiredTask.getCacheManager();
        cacheExpiredTask.getCacheNames().forEach(cacheName -> {
            // 从 cacheManager 中移除
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.evictIfPresent(cacheExpiredTask.getCacheKey());
            }
        });
    }

}
