package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.NoticeDO;
import com.github.mengweijin.vita.system.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Notice Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class NoticeService extends CrudRepository<NoticeMapper, NoticeDO> {

    public LambdaQueryWrapper<NoticeDO> getQueryWrapper(NoticeDO notice) {
        LambdaQueryWrapper<NoticeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(notice.getId() != null, NoticeDO::getId, notice.getId());
        wrapper.eq(StrUtil.isNotBlank(notice.getReleased()), NoticeDO::getReleased, notice.getReleased());
        wrapper.eq(notice.getCreateBy() != null, NoticeDO::getCreateBy, notice.getCreateBy());
        wrapper.eq(notice.getUpdateBy() != null, NoticeDO::getUpdateBy, notice.getUpdateBy());
        wrapper.gt(notice.getSearchStartTime() != null, NoticeDO::getCreateTime, notice.getSearchStartTime());
        wrapper.le(notice.getSearchEndTime() != null, NoticeDO::getCreateTime, notice.getSearchEndTime());
        if (StrUtil.isNotBlank(notice.getKeywords())) {
            wrapper.or(w -> w.like(NoticeDO::getName, notice.getKeywords()));
            wrapper.or(w -> w.like(NoticeDO::getDescription, notice.getKeywords()));
        }
        return wrapper;
    }
}
