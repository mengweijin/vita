package com.github.mengweijin.vita.framework.validator;

import com.github.mengweijin.vita.framework.validator.annotation.Dict;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.service.DictDataService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义典数据校验注解实现
 * @author mengweijin
 */
public class DictValidator implements ConstraintValidator<Dict, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private String code;

    @Override
    public void initialize(Dict parameters) {
        code = parameters.code();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        DictDataService dictDataService = SpringUtil.getBean(DictDataService.class);
        List<DictDataDO> dictDataList = dictDataService.getByCode(code);
        if(CollUtil.isEmpty(dictDataList)) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            //自定义返回消息
            context.buildConstraintViolationWithTemplate("No dict data was found by dict code=" + code).addConstraintViolation();
            return false;
        }

        boolean anyMatch = dictDataList.stream().map(DictDataDO::getVal).anyMatch(item -> item.equals(value.toString()));
        if(!anyMatch) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            String correctDictDataCode = dictDataList.stream().map(DictDataDO::getVal).collect(Collectors.joining());
            String message = CharSequenceUtil.format("The dict_data_code[{}] of dict_type_code[{}] is incorrect! The correct dict_data_code should be in [{}]", value, code, correctDictDataCode);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

    private void validateParameters() {
        if (StrValidator.isBlankOrUndefined(code)) {
            throw LOG.getAnnotationDoesNotContainAParameterException(Dict.class, "code");
        }
    }

}