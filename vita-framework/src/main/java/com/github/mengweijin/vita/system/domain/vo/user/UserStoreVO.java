package com.github.mengweijin.vita.system.domain.vo.user;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
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

    @Translation(translateType = ETranslateType.DEPT_ID_TO_NAME, field = "deptId")
    private String deptName;

    @Translation(translateType = ETranslateType.USER_ID_TO_AVATAR, field = "id")
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
