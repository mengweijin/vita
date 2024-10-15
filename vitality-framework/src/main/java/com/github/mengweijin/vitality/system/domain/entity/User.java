package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.validator.UsernameDuplicateBusinessCheckRule;
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
@TableName("VTL_USER")
public class User extends BaseEntity {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
    * 用户登录名（字母数字下划线）
    */
    @BusinessCheck(clazz = UsernameDuplicateBusinessCheckRule.class, groups = {Group.Create.class})
    private String username;

    /**
    * 用户昵称
    */
    private String nickname;

    /**
    * 登录密码
    */
    @JsonIgnore
    private String password;

    /**
    * 身份证号
    */
    @Sensitive(strategy = ESensitiveStrategy.ID_CARD)
    private String idCard;

    /**
    * 性别。关联数据字典：user_gender
    */
    private String gender;

    /**
    * 电子邮箱
    */
    @Sensitive(strategy = ESensitiveStrategy.EMAIL)
    private String email;

    /**
    * 移动电话
    */
    @Sensitive(strategy = ESensitiveStrategy.MOBILE_PHONE)
    private String mobile;

    /**
    * 2FA（two-factor authentication）双重身份验证密钥
    */
    @JsonIgnore
    private String secretKey;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 逻辑删除。[Y, N]
    */
    @TableLogic
    private String deleted;

    /**
     * 备注
     */
    private String remark;
}
