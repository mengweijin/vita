package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.monitor.domain.entity.LogOperationDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogOperationVO;
import com.github.mengweijin.vita.monitor.mapper.LogOperationMapper;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * LogOperation Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogOperationService extends BaseVitaService<LogOperationMapper, LogOperationDO, LogOperationVO> {

    @Async
    @EventListener
    public void saveAsync(LogOperationDO entity) {
        this.save(entity);
    }

    @Override
    public LambdaQueryWrapper<LogOperationDO> buildQueryWrapper(LogOperationDO logOperation) {
        LambdaQueryWrapper<LogOperationDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(logOperation.getId() != null, LogOperationDO::getId, logOperation.getId());

        wrapper.eq(StrValidator.isNotBlank(logOperation.getOperationType()), LogOperationDO::getOperationType, logOperation.getOperationType());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getHttpMethod()), LogOperationDO::getHttpMethod, logOperation.getHttpMethod());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getSuccess()), LogOperationDO::getSuccess, logOperation.getSuccess());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getErrorMsg()), LogOperationDO::getErrorMsg, logOperation.getErrorMsg());

        wrapper.eq(logOperation.getCreateBy() != null, LogOperationDO::getCreateBy, logOperation.getCreateBy());
        wrapper.eq(logOperation.getUpdateBy() != null, LogOperationDO::getUpdateBy, logOperation.getUpdateBy());
        wrapper.gt(logOperation.getStartCreateTime() != null, LogOperationDO::getCreateTime, logOperation.getStartCreateTime());
        wrapper.le(logOperation.getEndCreateTime() != null, LogOperationDO::getCreateTime, logOperation.getEndCreateTime());

        wrapper.like(StrValidator.isNotBlank(logOperation.getTitle()), LogOperationDO::getTitle, logOperation.getTitle());
        wrapper.like(StrValidator.isNotBlank(logOperation.getUrl()), LogOperationDO::getUrl, logOperation.getUrl());
        wrapper.like(StrValidator.isNotBlank(logOperation.getMethodName()), LogOperationDO::getMethodName, logOperation.getMethodName());
        return wrapper;
    }

    public Long getDailyUserOperationCount() {
        LocalDate localDate = LocalDate.now(Const.ZONE);
        LocalDateTime startTime = localDate.atTime(LocalTime.MIN);
        LocalDateTime endTime = localDate.atTime(LocalTime.MAX);
        return this.lambdaQuery().between(LogOperationDO::getCreateTime, startTime, endTime).count();
    }

    public Long getTotalUserOperationCount() {
        return this.lambdaQuery().count();
    }

    public List<HomeConsoleChartDataVO> selectDailyUserOperationCountBetweenTime(LocalDateTime startTime, LocalDateTime endTime) {
        return this.getBaseMapper().selectDailyUserOperationCountBetweenTime(startTime, endTime);
    }

}
