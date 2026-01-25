package com.github.mengweijin.vita.framework.jackson.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author mengweijin
 * @since 2025/12/21
 */
@Data
@AllArgsConstructor
public abstract class AbstractObjectMapperWrapper {

    private ObjectMapper objectMapper;

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public <T extends JsonNode> T valueToTree(Object fromValue) {
        try {
            return objectMapper.valueToTree(fromValue);
        } catch (IllegalArgumentException e) {
            throw new ServerException(e);
        }
    }
}
