package com.github.mengweijin.vita.system.domain.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * User Store VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@EqualsAndHashCode
@Data
public class UserStoreVO {

    private Long id;

    /**
     * 用户登录名（字母数字下划线）
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 部门 ID
     */
    private Long deptId;

    private String deptName;

    private String avatar;

    /**
     * 用户 token
     */
    private String token;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户权限
     */
    private List<String> permissions;

}
