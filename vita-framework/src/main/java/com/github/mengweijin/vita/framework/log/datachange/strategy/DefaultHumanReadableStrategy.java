package com.github.mengweijin.vita.framework.log.datachange.strategy;

import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/1/25
 */
@Component(DefaultHumanReadableStrategy.BEAN_NAME)
public class DefaultHumanReadableStrategy implements IHumanReadableStrategy {

    public static final String BEAN_NAME = "defaultDataChangeHumanReadableStrategy";

    @Override
    public String getBeanName() {
        return BEAN_NAME;
    }

    @Override
    public List<String> toHumanReadable(List<DiffModel> changeData) {
        changeData.sort(Comparator.comparing(DiffModel::getDiffType));
        String i18nKey = "monitor.log.datachange.human.readable.template.default";
        return changeData.stream()
                .map(i -> I18nUtils.msg(i18nKey, i.getFieldName(), i.getOldValue(), i.getNewValue()))
                .toList();
    }
}
