package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.Config;
import com.github.mengweijin.system.service.ConfigService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Validated
@RestController
@RequestMapping("/system/config")
public class ConfigController {

    private ConfigService configService;

    /**
     * <p>
     * Get Config page by Config
     * </p>
     * @param page page
     * @param config {@link Config}
     * @return Page<Config>
     */
    @SaCheckPermission("system:config:query")
    @GetMapping("/page")
    public IPage<Config> page(Page<Config> page, Config config) {
        return configService.page(page, config);
    }

    /**
     * <p>
     * Get Config list by Config
     * </p>
     * @param config {@link Config}
     * @return List<Config>
     */
    @SaCheckPermission("system:config:query")
    @GetMapping("/list")
    public List<Config> list(Config config) {
        return configService.list(new QueryWrapper<>(config));
    }

    /**
     * <p>
     * Get Config by id
     * </p>
     * @param id id
     * @return Config
     */
    @SaCheckPermission("system:config:query")
    @GetMapping("/{id}")
    public Config getById(@PathVariable("id") Long id) {
        return configService.getById(id);
    }

    /**
     * <p>
     * Get Config by code
     * </p>
     * @param code code
     * @return Config
     */
    @SaCheckPermission("system:config:query")
    @GetMapping("/code/{code}")
    public Config getByCode(@PathVariable("code") String code) {
        return configService.getByCode(code);
    }
    /**
     * <p>
     * Add Config
     * </p>
     * @param config {@link Config}
     */
    @SaCheckPermission("system:config:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody Config config) {
        boolean bool = configService.save(config);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Config
     * </p>
     * @param config {@link Config}
     */
    @SaCheckPermission("system:config:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody Config config) {
        boolean bool = configService.updateById(config);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Config by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:config:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = configService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

