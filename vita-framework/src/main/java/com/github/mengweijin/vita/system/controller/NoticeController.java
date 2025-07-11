package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.NoticeDO;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.service.NoticeService;
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
 *  Notice Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/notice")
public class NoticeController {

    private static final String LOG_TITLE = "通知公告";

    private NoticeService noticeService;

    /**
     * <p>
     * Get Notice page by Notice
     * </p>
     * @param page page
     * @param notice {@link NoticeDO}
     * @return Page<Notice>
     */
    @SaCheckPermission("system:notice:select")
    @GetMapping("/page")
    public IPage<NoticeDO> page(Page<NoticeDO> page, NoticeDO notice) {
        LambdaQueryWrapper<NoticeDO> wrapper = noticeService.getQueryWrapper(notice);
        return noticeService.page(page, wrapper);
    }

    /**
     * <p>
     * Get Notice list by Notice
     * </p>
     * @param notice {@link NoticeDO}
     * @return List<Notice>
     */
    @SaCheckPermission("system:notice:select")
    @GetMapping("/list")
    public List<NoticeDO> list(NoticeDO notice) {
        return noticeService.list(new LambdaQueryWrapper<>(notice));
    }

    /**
     * <p>
     * Get Notice by id
     * </p>
     * @param id id
     * @return Notice
     */
    @SaCheckPermission("system:notice:select")
    @GetMapping("/{id}")
    public NoticeDO getById(@PathVariable("id") Long id) {
        return noticeService.getById(id);
    }

    /**
     * <p>
     * Add Notice
     * </p>
     * @param notice {@link NoticeDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:notice:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody NoticeDO notice) {
        boolean bool = noticeService.save(notice);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Notice
     * </p>
     * @param notice {@link NoticeDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody NoticeDO notice) {
        boolean bool = noticeService.updateById(notice);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Notice by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:notice:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(noticeService.removeByIds(Arrays.asList(ids)));
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:release")
    @PostMapping("/release/{id}")
    public R<Void> release(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(NoticeDO::getReleased, EYesNo.Y.getValue()).eq(NoticeDO::getId, id).update();
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:notice:revocation")
    @PostMapping("/revoke/{id}")
    public R<Void> revoke(@PathVariable("id") Long id) {
        boolean bool = noticeService.lambdaUpdate().set(NoticeDO::getReleased, EYesNo.N.getValue()).eq(NoticeDO::getId, id).update();
        return R.result(bool);
    }
}

