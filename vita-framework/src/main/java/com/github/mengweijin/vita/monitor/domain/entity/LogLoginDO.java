package com.github.mengweijin.vita.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.monitor.domain.bo.LogLoginBO;
import com.github.mengweijin.vita.monitor.domain.vo.LogLoginVO;
import com.github.mengweijin.vita.framework.enums.dict.ELoginType;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@AutoMappers({
        @AutoMapper(target = LogLoginBO.class),
        @AutoMapper(target = LogLoginVO.class),
})
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("VT_LOG_LOGIN")
public class LogLoginDO extends BaseEntity {

    /**
    * 登录账号
    */
    private String username;

    /**
    * 登录类型。枚举类 {@link ELoginType}
    */
    private String loginType;

    /**
    * 登录IP地址
    */
    private String ip;

    /**
    * IP所属位置
    */
    private String ipLocation;

    /**
    * 浏览器
    */
    private String browser;

    /**
    * 设备平台类型
    */
    private String platform;

    /**
    * 操作系统
    */
    private String os;

    /**
    * 登录是否成功。[Y, N]
    */
    private String success;

    /**
    * 失败信息
    */
    private String errorMsg;

}
