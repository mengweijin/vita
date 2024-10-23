package com.github.mengweijin.vitality.framework.jackson.sensitive;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.mengweijin.vitality.framework.jackson.sensitive.serializer.SensitiveAnnotationSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏
 *
 * @author mengweijin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveAnnotationSerializer.class)
public @interface Sensitive {

    ESensitiveStrategy strategy();

}
