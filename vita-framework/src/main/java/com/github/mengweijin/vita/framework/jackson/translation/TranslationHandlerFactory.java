package com.github.mengweijin.vita.framework.jackson.translation;

import com.github.mengweijin.vita.framework.jackson.translation.handler.ITranslationHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class TranslationHandlerFactory {

    private static final Map<ETranslateType, ITranslationHandler> TRANSLATION_STRATEGY_MAP = new ConcurrentHashMap<>();
    private final List<ITranslationHandler> translationList;

    public TranslationHandlerFactory(List<ITranslationHandler> translationList) {
        this.translationList = translationList;
    }

    public static ITranslationHandler getTranslationStrategy(ETranslateType translateType) {
        return TRANSLATION_STRATEGY_MAP.get(translateType);
    }

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ITranslationHandler handler : translationList) {
            if (handler.translateType() == null) {
                log.warn("{} : was not set translationType!", handler.getClass().getName());
            }
            TRANSLATION_STRATEGY_MAP.put(handler.translateType(), handler);
        }
    }
}
