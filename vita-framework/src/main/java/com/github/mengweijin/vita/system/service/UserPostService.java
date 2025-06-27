package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.UserPostDO;
import com.github.mengweijin.vita.system.mapper.UserPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * User Post Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserPostService extends CrudRepository<UserPostMapper, UserPostDO> {

    public Set<Long> getUserIdsByPostId(Long postId) {
        List<UserPostDO> list = this.lambdaQuery().select(UserPostDO::getUserId).eq(UserPostDO::getPostId, postId).list();
        return list.stream().map(UserPostDO::getUserId).collect(Collectors.toSet());
    }

    public Long countUserInPostIds(Collection<?> postIds) {
        return this.lambdaQuery().in(UserPostDO::getPostId, postIds).count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUserPosts(Long userId, List<Long> postIds) {
        this.lambdaUpdate().eq(UserPostDO::getUserId, userId).remove();

        if(CollUtil.isEmpty(postIds)) {
            return;
        }

        List<UserPostDO> list = postIds.stream().map(postId -> {
            UserPostDO userPost = new UserPostDO();
            userPost.setUserId(userId);
            userPost.setPostId(postId);
            return userPost;
        }).toList();

        this.saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
    }

}
