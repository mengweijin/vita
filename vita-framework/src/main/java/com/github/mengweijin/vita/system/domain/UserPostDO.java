package com.github.mengweijin.vita.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
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
@TableName("VT_USER_POST")
public class UserPostDO extends BaseEntity {

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 岗位ID
    */
    private Long postId;
}
