package com.github.mengweijin.vita.framework.properties;

import ch.qos.logback.classic.Level;
import com.github.mengweijin.vita.framework.constant.Const;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.File;

/**
 * 加@Validated可对配置属性进行数据格式校验
 * havingValue: 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * matchIfMissing: 缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
 * <p>
 * ConfigurationProperties 注解主要用来把properties配置文件转化为bean来使用。
 * VitaAutoConfiguration 中的 EnableConfigurationProperties 注解的作用是使@ConfigurationProperties注解生效。
 * 如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的。
 * <p>
 * 1. spring cloud 使用 @RefreshScope + @Value 方式可以动态更新。
 * 2. 直接使用 @ConfigurationProperties，并不需要加 @RefreshScope 就能实现动态更新。
 * - @ConfigurationProperties实现动态刷新的原理：
 * - @ConfigurationProperties有 {@link org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder} 这个监听器，
 *         监听着 {@link org.springframework.cloud.context.environment.EnvironmentChangeEvent} 事件。
 *         当发生 EnvironmentChangeEvent 事件后，会重新构造原来的加了 @ConfigurationProperties 注解的 Bean 对象。
 *         这个是 Spring Cloud 的默认实现。
 *
 * @author Meng Wei Jin
 **/
@Data
@Validated
@ConfigurationProperties(prefix = "vita")
public class VitaProperties {

    /**
     * ${vita.upload-path}
     * 文件上传默认位置。
     */
    @NotBlank
    private String uploadPath = Const.PROJECT_DIR + "uploads" + File.separator;

    // region ----- vita.login

    /**
     * ${vita.login-captcha-enabled}
     */
    @NotNull
    private Boolean loginCaptchaEnabled = true;

    /**
     * ${vita.login-otp-enabled}
     */
    @NotNull
    private Boolean loginOtpEnabled = false;

    // endregion

    /**
     * ${vita.log-record-level}
     */
    @NotBlank
    private String logRecordLevel = Level.ERROR.levelStr;

    /**
     * ${vita.role-code-for-admin}
     */
    private String roleCodeForAdmin;

    @Valid
    private UserProperties user = new UserProperties();

    @Data
    public static class UserProperties {

        /**
         * ${vita.user.default-role-code}
         */
        private String defaultRoleCode;

        /**
         * ${vita.user.default-password}
         */
        @NotBlank
        private String defaultPassword = "aday.fun";

        /**
         * ${vita.user.password-change-interval}
         */
        @NotNull
        @Min(-1)
        @Max(Integer.MAX_VALUE)
        private Integer passwordChangeInterval = 90;

        private String defaultPasswordNew = "";

        private String defaultPasswordNewConfig = "";

    }
}
