package com.github.mengweijin.vita.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.monitor.domain.bo.LogDataChangeBO;
import com.github.mengweijin.vita.monitor.domain.vo.LogDataChangeVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@AutoMappers({
        @AutoMapper(target = LogDataChangeBO.class),
        @AutoMapper(target = LogDataChangeVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "VT_LOG_DATA_CHANGE", autoResultMap = true)
public class LogDataChangeDO extends BaseEntity {

    /**
     * 业务变更数据库表名称。
     */
    private String tableName;

    /**
     * 业务数据主键 ID
     */
    private Long businessId;

    /**
     * 数据变更前的数据。JSON
     * JSON 映射需要同时开启 @TableName(autoResultMap = true)
     */
    @JsonIgnore
    @TableField(typeHandler = JacksonTypeHandler.class)
    private transient JsonNode beforeData;

    /**
     * 数据变更后的数据。JSON
     * JSON 映射需要同时开启 @TableName(autoResultMap = true)
     */
    @JsonIgnore
    @TableField(typeHandler = JacksonTypeHandler.class)
    private transient JsonNode afterData;

    /**
     * 变更前后的数据。List<DiffModel> JSON。
     * JSON 映射需要同时开启 @TableName(autoResultMap = true)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<DiffModel> changeData;

    /**
     * 人类可阅读的变更数据信息。List<String> JSON
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> readableMessages;
}
