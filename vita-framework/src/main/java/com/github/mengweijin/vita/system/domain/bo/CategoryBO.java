package com.github.mengweijin.vita.system.domain.bo;

import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.validator.rule.CategoryCodeDuplicateCheckRule;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Config BO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryBO extends CategoryDO {

    @NotBlank(groups = Group.Create.class)
    @BusinessCheck(groups = Group.Create.class, checkRule = CategoryCodeDuplicateCheckRule.class)
    private String code;

    @NotBlank
    private String name;
}
