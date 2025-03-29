package com.github.mengweijin.vita.framework.jackson.translation;

import com.github.mengweijin.vita.framework.jackson.translation.strategy.ITranslationStrategy;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TranslationStrategyFactory {

    private List<ITranslationStrategy<Object>> translationList;

    @SuppressWarnings({"java:S2386"})
    public static final Map<ETranslateType, ITranslationStrategy<Object>> TRANSLATION_STRATEGY_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ITranslationStrategy<Object> strategy : translationList) {
            if(strategy.translateType() == null) {
                log.warn("{} : was not set translationType!", strategy.getClass().getName());
            }
            TRANSLATION_STRATEGY_MAP.put(strategy.translateType(), strategy);
        }
    }

    public static ITranslationStrategy<Object> getTranslationStrategy(ETranslateType translateType) {
        return TRANSLATION_STRATEGY_MAP.get(translateType);
    }
}
