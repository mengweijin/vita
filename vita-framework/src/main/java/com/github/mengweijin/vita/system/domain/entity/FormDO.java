package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.mybatis.typehandler.JsonTypeHandler;
import com.github.mengweijin.vita.system.domain.bo.FormBO;
import com.github.mengweijin.vita.system.domain.vo.FormVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;


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
     * 表单的规则和字段的整体配置数据，通常包含多个字段的配置
     */
    @TableField(value = "rules", typeHandler = JsonTypeHandler.class)
    private List<Object> rules;

    /**
     * 表单的配置数据（例如：布局、尺寸、全局数据等）
     */
    @TableField(value = "options", typeHandler = JsonTypeHandler.class)
    private Map<String, Object> options;

    /**
     * 备注
     */
    private String remark;

}
