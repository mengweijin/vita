package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Post Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class PostService extends CrudRepository<PostMapper, PostDO> {

    public LambdaQueryWrapper<PostDO> getQueryWrapper(PostDO post) {
        LambdaQueryWrapper<PostDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(post.getId() != null, PostDO::getId, post.getId());
        wrapper.eq(StrUtil.isNotBlank(post.getDisabled()), PostDO::getDisabled, post.getDisabled());
        wrapper.eq(post.getCreateBy() != null, PostDO::getCreateBy, post.getCreateBy());
        wrapper.eq(post.getUpdateBy() != null, PostDO::getUpdateBy, post.getUpdateBy());
        wrapper.gt(post.getSearchStartTime() != null, PostDO::getCreateTime, post.getSearchStartTime());
        wrapper.le(post.getSearchEndTime() != null, PostDO::getCreateTime, post.getSearchEndTime());
        if (StrUtil.isNotBlank(post.getKeywords())) {
            wrapper.or(w -> w.like(PostDO::getCode, post.getKeywords()));
            wrapper.or(w -> w.like(PostDO::getName, post.getKeywords()));
        }
        wrapper.orderByAsc(PostDO::getSeq);
        return wrapper;
    }
}
