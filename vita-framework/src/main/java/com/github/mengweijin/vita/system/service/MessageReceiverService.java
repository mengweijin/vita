package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.domain.vo.MessageReceiverVO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.system.mapper.MessageReceiverMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * MessageReceiver Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class MessageReceiverService extends BaseVitaService<MessageReceiverMapper, MessageReceiverDO, MessageReceiverVO> {

    @Override
    public LambdaQueryWrapper<MessageReceiverDO> buildQueryWrapper(MessageReceiverDO messageReceiver) {
        LambdaQueryWrapper<MessageReceiverDO> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(messageReceiver.getId() != null, MessageReceiverDO::getId, messageReceiver.getId());
        wrapper.eq(messageReceiver.getMessageId() != null, MessageReceiverDO::getMessageId, messageReceiver.getMessageId());
        wrapper.eq(messageReceiver.getUserId() != null, MessageReceiverDO::getUserId, messageReceiver.getUserId());
        wrapper.eq(StrUtil.isNotBlank(messageReceiver.getViewed()), MessageReceiverDO::getViewed, messageReceiver.getViewed());
        wrapper.eq(messageReceiver.getCreateBy() != null, MessageReceiverDO::getCreateBy, messageReceiver.getCreateBy());
        wrapper.eq(messageReceiver.getUpdateBy() != null, MessageReceiverDO::getUpdateBy, messageReceiver.getUpdateBy());
        wrapper.gt(messageReceiver.getStartCreateTime() != null, MessageReceiverDO::getCreateTime, messageReceiver.getStartCreateTime());
        wrapper.le(messageReceiver.getEndCreateTime() != null, MessageReceiverDO::getCreateTime, messageReceiver.getEndCreateTime());
        return wrapper;
    }

    public Long selectNotViewedCount() {
        Long userId = LoginHelper.getSessionUserId();
        return this.lambdaQuery()
                .eq(MessageReceiverDO::getUserId, userId)
                .eq(MessageReceiverDO::getViewed, EYesNo.N.getValue())
                .count();
    }

    public PageQuery<MessageVO> page(PageQuery<MessageVO> pageQuery, MessageVO message) {
        IPage<MessageVO> page = this.getBaseMapper().page(pageQuery.toPage(), message);
        return PageQuery.of(page);
    }

    public boolean setViewed(List<Long> ids) {
        List<Long> unViewedIds = this.lambdaQuery()
                .select(MessageReceiverDO::getId)
                .eq(MessageReceiverDO::getViewed, EYesNo.N.getValue())
                .in(MessageReceiverDO::getId, ids)
                .list().stream().map(MessageReceiverDO::getId).toList();
        return this.lambdaUpdate()
                .set(MessageReceiverDO::getViewed, EYesNo.Y.getValue())
                .set(MessageReceiverDO::getViewedTime, LocalDateTime.now())
                .in(MessageReceiverDO::getId, unViewedIds)
                .update();
    }

    public boolean setNotViewed(List<Long> ids) {
        return this.lambdaUpdate()
                .set(MessageReceiverDO::getViewed, EYesNo.N.getValue())
                .set(MessageReceiverDO::getViewedTime, null)
                .in(MessageReceiverDO::getId, ids)
                .update();
    }
}
