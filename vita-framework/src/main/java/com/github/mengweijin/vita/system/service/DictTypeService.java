package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.domain.entity.DictTypeDO;
import com.github.mengweijin.vita.system.domain.vo.DictTypeVO;
import com.github.mengweijin.vita.system.mapper.DictTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * DictType Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class DictTypeService extends BaseVitaService<DictTypeMapper, DictTypeDO, DictTypeVO> {

    private DictDataService dictDataService;

    @Override
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list) {
            List<DictDataDO> dictDataList = dictDataService.queryByTypeId((Long) id);
            if (CollUtil.isNotEmpty(dictDataList)) {
                throw new ClientException("Please remove dict data first in dict type id=[" + id + "].");
            }
        }
        return super.removeByIds(list);
    }

    @Override
    public LambdaQueryWrapper<DictTypeDO> buildQueryWrapper(DictTypeDO dictType) {
        LambdaQueryWrapper<DictTypeDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(dictType.getId() != null, DictTypeDO::getId, dictType.getId());
        wrapper.eq(dictType.getCreateBy() != null, DictTypeDO::getCreateBy, dictType.getCreateBy());
        wrapper.eq(dictType.getUpdateBy() != null, DictTypeDO::getUpdateBy, dictType.getUpdateBy());
        wrapper.gt(dictType.getStartCreateTime() != null, DictTypeDO::getCreateTime, dictType.getStartCreateTime());
        wrapper.le(dictType.getEndCreateTime() != null, DictTypeDO::getCreateTime, dictType.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(dictType.getName()), DictTypeDO::getName, dictType.getName());
        wrapper.like(StrUtil.isNotBlank(dictType.getCode()), DictTypeDO::getCode, dictType.getCode());
        return wrapper;
    }

    public DictTypeDO queryByCode(String code) {
        return this.lambdaQuery().eq(DictTypeDO::getCode, code).one();
    }

    @Cacheable(value = CacheNames.DICT_TYPE_ID_TO_CODE, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String queryCodeById(Long id) {
        return this.lambdaQuery().eq(DictTypeDO::getId, id).oneOpt().map(DictTypeDO::getCode).orElse(null);
    }
}
