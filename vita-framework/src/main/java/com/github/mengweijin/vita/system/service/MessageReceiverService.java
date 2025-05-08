package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.mapper.MessageReceiverMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
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
public class MessageReceiverService extends CrudRepository<MessageReceiverMapper, MessageReceiverDO> {

    public LambdaQueryWrapper<MessageReceiverDO> getQueryWrapper(MessageReceiverDO messageReceiver) {
        LambdaQueryWrapper<MessageReceiverDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(!Objects.isNull(messageReceiver.getId()), MessageReceiverDO::getId, messageReceiver.getId());
        wrapper.eq(!Objects.isNull(messageReceiver.getMessageId()), MessageReceiverDO::getMessageId, messageReceiver.getMessageId());
        wrapper.eq(!Objects.isNull(messageReceiver.getUserId()), MessageReceiverDO::getUserId, messageReceiver.getUserId());
        wrapper.eq(StrValidator.isNotBlank(messageReceiver.getViewed()), MessageReceiverDO::getViewed, messageReceiver.getViewed());
        wrapper.eq(!Objects.isNull(messageReceiver.getCreateBy()), MessageReceiverDO::getCreateBy, messageReceiver.getCreateBy());
        wrapper.eq(!Objects.isNull(messageReceiver.getUpdateBy()), MessageReceiverDO::getUpdateBy, messageReceiver.getUpdateBy());
        wrapper.gt(!Objects.isNull(messageReceiver.getSearchStartTime()), MessageReceiverDO::getCreateTime, messageReceiver.getSearchStartTime());
        wrapper.le(!Objects.isNull(messageReceiver.getSearchEndTime()), MessageReceiverDO::getCreateTime, messageReceiver.getSearchEndTime());
        return wrapper;
    }
}
