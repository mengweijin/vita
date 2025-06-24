package com.github.mengweijin.vita.system.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author mengweijin
 */
@Data
public class RolePermissionBO implements Serializable {

    @NotNull
    private Long roleId;

    private Set<Long> menuIds;
}
