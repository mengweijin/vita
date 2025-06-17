package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.monitor.domain.entity.LogDO;
import com.github.mengweijin.vita.monitor.service.LogSystemService;
import jakarta.validation.Valid;
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
 *  Log-System Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/log-system")
public class LogSystemController {

    private static final String LOG_TITLE = "系统日志";

    private LogSystemService logSystemService;

    /**
     * <p>
     * Get Log-System page by LogDO
     * </p>
     * @param page page
     * @param logDO {@link LogDO}
     * @return Page<LogDO>
     */
    @SaCheckPermission("monitor:logSystem:select")
    @GetMapping("/page")
    public IPage<LogDO> page(Page<LogDO> page, LogDO logDO) {
        LambdaQueryWrapper<LogDO> wrapper = logSystemService.getQueryWrapper(logDO);
        wrapper.orderByAsc(LogDO::getCreateTime);
        return logSystemService.page(page, wrapper);
    }

    /**
     * <p>
     * Get Log-System list by LogDO
     * </p>
     * @param logDO {@link LogDO}
     * @return List<LogDO>
     */
    @SaCheckPermission("monitor:logSystem:select")
    @GetMapping("/list")
    public List<LogDO> list(LogDO logDO) {
        return logSystemService.list(new LambdaQueryWrapper<>(logDO));
    }

    /**
     * <p>
     * Get Log-System by id
     * </p>
     * @param id id
     * @return LogDO
     */
    @SaCheckPermission("monitor:logSystem:select")
    @GetMapping("/{id}")
    public LogDO getById(@PathVariable("id") Long id) {
        return logSystemService.getById(id);
    }

    /**
     * <p>
     * Add Log-System
     * </p>
     * @param logDO {@link LogDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:logSystem:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogDO logDO) {
        boolean bool = logSystemService.save(logDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Log-System
     * </p>
     * @param logDO {@link LogDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:logSystem:update")
    @PostMapping("update")
    public R<Void> update(@Valid @RequestBody LogDO logDO) {
        boolean bool = logSystemService.updateById(logDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Log-System by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:logSystem:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        int i = logSystemService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.result(i);
    }

}

