package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.thread.ThreadUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.enums.dict.EMessageType;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.sse.SseConnector;
import com.github.mengweijin.vita.system.domain.entity.MessageDO;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import com.github.mengweijin.vita.system.mapper.MessageMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * Message Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MessageService extends BaseVitaService<MessageMapper, MessageDO, MessageVO> {

    private final ExecutorService executorService = ThreadUtil.newFixedExecutor(Const.PROCESSORS * 2, "thread-pool-message-", true);
    private MessageReceiverService messageReceiverService;
    private TransactionTemplate transactionTemplate;
    private SseConnector sseConnector;

    @Override
    public LambdaQueryWrapper<MessageDO> buildQueryWrapper(MessageDO message) {
        LambdaQueryWrapper<MessageDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(message.getId() != null, MessageDO::getId, message.getId());
        wrapper.eq(StrUtil.isNotBlank(message.getType()), MessageDO::getType, message.getType());
        wrapper.eq(message.getCreateBy() != null, MessageDO::getCreateBy, message.getCreateBy());
        wrapper.eq(message.getUpdateBy() != null, MessageDO::getUpdateBy, message.getUpdateBy());
        wrapper.gt(message.getStartCreateTime() != null, MessageDO::getCreateTime, message.getStartCreateTime());
        wrapper.le(message.getEndCreateTime() != null, MessageDO::getCreateTime, message.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(message.getTitle()), MessageDO::getTitle, message.getTitle());
        wrapper.like(StrUtil.isNotBlank(message.getContent()), MessageDO::getContent, message.getContent());
        return wrapper;
    }

    public void sendMessageToRole(EMessageType type, String title, String content, Long roleId) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        this.sendMessageToUsersAsync(type, title, content, userIds);
    }

    public void sendMessageToRole(EMessageType type, String title, String content, String roleCode) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleCode(roleCode);
        this.sendMessageToUsersAsync(type, title, content, userIds);
    }

    public void sendMessageToDept(EMessageType type, String title, String content, Long deptId) {
        UserService userService = SpringUtil.getBean(UserService.class);
        Set<Long> userIds = userService.getUserIdsInDeptId(deptId);
        this.sendMessageToUsersAsync(type, title, content, userIds);
    }

    public void sendMessageToPost(EMessageType type, String title, String content, Long postId) {
        UserPostService userPostService = SpringUtil.getBean(UserPostService.class);
        Set<Long> userIds = userPostService.getUserIdsByPostId(postId);
        this.sendMessageToUsersAsync(type, title, content, userIds);
    }


    public void sendMessageToUser(EMessageType category, String title, String content, Long receiveUserId) {
        this.sendMessageToUsersAsync(category, title, content, Set.of(receiveUserId));
    }

    public void sendMessageToUsersAsync(EMessageType type, String title, String content, Set<Long> userIds) {
        Long loginId = LoginHelper.getSessionUserId();
        CompletableFuture.runAsync(() -> transactionTemplate.executeWithoutResult(status -> {
                    MessageDO message = new MessageDO();
                    message.setType(type.getValue());
                    message.setTitle(title);
                    message.setContent(content);

                    message.setCreateBy(loginId);
                    message.setUpdateBy(loginId);
                    this.save(message);

                    if (CollUtil.isEmpty(userIds)) {
                        log.warn("The user id in Set was empty when send message to users! message = {}", message);
                        return;
                    }

                    List<MessageReceiverDO> messageReceiverList = new ArrayList<>();
                    userIds.forEach(userId -> {
                        MessageReceiverDO msgReceiver = new MessageReceiverDO();
                        msgReceiver.setMessageId(message.getId());
                        msgReceiver.setUserId(userId);

                        msgReceiver.setCreateBy(loginId);
                        msgReceiver.setUpdateBy(loginId);
                        messageReceiverList.add(msgReceiver);
                    });
                    messageReceiverService.saveBatch(messageReceiverList, Constants.DEFAULT_BATCH_SIZE);

                    // SSE 通知
                    sseConnector.sendMessage(title, userIds.toArray(new Long[]{}));
                }), executorService)
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

}
