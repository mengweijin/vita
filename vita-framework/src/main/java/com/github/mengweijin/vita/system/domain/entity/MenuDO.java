package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.MenuBO;
import com.github.mengweijin.vita.system.domain.vo.MenuVO;
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
        @AutoMapper(target = MenuBO.class),
        @AutoMapper(target = MenuVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_MENU")
public class MenuDO extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型。vt_menu_type: { DIR=目录；MENU=菜单; BTN=按钮; URL=外链页面；}
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
     * 路由路径（如：/system/user）或者一个完整的 url 地址
     */
    private String url;

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
