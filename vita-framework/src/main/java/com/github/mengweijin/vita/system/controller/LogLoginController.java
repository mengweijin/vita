package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.util.BeanCopyUtils;
import com.github.mengweijin.vita.system.domain.vo.LogLoginDOVO;
import com.github.mengweijin.vita.system.domain.entity.LogLoginDO;
import com.github.mengweijin.vita.system.service.LogLoginService;
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
 *  LogLogin Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/log-login")
public class LogLoginController {

    private LogLoginService logLoginService;

    /**
     * <p>
     * Get LogLogin page by LogLogin
     * </p>
     * @param page page
     * @param logLogin {@link LogLoginDO}
     * @return Page<LogLogin>
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/page")
    public IPage<LogLoginDOVO> page(Page<LogLoginDO> page, LogLoginDO logLogin) {
        IPage<LogLoginDO> paged = logLoginService.page(page, logLogin);
        return BeanCopyUtils.copyPage(paged, LogLoginDOVO.class);
    }

    /**
     * <p>
     * Get LogLogin list by LogLogin
     * </p>
     * @param logLogin {@link LogLoginDO}
     * @return List<LogLogin>
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/list")
    public List<LogLoginDO> list(LogLoginDO logLogin) {
        return logLoginService.list(new LambdaQueryWrapper<>(logLogin));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return LogLogin
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/{id}")
    public LogLoginDO getById(@PathVariable("id") Long id) {
        return logLoginService.getById(id);
    }

    /**
     * <p>
     * Add LogLogin
     * </p>
     * @param logLogin {@link LogLoginDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:logLogin:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogLoginDO logLogin) {
        boolean bool = logLoginService.save(logLogin);
        return R.result(bool);
    }

    /**
     * <p>
     * Update LogLogin
     * </p>
     * @param logLogin {@link LogLoginDO}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:logLogin:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody LogLoginDO logLogin) {
        boolean bool = logLoginService.updateById(logLogin);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete LogLogin by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:logLogin:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logLoginService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.result(i);
    }

}

