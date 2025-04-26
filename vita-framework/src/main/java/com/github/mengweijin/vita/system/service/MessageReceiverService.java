package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * Custom paging query
     *
     * @param page            page
     * @param messageReceiver {@link MessageReceiverDO}
     * @return IPage
     */
    public IPage<MessageReceiverDO> page(IPage<MessageReceiverDO> page, MessageReceiverDO messageReceiver) {
        LambdaQueryWrapper<MessageReceiverDO> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(messageReceiver.getMessageId()), MessageReceiverDO::getMessageId, messageReceiver.getMessageId())
                .eq(!Objects.isNull(messageReceiver.getUserId()), MessageReceiverDO::getUserId, messageReceiver.getUserId())
                .eq(StrValidator.isNotBlank(messageReceiver.getViewed()), MessageReceiverDO::getViewed, messageReceiver.getViewed())
                .eq(!Objects.isNull(messageReceiver.getViewedTime()), MessageReceiverDO::getViewedTime, messageReceiver.getViewedTime())
                .eq(!Objects.isNull(messageReceiver.getId()), MessageReceiverDO::getId, messageReceiver.getId())
                .eq(!Objects.isNull(messageReceiver.getCreateBy()), MessageReceiverDO::getCreateBy, messageReceiver.getCreateBy())
                .eq(!Objects.isNull(messageReceiver.getCreateTime()), MessageReceiverDO::getCreateTime, messageReceiver.getCreateTime())
                .eq(!Objects.isNull(messageReceiver.getUpdateBy()), MessageReceiverDO::getUpdateBy, messageReceiver.getUpdateBy())
                .eq(!Objects.isNull(messageReceiver.getUpdateTime()), MessageReceiverDO::getUpdateTime, messageReceiver.getUpdateTime());
        return this.page(page, query);
    }
}
