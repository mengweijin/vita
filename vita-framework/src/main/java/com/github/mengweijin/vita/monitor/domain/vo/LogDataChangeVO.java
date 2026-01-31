package com.github.mengweijin.vita.monitor.domain.vo;

import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogDataChangeVO extends LogDataChangeDO {

    private List<String> readableMessages;
}
