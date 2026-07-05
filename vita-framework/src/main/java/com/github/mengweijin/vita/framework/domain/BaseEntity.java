package com.github.mengweijin.vita.framework.domain;

import cn.hutool.v7.core.date.DateFormatPool;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 逻辑删除、乐观锁两个字段可根据需要，在子类中自行添加。这里只提供一个文档示例。
 * </p>
 *
 * <p>
 * 逻辑删除。
 *
 * @author Meng Wei Jin
 * @TableLogic protected String deleted;
 * </p>
 *
 * <p>
 * 乐观锁。
 * @Version protected Long version;
 * </p>
 * @since 2019-07-28
 **/
@Data
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    protected Long id;

    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
    protected Long createBy;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    protected Long updateBy;

    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "createBy")
    private String createByName;

    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "updateBy")
    private String updateByName;

    /**
     * 按创建时间查询的开始时间
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime startCreateTime;

    /**
     * 按创建时间查询的结束时间
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime endCreateTime;

    /**
     * 按更新时间查询的开始时间
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime startUpdateTime;

    /**
     * 按更新时间查询的结束时间
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime endUpdateTime;
}
