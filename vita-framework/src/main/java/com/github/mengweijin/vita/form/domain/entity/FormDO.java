package com.github.mengweijin.vita.form.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.form.domain.bo.FormBO;
import com.github.mengweijin.vita.form.domain.vo.FormVO;
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
    private Long parentId;

    /**
    * 表单名称
    */
    private String name;

    /**
    * 表单类型。关联字典：vt_form_type
    */
    private String type;

    /**
    * 静态表单路由路径
    */
    private String staticFormPath;

    /**
     * 动态表单 ID
     */
    private Long dynamicFormId;

    /**
     * 备注
     */
    private String remark;
}
