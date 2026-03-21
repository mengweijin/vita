package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.monitor.domain.bo.LogDataChangeBO;
import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogDataChangeVO;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
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
 *  Log-Data-Change Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/log-data-change")
public class LogDataChangeController {

    private static final String LOG_TITLE = "数据变动日志";

    private LogDataChangeService logDataChangeService;

    /**
     * <p>
     * Get Log-Data-Change page by LogDO
     * </p>
     * @param page page
     * @param logDataChangeDO {@link LogDataChangeDO}
     * @return Page<LogDO>
     */
    @SaCheckPermission("monitor:logDataChange:select")
    @GetMapping("/page")
    public PageQuery<LogDataChangeVO> page(PageQuery<LogDataChangeDO> page, LogDataChangeDO logDataChangeDO) {
        LambdaQueryWrapper<LogDataChangeDO> wrapper = logDataChangeService.buildQueryWrapper(logDataChangeDO);
        wrapper.orderByDesc(LogDataChangeDO::getCreateTime);
        return logDataChangeService.pageVo(page, wrapper);
    }

    /**
     * <p>
     * Get Log-Data-Change list by LogDO
     * </p>
     * @param logDataChangeDO {@link LogDataChangeDO}
     * @return List<LogDO>
     */
    @SaCheckPermission("monitor:logDataChange:select")
    @GetMapping("/list")
    public List<LogDataChangeVO> list(LogDataChangeDO logDataChangeDO) {
        return logDataChangeService.listVo(Wrappers.lambdaQuery(logDataChangeDO));
    }

    /**
     * <p>
     * Get Log-Data-Change by id
     * </p>
     * @param id id
     * @return LogDO
     */
    @GetMapping("/{id}")
    public LogDataChangeVO getById(@PathVariable("id") Long id) {
        return logDataChangeService.getVoById(id);
    }

    @GetMapping("/list/tableNames")
    public List<String> listTableNames() {
        return logDataChangeService.getTableNames();
    }

    /**
     * <p>
     * Add Log-Data-Change
     * </p>
     * @param bo {@link LogDataChangeBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:logDataChange:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogDataChangeBO bo) {
        boolean bool = logDataChangeService.saveByBo(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Log-Data-Change
     * </p>
     * @param bo {@link LogDataChangeBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:logDataChange:update")
    @PostMapping("update")
    public R<Void> update(@Valid @RequestBody LogDataChangeBO bo) {
        boolean bool = logDataChangeService.updateByBoById(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Log-Data-Change by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:logDataChange:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        int i = logDataChangeService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.result(i);
    }

}

