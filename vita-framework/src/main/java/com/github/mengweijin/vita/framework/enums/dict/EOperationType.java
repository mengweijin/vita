package com.github.mengweijin.vita.framework.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * vt_operation_log_type
 *
 * @author mengweijin
 */
@Getter
@AllArgsConstructor
public enum EOperationType implements IEnum<String> {

    /**
     * 查询
     */
    SELECT("SELECT"),

    /**
     * 新增
     */
    INSERT("INSERT"),

    /**
     * 修改
     */
    UPDATE("UPDATE"),

    /**
     * 删除
     */
    REMOVE("REMOVE"),

    /**
     * 导入
     */
    IMPORT("IMPORT"),

    /**
     * 导出
     */
    EXPORT("EXPORT"),

    /**
     * 启用
     */
    ENABLE("ENABLE"),

    /**
     * 停用
     */
    DISABLE("DISABLE"),

    /**
     * 上传
     */
    UPLOAD("UPLOAD"),

    /**
     * 下载
     */
    DOWNLOAD("DOWNLOAD"),

    /**
     * 强制下线
     */
    OFFLINE("OFFLINE"),

    /**
     * 复制
     */
    COPY("COPY"),

    /**
     * 发布
     */
    PUBLISH("PUBLISH"),

    /**
     * 取消发布
     */
    UNPUBLISH("UNPUBLISH"),

    /**
     * 其他
     */
    OTHER("OTHER");

    private final String value;
}
