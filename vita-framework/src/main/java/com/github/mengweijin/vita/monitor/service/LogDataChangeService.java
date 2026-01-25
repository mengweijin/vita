package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.log.operation.EOperationType;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.ObjectMapperUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogDataChangeVO;
import com.github.mengweijin.vita.monitor.mapper.LogDataChangeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * LogDataChange Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogDataChangeService extends BaseVitaService<LogDataChangeMapper, LogDataChangeDO, LogDataChangeVO> {

    @Override
    public LambdaQueryWrapper<LogDataChangeDO> buildQueryWrapper(LogDataChangeDO logDataChangeDO) {
        LambdaQueryWrapper<LogDataChangeDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(logDataChangeDO.getId() != null, LogDataChangeDO::getId, logDataChangeDO.getId());

        wrapper.eq(StrValidator.isNotBlank(logDataChangeDO.getTableName()), LogDataChangeDO::getTableName, logDataChangeDO.getTableName());
        wrapper.eq(logDataChangeDO.getBusinessId() != null, LogDataChangeDO::getBusinessId, logDataChangeDO.getBusinessId());
        wrapper.eq(StrValidator.isNotBlank(logDataChangeDO.getOperationType()), LogDataChangeDO::getOperationType, logDataChangeDO.getOperationType());

        wrapper.eq(logDataChangeDO.getCreateBy() != null, LogDataChangeDO::getCreateBy, logDataChangeDO.getCreateBy());
        wrapper.eq(logDataChangeDO.getUpdateBy() != null, LogDataChangeDO::getUpdateBy, logDataChangeDO.getUpdateBy());
        wrapper.gt(logDataChangeDO.getStartCreateTime() != null, LogDataChangeDO::getCreateTime, logDataChangeDO.getStartCreateTime());
        wrapper.le(logDataChangeDO.getEndCreateTime() != null, LogDataChangeDO::getCreateTime, logDataChangeDO.getEndCreateTime());

        wrapper.like(StrValidator.isNotBlank(logDataChangeDO.getRemark()), LogDataChangeDO::getRemark, logDataChangeDO.getRemark());
        return wrapper;
    }

    public <K, V> void saveWhenChange(String tableName, Long businessId, EOperationType operationType, Map<K, V> beforeData, Map<K, V> afterData, String... ignoreFields) {
        LogDataChangeDO logDataChangeDO = new LogDataChangeDO();
        logDataChangeDO.setTableName(tableName);
        logDataChangeDO.setBusinessId(businessId);
        logDataChangeDO.setOperationType(operationType.name());
        logDataChangeDO.setBeforeData(ObjectMapperUtils.getObjectMapperWrapper().valueToTree(beforeData));
        logDataChangeDO.setAfterData(ObjectMapperUtils.getObjectMapperWrapper().valueToTree(afterData));

        // List<DiffModel<?,?>> changeData = DiffUtils.diffMaps(beforeData, afterData);
        // logDataChangeDO.setChangeData(changeData);

        // if(changeData.isEmpty()) {
        //     log.info("No data change, no need to save logDataChangeDO.");
        // } else {
        //     // 保存日志
        //     AopUtils.getAopProxy(this).save(logDataChangeDO);
        // }
    }
}
