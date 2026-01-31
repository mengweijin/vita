package com.github.mengweijin.vita.framework.springdoc;

import com.github.mengweijin.vita.framework.properties.ApplicationProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@AllArgsConstructor
@Configuration
public class SpringDocConfig {

    private ApplicationProperties applicationProperties;

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI openApi() {
        String applicationName = applicationProperties.getName();
        String applicationVersion = applicationProperties.getVersion();
        String applicationAuthor = applicationProperties.getAuthor();
        String applicationGithub = applicationProperties.getGithub();
        String applicationEmail = applicationProperties.getEmail();
        String applicationHome = applicationProperties.getHome();
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " - API")
                        .description(applicationName + " 管理系统后台接口文档")
                        .version(applicationVersion)
                        .license(new License()
                                .name("Apache 2.0 许可协议")
                                .url(applicationGithub + "/vita/blob/master/LICENSE"))
                        .contact(new Contact()
                                .name(applicationAuthor)
                                .email(applicationEmail)))
                .externalDocs(new ExternalDocumentation()
                        .description(applicationName + " 在线演示：" + applicationHome)
                        .url(applicationHome));
    }
}
