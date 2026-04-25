package com.github.mengweijin.vita.framework.mapstruct;

import io.github.linpeilie.annotations.MapperConfig;
import org.springframework.context.annotation.Configuration;

/**
 * mapperPackage: 生成的 mapper 接口所在的包路径，指定到 spring 可以扫描到的位置。默认散落在目标类所在的包路径。
 * 如果转为三方依赖 jar 中的类，会出现不同的包路径，而默认 spring 扫描不到三方 jar 的包路径，就会出错。
 * 因此，统一指定到工程所在包路径下可以被 spring 扫描到的位置。
 *
 * @author mengweijin
 * @since 2026/4/12
 */
@Configuration
@MapperConfig(mapperPackage = "com.github.mengweijin.vita.framework.mapstruct.mappers")
public class MapStructPlusConfig {
}
