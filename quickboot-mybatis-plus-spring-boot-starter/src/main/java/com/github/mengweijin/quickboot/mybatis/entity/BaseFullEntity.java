package com.github.mengweijin.quickboot.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Meng Wei Jin
 * @date Create in 2019-07-28 15:18
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseFullEntity extends BaseEntity {

    /**
     * 逻辑删除。不是每张表都需要
     * logically deleted
     */
    @TableLogic
    protected Integer deleted;

    /**
     * 乐观锁。不是每张表都需要
     */
    @Version
    protected Long version;
}
