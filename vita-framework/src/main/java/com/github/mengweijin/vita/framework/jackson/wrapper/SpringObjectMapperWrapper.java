package com.github.mengweijin.vita.framework.jackson.wrapper;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author mengweijin
 * @since 2025/12/21
 */
public class SpringObjectMapperWrapper extends AbstractObjectMapperWrapper {

    private static volatile SpringObjectMapperWrapper instance;

    private SpringObjectMapperWrapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public static SpringObjectMapperWrapper getInstance() {
        if (instance == null) {
            synchronized (SpringObjectMapperWrapper.class) {
                if (instance == null) {
                    ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
                    instance = new SpringObjectMapperWrapper(objectMapper);
                }
            }
        }
        return instance;
    }
}
