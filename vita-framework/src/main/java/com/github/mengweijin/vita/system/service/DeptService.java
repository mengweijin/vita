package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  Dept Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DeptService extends CrudRepository<DeptMapper, DeptDO> {

    @Override
    public boolean removeByIds(Collection<?> list) {
        boolean anyMatch = list.stream().anyMatch(id -> this.hasChildren((Long) id));
        if(anyMatch) {
            throw new ClientException("Please delete the child node first!");
        }
        return super.removeByIds(list);
    }

    public LambdaQueryWrapper<DeptDO> getQueryWrapper(DeptDO dept) {
        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!Objects.isNull(dept.getId()), DeptDO::getId, dept.getId());
        wrapper.eq(!Objects.isNull(dept.getParentId()), DeptDO::getParentId, dept.getParentId());
        wrapper.eq(StrValidator.isNotBlank(dept.getDisabled()), DeptDO::getDisabled, dept.getDisabled());
        wrapper.eq(!Objects.isNull(dept.getCreateBy()), DeptDO::getCreateBy, dept.getCreateBy());
        wrapper.eq(!Objects.isNull(dept.getUpdateBy()), DeptDO::getUpdateBy, dept.getUpdateBy());
        wrapper.gt(!Objects.isNull(dept.getSearchStartTime()), DeptDO::getCreateTime, dept.getSearchStartTime());
        wrapper.le(!Objects.isNull(dept.getSearchEndTime()), DeptDO::getCreateTime, dept.getSearchEndTime());
        if (StrValidator.isNotBlank(dept.getKeywords())) {
            wrapper.or(w -> w.like(DeptDO::getName, dept.getKeywords()));
        }
        wrapper.orderByAsc(DeptDO::getSeq);
        return wrapper;
    }

    public boolean setDisabled(Long id, String disabled) {
        return this.lambdaUpdate().set(DeptDO::getDisabled, disabled).eq(DeptDO::getId, id).update();
    }

    @Cacheable(value = CacheNames.DEPT_ID_TO_NAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNameById(Long id) {
        return this.lambdaQuery()
                .select(DeptDO::getName)
                .eq(DeptDO::getId, id)
                .oneOpt()
                .map(DeptDO::getName)
                .orElse(null);
    }

    public List<Long> selectChildrenIdsWithCurrentIdById(Long id) {
        return this.getBaseMapper().selectChildrenIdsWithCurrentIdById(id);
    }

    public boolean hasChildren(Long id) {
        return this.lambdaQuery().eq(DeptDO::getParentId, id).count() > 0;
    }
}
