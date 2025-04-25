package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.UserPostDO;
import com.github.mengweijin.vita.system.mapper.UserPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
