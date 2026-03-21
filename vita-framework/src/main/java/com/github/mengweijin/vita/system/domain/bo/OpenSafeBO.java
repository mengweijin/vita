package com.github.mengweijin.vita.system.domain.bo;

import com.github.mengweijin.vita.framework.enums.dict.ESafeMode;
import com.github.mengweijin.vita.framework.validator.annotation.Dict;
import com.github.mengweijin.vita.framework.enums.EDictType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 开启二级认证 BO
 * @author mengweijin
 * @since 2026/3/14
 */
@Data
public class OpenSafeBO {

    /**
     * 二级认证模式，关联字典 vt_safe_mode
     * {@link ESafeMode}
     */
    @NotBlank
    @Dict(dictType = EDictType.VT_SAFE_MODE)
    private String safeMode;

    @NotBlank
    private String value;

}
