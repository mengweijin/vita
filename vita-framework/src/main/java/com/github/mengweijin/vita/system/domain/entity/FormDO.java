package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.FormBO;
import com.github.mengweijin.vita.system.domain.vo.FormVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 流程表单表
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@AutoMappers({
        @AutoMapper(target = FormBO.class),
        @AutoMapper(target = FormVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_FORM")
public class FormDO extends BaseEntity {

    /**
     * 父 ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单类型（静态表单、动态表单）。关联字典：vt_form_type
     */
    private String type;

    /**
     * 静态表单路由路径；或动态表单 ID
     */
    private String formPath;

    /**
     * 备注
     */
    private String remark;
}
