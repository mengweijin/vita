package com.github.mengweijin.quickboot.framework.validator.annotation;

import com.github.mengweijin.quickboot.framework.validator.CharsetLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Meng Wei Jin
 * @description 自定义字段验证
 * @date Create in 2019-10-21 19:58
 **/
@Documented
@Constraint(validatedBy = CharsetLengthValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CharsetLength {

    int min() default 0;

    /**
     * 数据库可存储的最大字节数
     * @return
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 默认字符编码，以此计算汉字字节长度；
     * Java语言中，中文字符所占的字节数取决于字符的编码方式，一般情况下，
     * 采用ISO8859-1编码方式时，一个中文字符与一个英文字符一样只占1个字节；
     * 采用GB2312或GBK编码方式时，一个中文字符占2个字节；
     * 采用UTF-8编码方式时，一个中文字符会占3个字节.
     * @return
     */
    String charset() default "UTF-8";

    String message() default "${validatedValue}: {org.hibernate.validator.constraints.Length.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
