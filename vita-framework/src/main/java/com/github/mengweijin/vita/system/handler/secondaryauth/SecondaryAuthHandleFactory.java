package com.github.mengweijin.vita.system.handler.secondaryauth;

import com.github.mengweijin.vita.framework.enums.dict.ESafeMode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 */
@Slf4j
@Component
@AllArgsConstructor
public class SecondaryAuthHandleFactory implements InitializingBean {

    private static final Map<ESafeMode, ISecondaryAuthHandler> CACHE_MAP = new ConcurrentHashMap<>();
    private final List<ISecondaryAuthHandler> openSafeValidateList;

    public static ISecondaryAuthHandler getHandler(ESafeMode safeMode) {
        return CACHE_MAP.get(safeMode);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (ISecondaryAuthHandler validate : openSafeValidateList) {
            if (validate.supported() == null) {
                log.warn("{} : was not set supported safeMode!", validate.getClass().getName());
            }
            CACHE_MAP.put(validate.supported(), validate);
        }
    }

}
