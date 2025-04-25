package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.ConfigDO;
import com.github.mengweijin.vita.system.service.ConfigService;
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
 *  Config Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/config")
public class ConfigController {

    private ConfigService configService;

    /**
     * <p>
     * Get Config page by Config
     * </p>
     * @param page page
     * @param config {@link ConfigDO}
     * @return Page<Config>
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/page")
    public IPage<ConfigDO> page(Page<ConfigDO> page, ConfigDO config) {
        return configService.page(page, config);
    }

    /**
     * <p>
     * Get Config list by Config
     * </p>
     * @param config {@link ConfigDO}
     * @return List<Config>
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/list")
    public List<ConfigDO> list(ConfigDO config) {
        return configService.list(new LambdaQueryWrapper<>(config));
    }

    /**
     * <p>
     * Get Config by id
     * </p>
     * @param id id
     * @return Config
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/{id}")
    public ConfigDO getById(@PathVariable("id") Long id) {
        return configService.getById(id);
    }

    /**
     * <p>
     * Get Config by code
     * </p>
     * @param code code
     * @return Config
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/code/{code}")
    public ConfigDO getByCode(@PathVariable("code") String code) {
        return configService.getByCode(code);
    }
    /**
     * <p>
     * Add Config
     * </p>
     * @param config {@link ConfigDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:config:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody ConfigDO config) {
        boolean bool = configService.save(config);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Config
     * </p>
     * @param config {@link ConfigDO}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:config:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody ConfigDO config) {
        boolean bool = configService.updateById(config);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Config by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:config:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(configService.removeByIds(Arrays.asList(ids)));
    }

}

