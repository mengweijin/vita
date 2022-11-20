package com.github.mengweijin.vitality.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.vitality.VitalityProperties;
import com.github.mengweijin.vitality.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.util.ServletUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * 自动填充。
 * 注意：如果使用了异步注解或者线程池，而又在新启的线程中用 SpringUtils.getBean(Class) 的方式调用了 mybatis-plus 的 save 方法，
 * 此时，会触发自动填充，而 HttpServletRequest 对象不能跨越线程传递，因此 RequestContextHolder.getRequestAttributes() 总返回 null.
 * 于是，下面的填充 username 的时候就报空指针的错误了。
 *
 * 解决方案：
 * 当使用异步注解或线程池时，不要让 mybatis-plus 触发用户名的自动填充。
 * 此时，如果还需要填充 createBy 和 updateBy 字段，则需要开发者手动给 entity 设置 createBy 和 updateBy 属性的值。
 *
 * @author mengweijin
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    private static final String BASE_ENTITY_CREATE_BY = "createBy";
    private static final String BASE_ENTITY_CREATE_TIME = "createTime";
    private static final String BASE_ENTITY_UPDATE_BY = "updateBy";
    private static final String BASE_ENTITY_UPDATE_TIME = "updateTime";

    @Autowired
    private VitalityProperties vitalityProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictInsertFill(metaObject, BASE_ENTITY_CREATE_TIME, LocalDateTime.class, localDateTime);
            this.strictInsertFill(metaObject, BASE_ENTITY_UPDATE_TIME, LocalDateTime.class, localDateTime);

            // session LOGIN_USER
            String userId = this.getUserId();
            if(StrUtil.isNotBlank(userId)) {
                this.strictInsertFill(metaObject, BASE_ENTITY_CREATE_BY, String.class, userId);
                this.strictInsertFill(metaObject, BASE_ENTITY_UPDATE_BY, String.class, userId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictUpdateFill(metaObject, BASE_ENTITY_UPDATE_TIME, LocalDateTime.class, localDateTime);

            String userId = this.getUserId();
            if(StrUtil.isNotBlank(userId)) {
                this.strictUpdateFill(metaObject, BASE_ENTITY_UPDATE_BY, String.class, userId);
            }
        }
    }

    /**
     * 填充策略
     * MetaObjectHandler提供的默认方法的策略均为：如果属性有值则不覆盖，如果填充值为null则不填充
     * 这里修改为：一律填充
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        metaObject.setValue(fieldName, fieldVal.get());
        return this;
    }

    /**
     * session LOGIN_USER_ID
     * @return username from session key LOGIN_USER_ID
     */
    protected String getUserId() {
        Object username = null;
        if(RequestContextHolder.getRequestAttributes() != null) {
            username = ServletUtils.getSession().getAttribute(vitalityProperties.getLoginUserIdKeyName());
        }
        return username == null ? null : String.valueOf(username);
    }

}
