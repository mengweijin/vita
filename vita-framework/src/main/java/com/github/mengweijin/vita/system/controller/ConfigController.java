package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.datachange.DataChangeLog;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.ConfigBO;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.vo.ConfigVO;
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
 * Config Controller
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

    private static final String LOG_TITLE = "配置管理";

    private ConfigService configService;

    /**
     * <p>
     * Get Config page by Config
     * </p>
     *
     * @param page   page
     * @param config {@link ConfigDO}
     * @return Page<Config>
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/page")
    public PageQuery<ConfigVO> page(PageQuery<ConfigDO> page, ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = configService.buildQueryWrapper(config);
        wrapper.orderByAsc(ConfigDO::getConfigKey);
        return configService.pageVo(page, wrapper);
    }

    /**
     * <p>
     * Get Config list by Config
     * </p>
     *
     * @param config {@link ConfigDO}
     * @return List<Config>
     */
    @SaCheckPermission("system:config:select")
    @GetMapping("/list")
    public List<ConfigVO> list(ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = configService.buildQueryWrapper(config);
        wrapper.orderByAsc(ConfigDO::getConfigKey);
        return configService.listVo(wrapper);
    }

    /**
     * <p>
     * Get Config by id
     * </p>
     *
     * @param id id
     * @return Config
     */
    @GetMapping("/{id}")
    public ConfigVO getById(@PathVariable("id") Long id) {
        return configService.getVoById(id);
    }

    /**
     * <p>
     * Get Config by code
     * </p>
     *
     * @param code code
     * @return Config
     */
    @GetMapping("/query/by/code/{code}")
    public ConfigVO queryByCode(@PathVariable("code") String code) {
        ConfigDO configDO = configService.getByConfigKey(code);
        return MapstructUtils.getConverter().convert(configDO, ConfigVO.class);
    }

    /**
     * <p>
     * Add Config
     * </p>
     *
     * @param bo {@link ConfigDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:config:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody ConfigBO bo) {
        boolean bool = configService.save(bo);
        // 刷新配置
        configService.publishEnvironmentChangeEvent(bo.getConfigKey());
        return R.result(bool);
    }

    /**
     * <p>
     * Update Config
     * </p>
     *
     * @param bo {@link ConfigDO}
     */
    @DataChangeLog(entityClass = ConfigDO.class, businessId = "#bo.id")
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:config:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody ConfigBO bo) {
        boolean bool = configService.updateById(bo);
        // 刷新配置
        configService.publishEnvironmentChangeEvent(bo.getConfigKey());
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Config by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:config:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(configService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 手动从数据库配置读取，并刷新被 @ConfigurationProperties 注解的类的属性的值
     */
    @SaCheckPermission("system:config:refresh")
    @PostMapping("/refresh")
    public R<Void> refresh() {
        configService.publishEnvironmentChangeEvent();
        return R.ok();
    }

}

