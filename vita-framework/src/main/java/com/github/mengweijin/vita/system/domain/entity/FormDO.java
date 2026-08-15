package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "VT_FORM", autoResultMap = true)
public class FormDO extends BaseEntity {

    /**
     * 表单名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 备注
     */
    private String remark;

}
