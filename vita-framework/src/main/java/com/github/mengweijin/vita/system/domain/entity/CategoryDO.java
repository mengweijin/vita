package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.CategoryBO;
import com.github.mengweijin.vita.system.domain.vo.CategoryVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 */
@AutoMappers({
        @AutoMapper(target = CategoryBO.class),
        @AutoMapper(target = CategoryVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_CATEGORY")
public class CategoryDO extends BaseEntity {

    /**
     * PARENT ID
     */
    private Long parentId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 展示顺序
     */
    private Integer seq;

    /**
     * 是否已禁用。[Y, N]
     */
    private String disabled;
}
