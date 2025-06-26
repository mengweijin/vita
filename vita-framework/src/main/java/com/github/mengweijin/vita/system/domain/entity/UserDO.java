package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
@TableName("VT_USER")
public class UserDO extends BaseEntity {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
    * 用户登录名（字母数字下划线）
    */
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
     * 密码加盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 密码强度
     */
    @JsonIgnore
    private String passwordLevel;

    /**
     * 密码修改时间
     */
    @JsonIgnore
    private LocalDateTime passwordChangeTime;

    /**
    * 身份证号
    */
    @JsonIgnore
    private String citizenId;

    /**
    * 性别。关联数据字典：vt_user_gender
    */
    private String gender;

    /**
    * 电子邮箱
    */
    private String email;

    /**
    * 移动电话
    */
    private String mobile;

    /**
     * TOTP 动态口令验证密钥
    */
    @JsonIgnore
    private String totp;

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
