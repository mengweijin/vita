package com.github.mengweijin.vita.framework.validator;


import com.github.mengweijin.vita.framework.validator.annotation.Xss;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dromara.hutool.http.html.HtmlUtil;

/**
 * 自定义xss校验注解实现
 * @author mengweijin
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !containsHtml(value);
    }

    public boolean containsHtml(String value) {
        if(value == null) {
            return false;
        }
        return HtmlUtil.RE_HTML_MARK.matcher(value).find();
    }
}