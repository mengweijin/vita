package com.github.mengweijin.vita.framework.jackson.translation;

import com.github.mengweijin.vita.framework.jackson.translation.strategy.ITranslationStrategy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class TranslationStrategyFactory {

    private final List<ITranslationStrategy<? extends Serializable>> translationList;

    public TranslationStrategyFactory(List<ITranslationStrategy<? extends Serializable>> translationList) {
        this.translationList = translationList;
    }

    public static final Map<ETranslateType, ITranslationStrategy<? extends Serializable>> TRANSLATION_STRATEGY_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ITranslationStrategy<? extends Serializable> strategy : translationList) {
            if(strategy.translateType() == null) {
                log.warn("{} : was not set translationType!", strategy.getClass().getName());
            }
            TRANSLATION_STRATEGY_MAP.put(strategy.translateType(), strategy);
        }
    }

    public static ITranslationStrategy<? extends Serializable> getTranslationStrategy(ETranslateType translateType) {
        return TRANSLATION_STRATEGY_MAP.get(translateType);
    }
}
