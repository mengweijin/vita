package com.github.mengweijin.vita.framework.validator;

import com.github.mengweijin.vita.framework.validator.annotation.Dict;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.enums.EDictType;
import com.github.mengweijin.vita.system.service.DictDataService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.extra.spring.SpringUtil;
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

    private EDictType dictType;

    @Override
    public void initialize(Dict parameters) {
        dictType = parameters.dictType();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        DictDataService dictDataService = SpringUtil.getBean(DictDataService.class);
        List<DictDataDO> dictDataList = dictDataService.getByCode(dictType.getValue());
        if(CollUtil.isEmpty(dictDataList)) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            //自定义返回消息
            context.buildConstraintViolationWithTemplate("No dict data was found by dict code=" + dictType.getValue()).addConstraintViolation();
            return false;
        }

        boolean anyMatch = dictDataList.stream().map(DictDataDO::getVal).anyMatch(item -> item.equals(value.toString()));
        if(!anyMatch) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            String correctDictDataCode = dictDataList.stream().map(DictDataDO::getVal).collect(Collectors.joining());
            String message = CharSequenceUtil.format("The dict_data_code[{}] of dict_type_code[{}] is incorrect! The correct dict_data_code should be in [{}]", value, dictType.getValue(), correctDictDataCode);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

    private void validateParameters() {
        if (StrValidator.isBlankOrUndefined(dictType.getValue())) {
            throw LOG.getAnnotationDoesNotContainAParameterException(Dict.class, "dictType");
        }
    }

}