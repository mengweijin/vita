package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.ConfigDO;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.core.util.BooleanUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  Config Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class ConfigService extends CrudRepository<ConfigMapper, ConfigDO> {

    /**
     * Custom paging query
     * @param page page
     * @param config {@link ConfigDO}
     * @return IPage
     */
    public IPage<ConfigDO> page(IPage<ConfigDO> page, ConfigDO config){
        LambdaQueryWrapper<ConfigDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(config.getVal()), ConfigDO::getVal, config.getVal())
                .eq(StrValidator.isNotBlank(config.getRemark()), ConfigDO::getRemark, config.getRemark())
                .eq(!Objects.isNull(config.getId()), ConfigDO::getId, config.getId())
                .eq(!Objects.isNull(config.getCreateBy()), ConfigDO::getCreateBy, config.getCreateBy())
                .eq(!Objects.isNull(config.getCreateTime()), ConfigDO::getCreateTime, config.getCreateTime())
                .eq(!Objects.isNull(config.getUpdateBy()), ConfigDO::getUpdateBy, config.getUpdateBy())
                .eq(!Objects.isNull(config.getUpdateTime()), ConfigDO::getUpdateTime, config.getUpdateTime())
                .like(StrValidator.isNotBlank(config.getName()), ConfigDO::getName, config.getName())
                .like(StrValidator.isNotBlank(config.getCode()), ConfigDO::getCode, config.getCode());
        return this.page(page, query);
    }

    public ConfigDO getByCode(String code) {
        return this.lambdaQuery().eq(ConfigDO::getCode, code).one();
    }

    public boolean getCaptchaEnabled() {
        ConfigDO config = this.getByCode(ConfigConst.CAPTCHA_ENABLED);
        return BooleanUtil.toBoolean(config.getVal());
    }
}
