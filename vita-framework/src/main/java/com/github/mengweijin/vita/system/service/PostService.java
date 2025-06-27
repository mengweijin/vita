package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
@AllArgsConstructor
public class PostService extends CrudRepository<PostMapper, PostDO> {

    private UserPostService userPostService;

    @Override
    public boolean removeByIds(Collection<?> postIds) {
        long userCount = userPostService.countUserInPostIds(postIds);
        if(userCount > 0) {
            throw new ClientException(I18nUtils.msg("system.post.delete.hasUser"));
        }
        return super.removeByIds(postIds);
    }

    public LambdaQueryWrapper<PostDO> getQueryWrapper(PostDO post) {
        LambdaQueryWrapper<PostDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(post.getId() != null, PostDO::getId, post.getId());
        wrapper.eq(StrUtil.isNotBlank(post.getDisabled()), PostDO::getDisabled, post.getDisabled());
        wrapper.eq(post.getCreateBy() != null, PostDO::getCreateBy, post.getCreateBy());
        wrapper.eq(post.getUpdateBy() != null, PostDO::getUpdateBy, post.getUpdateBy());
        wrapper.gt(post.getSearchStartTime() != null, PostDO::getCreateTime, post.getSearchStartTime());
        wrapper.le(post.getSearchEndTime() != null, PostDO::getCreateTime, post.getSearchEndTime());
        if (StrUtil.isNotBlank(post.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(PostDO::getCode, post.getKeywords()));
                w.or(w1 -> w1.like(PostDO::getName, post.getKeywords()));
            });
        }
        return wrapper;
    }
}
