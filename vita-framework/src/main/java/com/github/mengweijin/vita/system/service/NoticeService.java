package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.NoticeDO;
import com.github.mengweijin.vita.system.domain.vo.NoticeVO;
import com.github.mengweijin.vita.system.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
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
public class NoticeService extends BaseVitaService<NoticeMapper, NoticeDO, NoticeVO> {

    @Override
    public LambdaQueryWrapper<NoticeDO> buildQueryWrapper(NoticeDO notice) {
        LambdaQueryWrapper<NoticeDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(notice.getId() != null, NoticeDO::getId, notice.getId());
        wrapper.eq(StrUtil.isNotBlank(notice.getReleased()), NoticeDO::getReleased, notice.getReleased());
        wrapper.eq(notice.getCreateBy() != null, NoticeDO::getCreateBy, notice.getCreateBy());
        wrapper.eq(notice.getUpdateBy() != null, NoticeDO::getUpdateBy, notice.getUpdateBy());
        wrapper.gt(notice.getStartCreateTime() != null, NoticeDO::getCreateTime, notice.getStartCreateTime());
        wrapper.le(notice.getEndCreateTime() != null, NoticeDO::getCreateTime, notice.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(notice.getTitle()), NoticeDO::getTitle, notice.getTitle());
        wrapper.like(StrUtil.isNotBlank(notice.getDescription()), NoticeDO::getDescription, notice.getDescription());
        return wrapper;
    }
}
