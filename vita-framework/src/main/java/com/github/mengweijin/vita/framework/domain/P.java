package com.github.mengweijin.vita.framework.domain;

import com.github.mengweijin.vita.framework.jackson.wrapper.AbstractObjectMapperWrapper;
import com.github.mengweijin.vita.framework.jackson.wrapper.SensitiveObjectMapperWrapper;
import com.github.mengweijin.vita.framework.jackson.wrapper.SpringObjectMapperWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class P {

    public static AbstractObjectMapperWrapper getObjectMapperWrapper() {
        return SpringObjectMapperWrapper.getInstance();
    }

    public static AbstractObjectMapperWrapper getSensitiveObjectMapperWrapper() {
        return SensitiveObjectMapperWrapper.getInstance();
    }

}
