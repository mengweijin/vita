package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        wrapper.eq(!Objects.isNull(post.getId()), PostDO::getId, post.getId());
        wrapper.eq(StrValidator.isNotBlank(post.getDisabled()), PostDO::getDisabled, post.getDisabled());
        wrapper.eq(!Objects.isNull(post.getCreateBy()), PostDO::getCreateBy, post.getCreateBy());
        wrapper.eq(!Objects.isNull(post.getUpdateBy()), PostDO::getUpdateBy, post.getUpdateBy());
        wrapper.gt(!Objects.isNull(post.getSearchStartTime()), PostDO::getCreateTime, post.getSearchStartTime());
        wrapper.le(!Objects.isNull(post.getSearchEndTime()), PostDO::getCreateTime, post.getSearchEndTime());
        if (StrValidator.isNotBlank(post.getKeywords())) {
            wrapper.or(w -> w.like(PostDO::getName, post.getKeywords()));
        }
        wrapper.orderByAsc(PostDO::getSeq);
        return wrapper;
    }
}
