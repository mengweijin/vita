package com.github.mengweijin.vita.system.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * User Sensitive VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@EqualsAndHashCode
@Data
public class UserSensitiveVO implements Serializable {

    private String passwordLevel;

    private String citizenId;
}
