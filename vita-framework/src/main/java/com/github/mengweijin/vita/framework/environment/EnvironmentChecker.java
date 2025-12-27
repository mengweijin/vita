package com.github.mengweijin.vita.framework.environment;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 * @author mengweijin
 * @since 2025/12/21
 */
@Component
@AllArgsConstructor
public class EnvironmentChecker {

    private final Environment environment;

    public boolean isDevOrLocalOrTest() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.stream(activeProfiles)
                .anyMatch(profile -> profile.toLowerCase().matches("dev|local|test"));
    }
}
