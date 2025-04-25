package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.system.domain.DeptDO;
import com.github.mengweijin.vita.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    /**
     * Custom paging query
     * @param page page
     * @param dept {@link DeptDO}
     * @return IPage
     */
    public IPage<DeptDO> page(IPage<DeptDO> page, DeptDO dept){
        LambdaQueryWrapper<DeptDO> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(dept.getParentId()), DeptDO::getParentId, dept.getParentId())
                .eq(StrValidator.isNotBlank(dept.getName()), DeptDO::getName, dept.getName())
                .eq(!Objects.isNull(dept.getSeq()), DeptDO::getSeq, dept.getSeq())
                .eq(StrValidator.isNotBlank(dept.getDisabled()), DeptDO::getDisabled, dept.getDisabled())
                .eq(StrValidator.isNotBlank(dept.getRemark()), DeptDO::getRemark, dept.getRemark())
                .eq(!Objects.isNull(dept.getId()), DeptDO::getId, dept.getId())
                .eq(!Objects.isNull(dept.getCreateBy()), DeptDO::getCreateBy, dept.getCreateBy())
                .eq(!Objects.isNull(dept.getCreateTime()), DeptDO::getCreateTime, dept.getCreateTime())
                .eq(!Objects.isNull(dept.getUpdateBy()), DeptDO::getUpdateBy, dept.getUpdateBy())
                .eq(!Objects.isNull(dept.getUpdateTime()), DeptDO::getUpdateTime, dept.getUpdateTime());
        return this.page(page, query);
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
}
