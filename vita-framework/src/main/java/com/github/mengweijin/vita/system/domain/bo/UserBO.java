package com.github.mengweijin.vita.system.domain.bo;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.framework.validator.annotation.CharsetLength;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.validator.rule.UsernameDuplicateCheckRule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.hutool.core.regex.RegexPool;

import java.util.ArrayList;
import java.util.List;

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
public class UserBO extends BaseEntity {

    /**
     * 部门ID
     */
    @NotNull
    private Long deptId;

    /**
     * 用户登录名（字母数字下划线）
     */
    @NotBlank(groups = {Group.Create.class})
    @Pattern(regexp = RegexPool.GENERAL, message = "{user.username.pattern}")
    @BusinessCheck(groups = {Group.Create.class}, checkRule = UsernameDuplicateCheckRule.class)
    private String username;

    /**
     * 用户昵称
     */
    @NotBlank
    @CharsetLength(min = 1, max = 30, message = "{user.nickname.pattern}")
    private String nickname;

    /**
     * 身份证号
     */
    @Pattern(regexp = RegexPool.CITIZEN_ID, message = "{user.citizenId.pattern}")
    private String citizenId;

    /**
     * 性别。关联数据字典：user_gender
     */
    private String gender;

    /**
     * 电子邮箱
     */
    @Email
    private String email;

    /**
     * 移动电话
     */
    @Pattern(regexp = RegexPool.MOBILE, message = "{user.mobile.pattern}")
    private String mobile;

    /**
     * 是否禁用。[Y, N]
     */
    private String disabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色
     */
    private List<Long> roleIds = new ArrayList<>();

    /**
     * 岗位
     */
    private List<Long> postIds = new ArrayList<>();
}
