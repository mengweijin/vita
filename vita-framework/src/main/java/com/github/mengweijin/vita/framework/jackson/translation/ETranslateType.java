package com.github.mengweijin.vita.framework.jackson.translation;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 翻译类型枚举
 *
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ETranslateType implements IEnum<String> {

    /**
     * userId 转 username
     */
    USER_ID_TO_USERNAME("USER_ID_TO_USERNAME"),

    /**
     * userId 转 nickname
     */
    USER_ID_TO_NICKNAME("USER_ID_TO_NICKNAME"),

    /**
     * userId 转 用户图像
     */
    USER_ID_TO_AVATAR("USER_ID_TO_AVATAR"),

    /**
     * 部门 id 转 部门名称
     */
    DEPT_ID_TO_NAME("DEPT_ID_TO_NAME"),

    /**
     * 字典数据值 转 字典标签名
     */
    DICT_DATA_TO_LABEL("DICT_DATA_TO_LABEL"),

    /**
     * 分类 id 转 名称
     */
    CATEGORY_ID_TO_NAME("CATEGORY_ID_TO_NAME");

    private final String value;

}
