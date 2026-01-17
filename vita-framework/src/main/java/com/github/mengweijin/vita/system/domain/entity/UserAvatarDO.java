package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.UserAvatarBO;
import com.github.mengweijin.vita.system.domain.vo.user.UserAvatarVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@AutoMappers({
        @AutoMapper(target = UserAvatarBO.class),
        @AutoMapper(target = UserAvatarVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_USER_AVATAR")
public class UserAvatarDO extends BaseEntity {

    /**
    * 用户ID
    */
    @NotNull
    private Long userId;

    /**
    * 用户头像，以 Base64 文本存储的大字段。
    */
    private String avatar;
}
