package com.github.mengweijin.vita.framework.jackson.translation.handler;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.DeptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户名翻译
 *
 * @author mengweijin
 * @since 2023/5/20
 */
@Slf4j
@Component
@AllArgsConstructor
public class DeptNameTranslationHandler implements ITranslationHandler {

    private DeptService deptService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DEPT_ID_TO_NAME;
    }

    @Override
    public String translation(Object value, Translation translation) {
        try {
            long id = NumberUtil.parseLong(StrUtil.toStringOrNull(value));
            return deptService.getNameById(id);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
