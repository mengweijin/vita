package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * Custom paging query
     * @param page page
     * @param dictType {@link DictTypeDO}
     * @return IPage
     */
    public IPage<DictTypeDO> page(IPage<DictTypeDO> page, DictTypeDO dictType){
        LambdaQueryWrapper<DictTypeDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(dictType.getRemark()), DictTypeDO::getRemark, dictType.getRemark())
                .eq(!Objects.isNull(dictType.getId()), DictTypeDO::getId, dictType.getId())
                .eq(!Objects.isNull(dictType.getCreateBy()), DictTypeDO::getCreateBy, dictType.getCreateBy())
                .eq(!Objects.isNull(dictType.getCreateTime()), DictTypeDO::getCreateTime, dictType.getCreateTime())
                .eq(!Objects.isNull(dictType.getUpdateBy()), DictTypeDO::getUpdateBy, dictType.getUpdateBy())
                .eq(!Objects.isNull(dictType.getUpdateTime()), DictTypeDO::getUpdateTime, dictType.getUpdateTime())
                .like(StrValidator.isNotBlank(dictType.getName()), DictTypeDO::getName, dictType.getName())
                .like(StrValidator.isNotBlank(dictType.getCode()), DictTypeDO::getCode, dictType.getCode());
        return this.page(page, query);
    }

    public DictTypeDO getByCode(String code) {
        return this.lambdaQuery().eq(DictTypeDO::getCode, code).one();
    }
}
