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
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
        wrapper.eq(!Objects.isNull(dictType.getId()), DictTypeDO::getId, dictType.getId());
        wrapper.eq(!Objects.isNull(dictType.getCreateBy()), DictTypeDO::getCreateBy, dictType.getCreateBy());
        wrapper.eq(!Objects.isNull(dictType.getUpdateBy()), DictTypeDO::getUpdateBy, dictType.getUpdateBy());
        wrapper.gt(!Objects.isNull(dictType.getSearchStartTime()), DictTypeDO::getCreateTime, dictType.getSearchStartTime());
        wrapper.le(!Objects.isNull(dictType.getSearchEndTime()), DictTypeDO::getCreateTime, dictType.getSearchEndTime());
        if (StrValidator.isNotBlank(dictType.getKeywords())) {
            wrapper.or(w -> w.like(DictTypeDO::getName, dictType.getKeywords()));
            wrapper.or(w -> w.like(DictTypeDO::getCode, dictType.getKeywords()));
        }
        return wrapper;
    }

    public DictTypeDO getByCode(String code) {
        return this.lambdaQuery().eq(DictTypeDO::getCode, code).one();
    }
}
