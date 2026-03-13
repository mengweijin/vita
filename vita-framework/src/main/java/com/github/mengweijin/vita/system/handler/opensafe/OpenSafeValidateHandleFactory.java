package com.github.mengweijin.vita.system.handler.opensafe;

import com.github.mengweijin.vita.system.enums.dict.ESafeMode;
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
public class OpenSafeValidateHandleFactory implements InitializingBean {

    private final List<IOpenSafeValidateHandler> openSafeValidateList;

    private static final Map<ESafeMode, IOpenSafeValidateHandler> CACHE_MAP = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (IOpenSafeValidateHandler validate : openSafeValidateList) {
            if(validate.supported() == null) {
                log.warn("{} : was not set supported safeMode!", validate.getClass().getName());
            }
            CACHE_MAP.put(validate.supported(), validate);
        }
    }

    public static IOpenSafeValidateHandler getHandler(ESafeMode safeMode) {
        return CACHE_MAP.get(safeMode);
    }

}
