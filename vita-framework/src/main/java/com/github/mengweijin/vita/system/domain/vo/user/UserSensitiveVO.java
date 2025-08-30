package com.github.mengweijin.vita.system.domain.vo.user;

import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * User Sensitive VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSensitiveVO extends UserVO implements Serializable {

    private String passwordLevel;

    private String citizenId;

    private Set<Long> roleIds;

    private Set<Long> postIds;

    private List<RoleDO> roleList;

    private List<PostDO> postList;
}
