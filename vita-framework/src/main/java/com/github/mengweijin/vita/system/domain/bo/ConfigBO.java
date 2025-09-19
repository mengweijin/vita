package com.github.mengweijin.vita.system.domain.bo;

import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.validator.rule.ConfigKeyDuplicateCheckRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * Config VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigBO extends ConfigDO {

    /**
     * 配置管理默认支持 spring boot 中所有可以在 application.yml 中配置的参数。
     * 胡乱配置会导致应用出现不可预测的异常情况，因此为了安全起见，这里的配置项规定必须以 vita. 开头。
     */
    @NotBlank(groups = {Group.Create.class, Group.Update.class})
    @Length(max = 255)
    @Pattern(regexp = "^vita\\..+$", message = "{config.key.pattern}")
    @BusinessCheck(groups = {Group.Create.class, Group.Update.class}, checkRule = ConfigKeyDuplicateCheckRule.class)
    private String configKey;

    @NotBlank(groups = {Group.Create.class, Group.Update.class})
    @Length(max = 2000)
    private String configValue;
}
