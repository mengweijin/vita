package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.mapper.DictDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  DictData Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DictDataService extends CrudRepository<DictDataMapper, DictDataDO> {

    public LambdaQueryWrapper<DictDataDO> getQueryWrapper(DictDataDO dictData) {
        LambdaQueryWrapper<DictDataDO> wrapper = Wrappers.lambdaQuery(dictData);
        wrapper.eq(dictData.getId() != null, DictDataDO::getId, dictData.getId());
        wrapper.eq(StrUtil.isNotBlank(dictData.getDisabled()), DictDataDO::getDisabled, dictData.getDisabled());
        wrapper.eq(dictData.getCreateBy() != null, DictDataDO::getCreateBy, dictData.getCreateBy());
        wrapper.eq(dictData.getUpdateBy() != null, DictDataDO::getUpdateBy, dictData.getUpdateBy());
        wrapper.gt(dictData.getStartCreateTime() != null, DictDataDO::getCreateTime, dictData.getStartCreateTime());
        wrapper.le(dictData.getEndCreateTime() != null, DictDataDO::getCreateTime, dictData.getEndCreateTime());
        if (StrUtil.isNotBlank(dictData.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(DictDataDO::getLabel, dictData.getKeywords()));
                w.or(w1 -> w1.like(DictDataDO::getCode, dictData.getKeywords()));
                w.or(w1 -> w1.like(DictDataDO::getVal, dictData.getKeywords()));
            });
        }
        return wrapper;
    }

    @Cacheable(value = CacheNames.DICT_VAL_TO_LABEL, key = "#code + ':' + #val", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getLabelByCodeAndVal(String code, String val) {
        return this.lambdaQuery()
                .select(DictDataDO::getLabel)
                .eq(DictDataDO::getCode, code)
                .eq(DictDataDO::getVal, val)
                .oneOpt()
                .map(DictDataDO::getLabel)
                .orElse(null);
    }

    public List<DictDataDO> getByCode(String code) {
        return this.lambdaQuery()
                .eq(DictDataDO::getCode, code)
                .orderByAsc(DictDataDO::getSeq)
                .list();
    }

    public void checkValDuplicate(Long id, String code, String val) {
        Optional<DictDataDO> optional = this.lambdaQuery()
                .eq(DictDataDO::getCode, code)
                .eq(DictDataDO::getVal, val)
                .ne(id != null, DictDataDO::getId, id)
                .oneOpt();
        if (optional.isPresent()) {
            throw new ClientException(CharSequenceUtil.format("The dict type code[{}] and value[{}] already exists!", code, val));
        }
    }

}
