package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.domain.entity.MessageDO;
import com.github.mengweijin.vita.system.domain.entity.MessageReceiverDO;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.mapper.MessageMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
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
public class MessageService extends CrudRepository<MessageMapper, MessageDO> {

    private MessageReceiverService messageReceiverService;

    private TransactionTemplate transactionTemplate;

    private final ExecutorService executorService = ThreadUtil.newFixedExecutor(Const.PROCESSORS * 2, "thread-pool-message-", true);

    public LambdaQueryWrapper<MessageDO> getQueryWrapper(MessageDO message) {
        LambdaQueryWrapper<MessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(message.getId() != null, MessageDO::getId, message.getId());
        wrapper.eq(StrUtil.isNotBlank(message.getCategory()), MessageDO::getCategory, message.getCategory());
        wrapper.eq(message.getCreateBy() != null, MessageDO::getCreateBy, message.getCreateBy());
        wrapper.eq(message.getUpdateBy() != null, MessageDO::getUpdateBy, message.getUpdateBy());
        wrapper.gt(message.getSearchStartTime() != null, MessageDO::getCreateTime, message.getSearchStartTime());
        wrapper.le(message.getSearchEndTime() != null, MessageDO::getCreateTime, message.getSearchEndTime());
        if (StrUtil.isNotBlank(message.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(MessageDO::getTitle, message.getKeywords()));
                w.or(w1 -> w1.like(MessageDO::getContent, message.getKeywords()));
            });
        }
        return wrapper;
    }

    public void sendMessageToRole(EMessageCategory category, String title, String content, Long roleId) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        this.sendMessageToUsersAsync(category, title, content, userIds);
    }

    public void sendMessageToRole(EMessageCategory category, String title, String content, String roleCode) {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> userIds = userRoleService.getUserIdsByRoleCode(roleCode);
        this.sendMessageToUsersAsync(category, title, content, userIds);
    }

    public void sendMessageToDept(EMessageCategory category, String title, String content, Long deptId) {
        UserService userService = SpringUtil.getBean(UserService.class);
        Set<Long> userIds = userService.getUserIdsInDeptId(deptId);
        this.sendMessageToUsersAsync(category, title, content, userIds);
    }

    public void sendMessageToPost(EMessageCategory category, String title, String content, Long postId) {
        UserPostService userPostService = SpringUtil.getBean(UserPostService.class);
        Set<Long> userIds = userPostService.getUserIdsByPostId(postId);
        this.sendMessageToUsersAsync(category, title, content, userIds);
    }


    public void sendMessageToUser(EMessageCategory category, String title, String content, Long receiveUserId) {
        this.sendMessageToUsersAsync(category, title, content, Set.of(receiveUserId));
    }

    public void sendMessageToUsersAsync(EMessageCategory category, String title, String content, Set<Long> userIds) {
        Long loginId = LoginHelper.getLoginUserIdQuietly();
        CompletableFuture.runAsync(() -> transactionTemplate.executeWithoutResult(status -> {
                    MessageDO message = new MessageDO();
                    message.setCategory(category.getValue());
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
                }), executorService)
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

}
