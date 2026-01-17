package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.NoticeBO;
import com.github.mengweijin.vita.system.domain.vo.NoticeVO;
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
        @AutoMapper(target = NoticeBO.class),
        @AutoMapper(target = NoticeVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_NOTICE")
public class NoticeDO extends BaseEntity {

    /**
    * 标题
    */
    private String title;

    /**
    * 内容
    */
    private String description;

    /**
    * 是否已发布。[Y, N]
    */
    private String released;
}
