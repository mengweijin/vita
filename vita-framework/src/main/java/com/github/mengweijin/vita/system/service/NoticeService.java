package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.NoticeDO;
import com.github.mengweijin.vita.system.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        wrapper.eq(!Objects.isNull(notice.getId()), NoticeDO::getId, notice.getId());
        wrapper.eq(StrValidator.isNotBlank(notice.getReleased()), NoticeDO::getReleased, notice.getReleased());
        wrapper.eq(!Objects.isNull(notice.getCreateBy()), NoticeDO::getCreateBy, notice.getCreateBy());
        wrapper.eq(!Objects.isNull(notice.getUpdateBy()), NoticeDO::getUpdateBy, notice.getUpdateBy());
        wrapper.gt(!Objects.isNull(notice.getSearchStartTime()), NoticeDO::getCreateTime, notice.getSearchStartTime());
        wrapper.le(!Objects.isNull(notice.getSearchEndTime()), NoticeDO::getCreateTime, notice.getSearchEndTime());
        if (StrValidator.isNotBlank(notice.getKeywords())) {
            wrapper.or(w -> w.like(NoticeDO::getName, notice.getKeywords()));
            wrapper.or(w -> w.like(NoticeDO::getDescription, notice.getKeywords()));
        }
        return wrapper;
    }
}
