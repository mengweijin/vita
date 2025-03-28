package com.github.mengweijin.vita.framework.validator;


import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 自定义业务校验注解实现
 *
 * @author mengweijin
 */
public class BusinessCheckValidator implements ConstraintValidator<BusinessCheck, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private Class<? extends CheckRule>[] checkRule;

    @Override
    public void initialize(BusinessCheck parameters) {
        checkRule = parameters.checkRule();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            for (Class<? extends CheckRule> cls : checkRule) {
                CheckRule checkRuleInstance = cls.getDeclaredConstructor().newInstance();
                boolean valid = checkRuleInstance.isValid(value);
                if (!valid) {
                    //禁止默认消息返回
                    context.disableDefaultConstraintViolation();
                    //自定义返回消息
                    context.buildConstraintViolationWithTemplate(checkRuleInstance.message(value)).addConstraintViolation();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new ServerException(e);
        }
    }

    private void validateParameters() {
        if (checkRule == null || checkRule.length == 0) {
            throw LOG.getAnnotationDoesNotContainAParameterException(BusinessCheck.class, "checkRule");
        }
    }

    public interface CheckRule {

        boolean isValid(CharSequence value);

        String message(CharSequence value);
    }
}