package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.MessageDO;
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
 *  Message Controller
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

    private MessageService messageService;

    /**
     * <p>
     * Get Message page by Message
     * </p>
     * @param page page
     * @param message {@link MessageDO}
     * @return Page<Message>
     */
    @SaCheckPermission("system:message:select")
    @GetMapping("/page")
    public IPage<MessageDO> page(Page<MessageDO> page, MessageDO message) {
        LambdaQueryWrapper<MessageDO> wrapper = messageService.getQueryWrapper(message);
        return messageService.page(page, wrapper);
    }

    /**
     * <p>
     * Get Message list by Message
     * </p>
     * @param message {@link MessageDO}
     * @return List<Message>
     */
    @SaCheckPermission("system:message:select")
    @GetMapping("/list")
    public List<MessageDO> list(MessageDO message) {
        return messageService.list(new LambdaQueryWrapper<>(message));
    }

    /**
     * <p>
     * Get Message by id
     * </p>
     * @param id id
     * @return Message
     */
    @SaCheckPermission("system:message:select")
    @GetMapping("/{id}")
    public MessageDO getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    /**
     * <p>
     * Add Message
     * </p>
     * @param message {@link MessageDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:message:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody MessageDO message) {
        boolean bool = messageService.save(message);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Message
     * </p>
     * @param message {@link MessageDO}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:message:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody MessageDO message) {
        boolean bool = messageService.updateById(message);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Message by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:message:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(messageService.removeByIds(Arrays.asList(ids)));
    }

}

