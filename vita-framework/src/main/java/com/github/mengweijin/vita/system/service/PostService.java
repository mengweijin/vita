package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.PostDO;
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

    /**
     * Custom paging query
     * @param page page
     * @param post {@link PostDO}
     * @return IPage
     */
    public IPage<PostDO> page(IPage<PostDO> page, PostDO post){
        LambdaQueryWrapper<PostDO> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(post.getSeq()), PostDO::getSeq, post.getSeq())
                .eq(StrValidator.isNotBlank(post.getDisabled()), PostDO::getDisabled, post.getDisabled())
                .eq(StrValidator.isNotBlank(post.getRemark()), PostDO::getRemark, post.getRemark())
                .eq(!Objects.isNull(post.getId()), PostDO::getId, post.getId())
                .eq(!Objects.isNull(post.getCreateBy()), PostDO::getCreateBy, post.getCreateBy())
                .eq(!Objects.isNull(post.getCreateTime()), PostDO::getCreateTime, post.getCreateTime())
                .eq(!Objects.isNull(post.getUpdateBy()), PostDO::getUpdateBy, post.getUpdateBy())
                .eq(!Objects.isNull(post.getUpdateTime()), PostDO::getUpdateTime, post.getUpdateTime())
                .like(StrValidator.isNotBlank(post.getName()), PostDO::getName, post.getName());
        return this.page(page, query);
    }
}
