package com.github.mengweijin.vita.system.domain.bo;

import cn.hutool.v7.core.regex.RegexPool;
import com.github.mengweijin.vita.framework.validator.annotation.CharsetLength;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class UserBasicInformationBO implements Serializable {

    private Long id;

    /**
     * 用户昵称
     */
    @NotBlank
    @CharsetLength(min = 1, max = 30, message = "{user.nickname.pattern}")
    private String nickname;

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

}
