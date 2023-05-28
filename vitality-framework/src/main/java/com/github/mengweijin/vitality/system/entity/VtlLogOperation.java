package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统操作日志表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_OPERATION")
public class VtlLogOperation extends BaseEntity {

    /**
     * 请求url
     */
    @TableField("URL")
    private String url;

    /**
     * http 请求方式
     */
    @TableField("HTTP_METHOD")
    private String httpMethod;

    /**
     * 请求方法名称
     */
    @TableField("METHOD_NAME")
    private String methodName;

    /**
     * 操作IP地址
     */
    @TableField("IP")
    private String ip;

    /**
     * IP所属位置
     */
    @TableField("IP_LOCATION")
    private String ipLocation;

    /**
     * 操作是否成功。{0=失败, 1=成功}
     */
    @TableField("SUCCEEDED")
    private Boolean succeeded;

    /**
     * 错误消息
     */
    @TableField("ERROR_INFO")
    private String errorInfo;

}
