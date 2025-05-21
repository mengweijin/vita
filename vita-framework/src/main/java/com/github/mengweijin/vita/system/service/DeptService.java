package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
    public boolean removeByIds(Collection<?> ids) {
        Long subDeptCount = this.lambdaQuery().in(DeptDO::getParentId, ids).count();
        if(subDeptCount > 0) {
            throw new ClientException("Please delete the child node first!");
        }

        UserService userService = SpringUtil.getBean(UserService.class);
        Long userCount = userService.lambdaQuery().in(UserDO::getDeptId, ids).count();
        if(userCount > 0) {
            throw new ClientException("Please remove all users under the department first!");
        }
        return super.removeByIds(ids);
    }

    public LambdaQueryWrapper<DeptDO> getQueryWrapper(DeptDO dept) {
        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dept.getId() != null, DeptDO::getId, dept.getId());
        wrapper.eq(dept.getParentId() != null, DeptDO::getParentId, dept.getParentId());
        wrapper.eq(StrUtil.isNotBlank(dept.getDisabled()), DeptDO::getDisabled, dept.getDisabled());
        wrapper.eq(dept.getCreateBy() != null, DeptDO::getCreateBy, dept.getCreateBy());
        wrapper.eq(dept.getUpdateBy() != null, DeptDO::getUpdateBy, dept.getUpdateBy());
        wrapper.gt(dept.getSearchStartTime() != null, DeptDO::getCreateTime, dept.getSearchStartTime());
        wrapper.le(dept.getSearchEndTime() != null, DeptDO::getCreateTime, dept.getSearchEndTime());
        if (StrUtil.isNotBlank(dept.getKeywords())) {
            wrapper.or(w -> w.like(DeptDO::getCode, dept.getKeywords()));
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

}
