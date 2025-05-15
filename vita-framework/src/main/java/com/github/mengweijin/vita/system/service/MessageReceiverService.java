package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.mapper.MessageReceiverMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

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
public class MessageReceiverService extends CrudRepository<MessageReceiverMapper, MessageReceiverDO> {

    public LambdaQueryWrapper<MessageReceiverDO> getQueryWrapper(MessageReceiverDO messageReceiver) {
        LambdaQueryWrapper<MessageReceiverDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(messageReceiver.getId() != null, MessageReceiverDO::getId, messageReceiver.getId());
        wrapper.eq(messageReceiver.getMessageId() != null, MessageReceiverDO::getMessageId, messageReceiver.getMessageId());
        wrapper.eq(messageReceiver.getUserId() != null, MessageReceiverDO::getUserId, messageReceiver.getUserId());
        wrapper.eq(StrUtil.isNotBlank(messageReceiver.getViewed()), MessageReceiverDO::getViewed, messageReceiver.getViewed());
        wrapper.eq(messageReceiver.getCreateBy() != null, MessageReceiverDO::getCreateBy, messageReceiver.getCreateBy());
        wrapper.eq(messageReceiver.getUpdateBy() != null, MessageReceiverDO::getUpdateBy, messageReceiver.getUpdateBy());
        wrapper.gt(messageReceiver.getSearchStartTime() != null, MessageReceiverDO::getCreateTime, messageReceiver.getSearchStartTime());
        wrapper.le(messageReceiver.getSearchEndTime() != null, MessageReceiverDO::getCreateTime, messageReceiver.getSearchEndTime());
        return wrapper;
    }
}
