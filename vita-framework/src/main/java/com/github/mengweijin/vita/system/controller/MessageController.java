package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.MessageBO;
import com.github.mengweijin.vita.system.domain.entity.MessageDO;
import com.github.mengweijin.vita.system.domain.vo.MessageVO;
import com.github.mengweijin.vita.system.service.MessageReceiverService;
import com.github.mengweijin.vita.system.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Message Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/message")
public class MessageController {

    private static final String LOG_TITLE = "消息管理";

    private MessageService messageService;

    private MessageReceiverService messageReceiverService;

    @GetMapping("/query/notViewedCount")
    public Long queryNotViewedCount() {
        return messageReceiverService.selectNotViewedCount();
    }

    @PostMapping("/set/viewed/{messageReceiverIds}")
    public R<Void> setViewed(@PathVariable("messageReceiverIds") Long[] messageReceiverIds) {
        boolean bool = messageReceiverService.setViewed(Arrays.asList(messageReceiverIds));
        return R.result(bool);
    }

    @PostMapping("/set/notViewed/{messageReceiverIds}")
    public R<Void> setNotViewed(@PathVariable("messageReceiverIds") Long[] messageReceiverIds) {
        boolean bool = messageReceiverService.setNotViewed(Arrays.asList(messageReceiverIds));
        return R.result(bool);
    }

    /**
     * <p>
     * Get Message page by Message
     * </p>
     *
     * @param page    page
     * @param message {@link MessageVO}
     * @return Page<MessageVO>
     */
    @SaCheckPermission("system:message:select")
    @GetMapping("/page")
    public PageQuery<MessageVO> page(PageQuery<MessageVO> page, MessageVO message) {
        message.setUserId(LoginHelper.getSessionUserId());
        return messageReceiverService.page(page, message);
    }

    /**
     * <p>
     * Get Message list by Message
     * </p>
     *
     * @param message {@link MessageDO}
     * @return List<Message>
     */
    @SaCheckPermission("system:message:select")
    @GetMapping("/list")
    public List<MessageVO> list(MessageDO message) {
        return messageService.listVo(Wrappers.lambdaQuery(message));
    }

    /**
     * <p>
     * Get Message by id
     * </p>
     *
     * @param id id
     * @return Message
     */
    @GetMapping("/{id}")
    public MessageVO getById(@PathVariable("id") Long id) {
        return messageService.getVoById(id);
    }

    /**
     * <p>
     * Add Message
     * </p>
     *
     * @param message {@link MessageDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:message:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody MessageBO message) {
        boolean bool = messageService.saveByBo(message);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Message
     * </p>
     *
     * @param message {@link MessageDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:message:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody MessageBO message) {
        boolean bool = messageService.updateByBoById(message);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Message by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:message:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(messageService.removeByIds(Arrays.asList(ids)));
    }

}

