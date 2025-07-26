package com.github.mengweijin.vita.framework.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import cn.hutool.v7.extra.spring.SpringUtil;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class P {

    public static ObjectMapper objectMapper() {
        return SpringUtil.getBean(ObjectMapper.class);
    }


    public static String writeValueAsString(Object value) {
        try {
            return objectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper().readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }
}
