package com.github.mengweijin.vita.framework.jackson.translation;

import com.github.mengweijin.vita.framework.jackson.translation.strategy.ITranslationStrategy;
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
public class TranslationStrategyFactory {

    private final List<ITranslationStrategy> translationList;

    public TranslationStrategyFactory(List<ITranslationStrategy> translationList) {
        this.translationList = translationList;
    }

    private static final Map<ETranslateType, ITranslationStrategy> TRANSLATION_STRATEGY_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ITranslationStrategy strategy : translationList) {
            if(strategy.translateType() == null) {
                log.warn("{} : was not set translationType!", strategy.getClass().getName());
            }
            TRANSLATION_STRATEGY_MAP.put(strategy.translateType(), strategy);
        }
    }

    public static ITranslationStrategy getTranslationStrategy(ETranslateType translateType) {
        return TRANSLATION_STRATEGY_MAP.get(translateType);
    }
}
