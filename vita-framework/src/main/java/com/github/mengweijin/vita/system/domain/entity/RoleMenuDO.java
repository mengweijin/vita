package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.RoleMenuBO;
import com.github.mengweijin.vita.system.domain.vo.RoleMenuVO;
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
        @AutoMapper(target = RoleMenuBO.class),
        @AutoMapper(target = RoleMenuVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_ROLE_MENU")
public class RoleMenuDO extends BaseEntity {

    /**
    * 角色ID
    */
    private Long roleId;

    /**
    * 菜单ID
    */
    private Long menuId;
}
