package com.github.mengweijin.vita.framework.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@Configuration
public class SpringDocConfig {

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${application.author}")
    private String applicationAuthor;

    @Value("${application.email}")
    private String applicationEmail;

    @Value("${application.home}")
    private String applicationHome;

    @Value("${application.github}")
    private String applicationGithub;

    @Bean
    public OpenAPI openAPI() {
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
