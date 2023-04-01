package com.github.mengweijin.vitality.admin.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.admin.system.entity.SysErrorLog;
import com.github.mengweijin.vitality.admin.system.mapper.SysErrorLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class SysErrorLogService extends ServiceImpl<SysErrorLogMapper, SysErrorLog> {

    @Autowired
    private SysErrorLogMapper sysErrorLogMapper;

}
