package com.github.mengweijin.vita.generator.engine;

import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class VelocityTemplateEngine {

    private final VelocityEngine velocityEngine;

    public VelocityTemplateEngine() {
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init();
    }

    public String write(String fileName, String templateContent, Map<String, Object> objectMap) {
        try (StringWriter writer = new StringWriter()) {
            VelocityContext context = new VelocityContext(objectMap);
            velocityEngine.evaluate(context, writer, fileName, templateContent);
            return writer.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(e);
        }
    }
}
