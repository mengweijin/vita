package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.ConfigBO;
import com.github.mengweijin.vita.system.domain.vo.ConfigVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 同 application.yml 中的配置，此处配置的使用优先级高于 application.yml 中的配置
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@AutoMappers({
        @AutoMapper(target = ConfigBO.class),
        @AutoMapper(target = ConfigVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_CONFIG")
public class ConfigDO extends BaseEntity {

    /**
     * 配置 key。比如：spring.profiles.active
     */
    private String configKey;

    /**
     * 配置 key 对应的值。比如 ${spring.profiles.active} 的值：dev
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;
}
