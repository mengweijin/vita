package com.github.mengweijin.vita.framework.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import org.apache.ibatis.reflection.MetaObject;
import org.dromara.hutool.core.func.LambdaUtil;
import org.dromara.hutool.core.func.SerFunction;

import java.time.LocalDateTime;

/**
 * 自动填充。
 * 注意：如果使用了异步注解或者线程池，而又在新启的线程中用 SpringUtils.getBean(Class) 的方式调用了 mybatis-plus 的 save 方法，
 * 此时，会触发自动填充，而 HttpServletRequest 对象不能跨越线程传递，因此 RequestContextHolder.getRequestAttributes() 总返回 null.
 * 于是，下面的填充 username 的时候就报空指针的错误了。
 * 解决方案：
 * 当使用异步注解或线程池时，不要让 mybatis-plus 触发用户名的自动填充。
 * 此时，如果还需要填充 createBy 和 updateBy 字段，则需要开发者手动给 entity 设置 createBy 和 updateBy 属性的值。
 *
 * @author mengweijin
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_BY = LambdaUtil.getFieldName((SerFunction<BaseEntity, Long>)BaseEntity::getCreateBy);
    private static final String UPDATE_BY = LambdaUtil.getFieldName((SerFunction<BaseEntity, Long>)BaseEntity::getUpdateBy);

    private static final String CREATE_TIME = LambdaUtil.getFieldName((SerFunction<BaseEntity, LocalDateTime>)BaseEntity::getCreateTime);
    private static final String UPDATE_TIME = LambdaUtil.getFieldName((SerFunction<BaseEntity, LocalDateTime>)BaseEntity::getUpdateTime);

    @Override
    public void insertFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, localDateTime);
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, localDateTime);

            // session LOGIN USER
            Long userId = LoginHelper.getLoginUserIdQuietly();
            if(userId != null) {
                this.strictInsertFill(metaObject, CREATE_BY, Long.class, userId);
                this.strictInsertFill(metaObject, UPDATE_BY, Long.class, userId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, localDateTime);

            Long userId = LoginHelper.getLoginUserIdQuietly();
            if(userId != null) {
                this.strictUpdateFill(metaObject, UPDATE_BY, Long.class, userId);
            }
        }
    }

}
