package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.UserRoleBO;
import com.github.mengweijin.vita.system.domain.vo.user.UserRoleVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
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
        @AutoMapper(target = UserRoleBO.class),
        @AutoMapper(target = UserRoleVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_USER_ROLE")
public class UserRoleDO extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
