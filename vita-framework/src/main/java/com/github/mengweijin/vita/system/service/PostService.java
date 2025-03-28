package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.Post;
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
public class PostService extends CrudRepository<PostMapper, Post> {

    /**
     * Custom paging query
     * @param page page
     * @param post {@link Post}
     * @return IPage
     */
    public IPage<Post> page(IPage<Post> page, Post post){
        LambdaQueryWrapper<Post> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(post.getSeq()), Post::getSeq, post.getSeq())
                .eq(StrValidator.isNotBlank(post.getDisabled()), Post::getDisabled, post.getDisabled())
                .eq(StrValidator.isNotBlank(post.getRemark()), Post::getRemark, post.getRemark())
                .eq(!Objects.isNull(post.getId()), Post::getId, post.getId())
                .eq(!Objects.isNull(post.getCreateBy()), Post::getCreateBy, post.getCreateBy())
                .eq(!Objects.isNull(post.getCreateTime()), Post::getCreateTime, post.getCreateTime())
                .eq(!Objects.isNull(post.getUpdateBy()), Post::getUpdateBy, post.getUpdateBy())
                .eq(!Objects.isNull(post.getUpdateTime()), Post::getUpdateTime, post.getUpdateTime())
                .like(StrValidator.isNotBlank(post.getName()), Post::getName, post.getName());
        return this.page(page, query);
    }
}
