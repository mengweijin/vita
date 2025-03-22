package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiver;
import com.github.mengweijin.vita.system.mapper.MessageReceiverMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
public class MessageReceiverService extends CrudRepository<MessageReceiverMapper, MessageReceiver> {

    /**
     * Custom paging query
     *
     * @param page            page
     * @param messageReceiver {@link MessageReceiver}
     * @return IPage
     */
    public IPage<MessageReceiver> page(IPage<MessageReceiver> page, MessageReceiver messageReceiver) {
        LambdaQueryWrapper<MessageReceiver> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(messageReceiver.getMessageId()), MessageReceiver::getMessageId, messageReceiver.getMessageId())
                .eq(!Objects.isNull(messageReceiver.getUserId()), MessageReceiver::getUserId, messageReceiver.getUserId())
                .eq(StrUtil.isNotBlank(messageReceiver.getViewed()), MessageReceiver::getViewed, messageReceiver.getViewed())
                .eq(!Objects.isNull(messageReceiver.getViewedTime()), MessageReceiver::getViewedTime, messageReceiver.getViewedTime())
                .eq(!Objects.isNull(messageReceiver.getId()), MessageReceiver::getId, messageReceiver.getId())
                .eq(!Objects.isNull(messageReceiver.getCreateBy()), MessageReceiver::getCreateBy, messageReceiver.getCreateBy())
                .eq(!Objects.isNull(messageReceiver.getCreateTime()), MessageReceiver::getCreateTime, messageReceiver.getCreateTime())
                .eq(!Objects.isNull(messageReceiver.getUpdateBy()), MessageReceiver::getUpdateBy, messageReceiver.getUpdateBy())
                .eq(!Objects.isNull(messageReceiver.getUpdateTime()), MessageReceiver::getUpdateTime, messageReceiver.getUpdateTime());
        return this.page(page, query);
    }
}
