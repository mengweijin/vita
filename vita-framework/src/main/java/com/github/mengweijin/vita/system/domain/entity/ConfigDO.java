package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
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
@TableName("VT_CONFIG")
public class ConfigDO extends BaseEntity {

    /**
    * 名称
    */
    private String name;

    /**
    * 编码
    */
    private String code;

    /**
    * 值
    */
    private String val;

    /**
    * 备注
    */
    private String remark;
}
