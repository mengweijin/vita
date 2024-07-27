package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单-角色关联表
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_MENU_ROLE_RLT")
public class MenuRoleRltDO extends BaseEntity {

    /**
     * 菜单ID
     */
    @TableField("MENU_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}
