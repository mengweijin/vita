package com.github.mengweijin.vita.system.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
public class UserRoleBO {

    /**
     * 用户 ID
     */
    @NotNull
    private Long userId;

    /**
     * 角色
     */
    private List<Long> roleIds = new ArrayList<>();

}
