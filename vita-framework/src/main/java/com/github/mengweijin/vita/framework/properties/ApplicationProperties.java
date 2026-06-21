package com.github.mengweijin.vita.framework.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Meng Wei Jin
 **/
@Data
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @NotBlank
    private String name = "vita-admin";

    @NotBlank
    private String version;

    @NotBlank
    private String formattedVersion;

    @NotBlank
    private String author = "mengweijin";

    @NotBlank
    private String email = "aday.fun@outlook.com";

    @NotBlank
    private String blog = "https://aday.fun";

    @NotBlank
    private String home = "https://vita.aday.fun";

    @NotBlank
    private String github = "https://github.com/mengweijin";

    @NotBlank
    private String gitee = "https://gitee.com/mengweijin";

}
