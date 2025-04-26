package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * Custom paging query
     * @param page page
     * @param notice {@link NoticeDO}
     * @return IPage
     */
    public IPage<NoticeDO> page(IPage<NoticeDO> page, NoticeDO notice){
        LambdaQueryWrapper<NoticeDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(notice.getName()), NoticeDO::getName, notice.getName())
                .eq(StrValidator.isNotBlank(notice.getDescription()), NoticeDO::getDescription, notice.getDescription())
                .eq(StrValidator.isNotBlank(notice.getReleased()), NoticeDO::getReleased, notice.getReleased())
                .eq(!Objects.isNull(notice.getId()), NoticeDO::getId, notice.getId())
                .eq(!Objects.isNull(notice.getCreateBy()), NoticeDO::getCreateBy, notice.getCreateBy())
                .eq(!Objects.isNull(notice.getCreateTime()), NoticeDO::getCreateTime, notice.getCreateTime())
                .eq(!Objects.isNull(notice.getUpdateBy()), NoticeDO::getUpdateBy, notice.getUpdateBy())
                .eq(!Objects.isNull(notice.getUpdateTime()), NoticeDO::getUpdateTime, notice.getUpdateTime());
        return this.page(page, query);
    }
}
