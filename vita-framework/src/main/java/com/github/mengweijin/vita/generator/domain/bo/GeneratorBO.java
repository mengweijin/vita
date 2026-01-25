package com.github.mengweijin.vita.generator.domain.bo;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.util.SpringBootMainClassUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@Data
public class GeneratorBO {

    private String templateId;

    @NotBlank
    private String tableName;

    private String tablePrefix;

    private String packages;

    private String moduleName;

    private String author;

    /**
     * Optional.
     */
    private String baseEntity;

    public GeneratorBO() {
        this.tablePrefix = String.join(",", "VT_", "SYS_");
        this.packages = SpringBootMainClassUtils.getSpringBootApplicationClassPackage();
        this.moduleName = "system";
        this.author = "mengweijin";
        this.baseEntity = BaseEntity.class.getName();
    }
}
