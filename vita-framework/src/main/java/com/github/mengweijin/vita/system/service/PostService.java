package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.domain.vo.PostVO;
import com.github.mengweijin.vita.system.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class PostService extends BaseVitaService<PostMapper, PostDO, PostVO> {

    private UserPostService userPostService;

    @Override
    public boolean removeByIds(Collection<?> postIds) {
        long userCount = userPostService.countUserInPostIds(postIds);
        if(userCount > 0) {
            throw new ClientException(I18nUtils.msg("system.post.delete.hasUser"));
        }
        return super.removeByIds(postIds);
    }

    @Override
    public LambdaQueryWrapper<PostDO> buildQueryWrapper(PostDO post) {
        LambdaQueryWrapper<PostDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(post.getId() != null, PostDO::getId, post.getId());
        wrapper.eq(StrUtil.isNotBlank(post.getDisabled()), PostDO::getDisabled, post.getDisabled());
        wrapper.eq(post.getCreateBy() != null, PostDO::getCreateBy, post.getCreateBy());
        wrapper.eq(post.getUpdateBy() != null, PostDO::getUpdateBy, post.getUpdateBy());
        wrapper.gt(post.getStartCreateTime() != null, PostDO::getCreateTime, post.getStartCreateTime());
        wrapper.le(post.getEndCreateTime() != null, PostDO::getCreateTime, post.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(post.getName()), PostDO::getName, post.getName());
        wrapper.like(StrUtil.isNotBlank(post.getCode()), PostDO::getCode, post.getCode());
        return wrapper;
    }
}
