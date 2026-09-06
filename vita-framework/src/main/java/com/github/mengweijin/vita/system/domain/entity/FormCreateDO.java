package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.mybatis.typehandler.JsonTypeHandler;
import com.github.mengweijin.vita.system.domain.bo.FormCreateBO;
import com.github.mengweijin.vita.system.domain.vo.FormCreateVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 表单管理表
 *
 * @author mengweijin
 * @since 2026-08-22
 */
@AutoMappers({
        @AutoMapper(target = FormCreateBO.class),
        @AutoMapper(target = FormCreateVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "VT_FORM_CREATE", autoResultMap = true)
public class FormCreateDO extends BaseEntity {

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单编码
     */
    private String code;

    /**
     * 表单的规则和字段的整体配置数据，通常包含多个字段的配置
     */
    @TableField(value = "rules", typeHandler = JsonTypeHandler.class)
    private ArrayList<Object> rules;

    /**
     * 表单的配置数据（例如：布局、尺寸、全局数据等）
     */
    @TableField(value = "options", typeHandler = JsonTypeHandler.class)
    private HashMap<String, Object> options;

    /**
     * 备注
     */
    private String remark;
}
