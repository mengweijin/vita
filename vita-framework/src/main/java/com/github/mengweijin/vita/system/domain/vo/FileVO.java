package com.github.mengweijin.vita.system.domain.vo;

import com.github.mengweijin.vita.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vita.framework.jackson.sensitive.Sensitive;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * File VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileVO extends FileDO {

    @Sensitive(strategy = ESensitiveStrategy.PREFIX)
    private String storagePath;
}
