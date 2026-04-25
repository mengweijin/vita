package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.validator.annotation.Check;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.DictTypeBO;
import com.github.mengweijin.vita.system.domain.vo.DictTypeVO;
import com.github.mengweijin.vita.system.validator.rule.DictTypeCodeDuplicateCheckRule;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import jakarta.validation.constraints.NotBlank;
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
        @AutoMapper(target = DictTypeBO.class),
        @AutoMapper(target = DictTypeVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_DICT_TYPE")
public class DictTypeDO extends BaseEntity {

    /**
     * 字典名称
     */
    @NotBlank
    private String name;

    /**
     * 字典类型编码。
     */
    @NotBlank(groups = Group.Create.class)
    @Check(groups = Group.Create.class, rules = DictTypeCodeDuplicateCheckRule.class)
    private String code;

    /**
     * 备注
     */
    private String remark;
}
