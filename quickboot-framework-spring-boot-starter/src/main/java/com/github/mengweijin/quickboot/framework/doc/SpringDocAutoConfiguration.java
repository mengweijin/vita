package com.github.mengweijin.quickboot.framework.doc;

import com.github.mengweijin.quickboot.framework.AppInfoProperties;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
@Profile({"!prod"})
@ConditionalOnClass(GroupedOpenApi.class)
@Configuration
public class SpringDocAutoConfiguration {

    @Autowired
    private AppInfoProperties appInfoProperties;

    /**
     * 可以多添加几个这样的 bean,
     * 按照 .pathsToMatch(Constants.ALL_PATTERN)
     * 或者 .packagesToScan("com.github.mengweijin")
     * 来分组展示。
     */
    @Bean
    public GroupedOpenApi applicationAllApi() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch(Constants.ALL_PATTERN)
                .addOpenApiCustomiser(openApi ->
                        openApi.info(new Info().title("All APIs")
                        .version(appInfoProperties.getVersion()))
                )
                .build();
    }
}
