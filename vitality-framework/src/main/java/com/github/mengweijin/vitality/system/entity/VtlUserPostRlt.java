package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户-岗位关联表
 *
 * @author mengweijin
 * @since 2023-06-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_USER_POST_RLT")
public class VtlUserPostRlt extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 岗位ID
     */
    @TableField("POST_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;

}
