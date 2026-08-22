package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.FormCreateDO;
import com.github.mengweijin.vita.system.domain.vo.FormCreateVO;
import com.github.mengweijin.vita.system.mapper.FormCreateMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 表单管理表 FormCreate Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-08-22
 */
@Slf4j
@Service
@AllArgsConstructor
public class FormCreateService extends BaseVitaService<FormCreateMapper, FormCreateDO, FormCreateVO> {

    @Override
    public LambdaQueryWrapper<FormCreateDO> buildQueryWrapper(FormCreateDO formCreate) {
        LambdaQueryWrapper<FormCreateDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(formCreate.getId() != null, FormCreateDO::getId, formCreate.getId());
        wrapper.eq(StrUtil.isNotBlank(formCreate.getName()), FormCreateDO::getName, formCreate.getName());
        wrapper.eq(StrUtil.isNotBlank(formCreate.getCode()), FormCreateDO::getCode, formCreate.getCode());
        wrapper.eq(StrUtil.isNotBlank(formCreate.getRemark()), FormCreateDO::getRemark, formCreate.getRemark());
        wrapper.gt(formCreate.getStartCreateTime() != null, FormCreateDO::getCreateTime, formCreate.getStartCreateTime());
        wrapper.le(formCreate.getEndCreateTime() != null, FormCreateDO::getCreateTime, formCreate.getEndCreateTime());
        return wrapper;
    }
}
