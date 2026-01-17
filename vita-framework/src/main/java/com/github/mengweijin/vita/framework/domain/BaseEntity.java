package com.github.mengweijin.vita.framework.domain;

import cn.hutool.v7.core.date.DateFormatPool;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import lombok.Data;
import org.apache.commons.lang3.builder.DiffExclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *     逻辑删除、乐观锁两个字段可根据需要，在子类中自行添加。这里只提供一个文档示例。
 * </p>
 *
 * <p>
 * 逻辑删除。
 * @TableLogic
 * protected String deleted;
 * </p>
 *
 * <p>
 * 乐观锁。
 * @Version
 * protected Long version;
 * </p>
 *
 * @author Meng Wei Jin
 * @since 2019-07-28
 **/
@Data
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    protected Long id;

    @DiffExclude
    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @DiffExclude
    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
    protected Long createBy;

    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    protected Long updateBy;

    @DiffExclude
    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "createBy")
    private String createByName;

    @DiffExclude
    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "updateBy")
    private String updateByName;

    /**
     * 查询关键字
     */
    @DiffExclude
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String keywords;

    /**
     * 按创建时间查询的开始时间
     */
    @DiffExclude
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime startCreateTime;

    /**
     * 按创建时间查询的结束时间
     */
    @DiffExclude
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime endCreateTime;

    /**
     * 按更新时间查询的开始时间
     */
    @DiffExclude
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime startUpdateTime;

    /**
     * 按更新时间查询的结束时间
     */
    @DiffExclude
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime endUpdateTime;
}
