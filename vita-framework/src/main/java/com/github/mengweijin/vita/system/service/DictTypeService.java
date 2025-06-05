package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.domain.entity.DictTypeDO;
import com.github.mengweijin.vita.system.mapper.DictTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  DictType Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class DictTypeService extends CrudRepository<DictTypeMapper, DictTypeDO> {

    private DictDataService dictDataService;

    @Override
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list) {
            DictTypeDO dictType = this.getById((Long) id);
            List<DictDataDO> dictDataList = dictDataService.getByCode(dictType.getCode());
            if(CollUtil.isNotEmpty(dictDataList)) {
                throw new ClientException("Please remove dict data first in dict type [" + dictType.getName() + "].");
            }
        }
        return super.removeByIds(list);
    }

    public LambdaQueryWrapper<DictTypeDO> getQueryWrapper(DictTypeDO dictType) {
        LambdaQueryWrapper<DictTypeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dictType.getId() != null, DictTypeDO::getId, dictType.getId());
        wrapper.eq(dictType.getCreateBy() != null, DictTypeDO::getCreateBy, dictType.getCreateBy());
        wrapper.eq(dictType.getUpdateBy() != null, DictTypeDO::getUpdateBy, dictType.getUpdateBy());
        wrapper.gt(dictType.getSearchStartTime() != null, DictTypeDO::getCreateTime, dictType.getSearchStartTime());
        wrapper.le(dictType.getSearchEndTime() != null, DictTypeDO::getCreateTime, dictType.getSearchEndTime());
        if (StrUtil.isNotBlank(dictType.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(DictTypeDO::getName, dictType.getKeywords()));
                w.or(w1 -> w1.like(DictTypeDO::getCode, dictType.getKeywords()));
            });
        }
        return wrapper;
    }

    public DictTypeDO getByCode(String code) {
        return this.lambdaQuery().eq(DictTypeDO::getCode, code).one();
    }
}
