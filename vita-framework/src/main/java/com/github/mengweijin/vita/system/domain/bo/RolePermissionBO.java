package com.github.mengweijin.vita.system.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author mengweijin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionBO implements Serializable {

    @NotNull
    private Long roleId;

    private Set<Long> menuIds;
}
