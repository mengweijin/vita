package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("VT_FILE")
public class FileDO extends BaseEntity {

    /**
    * 原始文件名称
    */
    private String name;

    /**
    * 文件后缀
    */
    private String suffix;

    /**
    * 文件存储路径
    */
    private String storagePath;

    /**
     * MD5 码
     */
    private String md5;

    /**
     * 逻辑删除。[Y, N]
     */
    @TableLogic
    protected String deleted;
}
