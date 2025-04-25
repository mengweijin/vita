package com.github.mengweijin.vita.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
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
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_MENU")
public class MenuDO extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型。vt_menu_type: { DIR=目录；MENU=菜单; BTN=按钮; IFRAME=内嵌页面；URL=外链页面；}
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 权限。[*:*:*]
     */
    private String permission;

    /**
     * 路由名称。例如：SystemUser
     */
    private String routeName;

    /**
     * 路由路径。例如：/system/user
     */
    private String routePath;

    /**
     * 组件路径。例如：system/user/UserList.vue 或者一个外部 url
     */
    private String component;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否禁用。[Y, N]
     */
    private String disabled;

}
