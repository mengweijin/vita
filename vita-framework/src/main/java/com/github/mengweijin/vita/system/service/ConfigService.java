package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.listener.ConfigChangeListener;
import com.github.mengweijin.vita.system.listener.ConfigChangeListenerFactory;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Config Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class ConfigService extends CrudRepository<ConfigMapper, ConfigDO> {

    @Override
    public boolean updateById(ConfigDO config) {
        boolean updated = super.updateById(config);
        if(updated) {
            ConfigDO configDO = this.getById(config.getId());
            ConfigChangeListener listener = ConfigChangeListenerFactory.getConfigChangeListenerByCode(configDO.getCode());
            listener.run(configDO);
        }
        return updated;
    }

    public LambdaQueryWrapper<ConfigDO> getQueryWrapper(ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(config.getId() != null, ConfigDO::getId, config.getId());
        wrapper.eq(config.getCreateBy() != null, ConfigDO::getCreateBy, config.getCreateBy());
        wrapper.eq(config.getUpdateBy() != null, ConfigDO::getUpdateBy, config.getUpdateBy());
        wrapper.gt(config.getSearchStartTime() != null, ConfigDO::getCreateTime, config.getSearchStartTime());
        wrapper.le(config.getSearchEndTime() != null, ConfigDO::getCreateTime, config.getSearchEndTime());
        if (StrUtil.isNotBlank(config.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(ConfigDO::getName, config.getKeywords()));
                w.or(w1 -> w1.like(ConfigDO::getCode, config.getKeywords()));
                w.or(w1 -> w1.like(ConfigDO::getVal, config.getKeywords()));
            });
        }
        return wrapper;
    }

    public ConfigDO getByCode(String code) {
        return this.lambdaQuery().eq(ConfigDO::getCode, code).one();
    }

    public boolean getCaptchaEnabled() {
        ConfigDO config = this.getByCode(ConfigConst.LOGIN_CAPTCHA_ENABLED);
        return BooleanUtil.toBoolean(config.getVal());
    }

    public boolean getLoginOtpEnabled() {
        ConfigDO config = this.getByCode(ConfigConst.LOGIN_OTP_ENABLED);
        return BooleanUtil.toBoolean(config.getVal());
    }
}
