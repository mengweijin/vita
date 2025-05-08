package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.core.util.BooleanUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public LambdaQueryWrapper<ConfigDO> getQueryWrapper(ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!Objects.isNull(config.getId()), ConfigDO::getId, config.getId());
        wrapper.eq(!Objects.isNull(config.getCreateBy()), ConfigDO::getCreateBy, config.getCreateBy());
        wrapper.eq(!Objects.isNull(config.getUpdateBy()), ConfigDO::getUpdateBy, config.getUpdateBy());
        wrapper.gt(!Objects.isNull(config.getSearchStartTime()), ConfigDO::getCreateTime, config.getSearchStartTime());
        wrapper.le(!Objects.isNull(config.getSearchEndTime()), ConfigDO::getCreateTime, config.getSearchEndTime());
        if (StrValidator.isNotBlank(config.getKeywords())) {
            wrapper.or(w -> w.like(ConfigDO::getName, config.getKeywords()));
            wrapper.or(w -> w.like(ConfigDO::getCode, config.getKeywords()));
            wrapper.or(w -> w.like(ConfigDO::getVal, config.getKeywords()));
        }
        return wrapper;
    }

    public ConfigDO getByCode(String code) {
        return this.lambdaQuery().eq(ConfigDO::getCode, code).one();
    }

    public boolean getCaptchaEnabled() {
        ConfigDO config = this.getByCode(ConfigConst.CAPTCHA_ENABLED);
        return BooleanUtil.toBoolean(config.getVal());
    }
}
