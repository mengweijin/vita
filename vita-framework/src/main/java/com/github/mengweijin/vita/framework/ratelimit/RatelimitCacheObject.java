package com.github.mengweijin.vita.framework.ratelimit;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2025/12/14
 */
@Data
public class RatelimitCacheObject implements Serializable {

    private List<LocalDateTime> list = new ArrayList<>();

    public void add(LocalDateTime localDateTime) {
        list.add(localDateTime);
    }
}
