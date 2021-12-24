package com.github.mengweijin.quickboot.framework.environment;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.framework.util.AesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import java.util.HashMap;

/**
 * 安全加密处理器 参考自：com.baomidou.mybatisplus.autoconfigure.SafetyEncryptProcessor
 * <p>
 * 1. 生成 16 位随机 AES 密钥，在启动 jar 时把下面生成的 key 通过命令行参数 --cipher.key={randomKey} 传递到应用程序中
 * String randomKey = AesUtils.generateRandomKey();
 * <p>
 * 2. 密钥加密：配置在 application.yaml 中的加密值
 * String encrypt = AesUtils.encrypt(randomKey, password);
 * <p>
 * 3. YML 配置：加密配置 {cipher} 开头紧接加密内容（ 非数据库配置专用 YML 中其它配置也是可以使用的 ）
 * spring.datasource.username={cipher}Xb+EgsyuYRXw7U7sBJjBpA==
 * spring.datasource.password={cipher}Hzy5iliJbwDHhjLs1L0j6w==
 * <p>
 * 4. 为什么以 {cipher} 作为前缀？目的是和 Spring cloud config 加密前缀保持一直
 *
 * @author mengweijin
 */
public class SafetyEncryptEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 命令行中获取密钥
        String cipherKey = null;
        for (PropertySource<?> ps : environment.getPropertySources()) {
            if (ps instanceof SimpleCommandLinePropertySource) {
                SimpleCommandLinePropertySource source = (SimpleCommandLinePropertySource) ps;
                cipherKey = source.getProperty("cipher.key");
                break;
            }
        }
        // 处理加密内容
        if (StrUtil.isNotBlank(cipherKey)) {
            HashMap<String, Object> map = new HashMap<>();
            for (PropertySource<?> ps : environment.getPropertySources()) {
                if (ps instanceof OriginTrackedMapPropertySource) {
                    OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) ps;
                    for (String name : source.getPropertyNames()) {
                        Object value = source.getProperty(name);
                        if (value instanceof String) {
                            String str = (String) value;
                            if (str.startsWith("{cipher}")) {
                                map.put(name, AesUtils.decrypt(cipherKey, str.substring(8)));
                            }
                        }
                    }
                }
            }
            // 将解密的数据放入环境变量，并处于第一优先级上
            if (CollectionUtil.isNotEmpty(map)) {
                environment.getPropertySources().addFirst(new MapPropertySource("custom-encrypt", map));
            }
        }
    }
}
