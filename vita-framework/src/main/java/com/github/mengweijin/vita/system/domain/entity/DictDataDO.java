package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.DictDataBO;
import com.github.mengweijin.vita.system.domain.vo.DictDataVO;
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
        @AutoMapper(target = DictDataBO.class),
        @AutoMapper(target = DictDataVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_DICT_DATA")
public class DictDataDO extends BaseEntity {

    /**
     * 字典类型编码ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    /**
     * 字典数据值
     */
    private String val;

    /**
     * 字典数据标签名称
     */
    private String label;

    /**
     * 字典数据标签样式。["primary", "success", "info", "warning", "danger"]
     */
    private String tag;

    /**
     * 展示顺序
     */
    private Integer seq;

    /**
     * 是否已禁用。[Y, N]
     */
    private String disabled;

    /**
     * 备注
     */
    private String remark;
}
