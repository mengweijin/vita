package com.github.mengweijin.vita.workflow;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.plugin.json.JsonConvertJackson;
import org.springframework.stereotype.Component;

/**
 * Initialize warm-flow library's Jackson ObjectMapper to support Java 8 date/time types.
 *
 * @author mengweijin
 */
@Slf4j
@Component
public class WarmFlowJacksonInitializer {

    @PostConstruct
    public void init() {
        try {
            // Dynamically load warm-flow's JsonConvertJackson class and configure its ObjectMapper
            java.lang.reflect.Field objectMapperField = JsonConvertJackson.class.getDeclaredField("OBJECT_MAPPER");
            objectMapperField.setAccessible(true);

            Object objectMapper = objectMapperField.get(null);
            if (objectMapper instanceof com.fasterxml.jackson.databind.ObjectMapper mapper) {
                // Register JavaTimeModule to support LocalDateTime serialization/deserialization
                mapper.registerModule(new JavaTimeModule());
                log.info("Successfully registered JavaTimeModule to warm-flow's ObjectMapper");
            }
        } catch (NoSuchFieldException e) {
            log.debug("OBJECT_MAPPER field not found in JsonConvertJackson class");
            throw new ServerException("Failed to initialize warm-flow's Jackson ObjectMapper: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.warn("Failed to access OBJECT_MAPPER field in JsonConvertJackson: {}", e.getMessage());
            throw new ServerException("Failed to initialize warm-flow's Jackson ObjectMapper: " + e.getMessage(), e);
        }
    }

}
