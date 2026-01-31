package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.DiffUtils;
import com.github.mengweijin.vita.framework.util.ObjectMapperUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogDataChangeVO;
import com.github.mengweijin.vita.monitor.mapper.LogDataChangeMapper;
import com.github.mengweijin.vita.framework.log.datachange.strategy.IHumanReadableStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

        wrapper.eq(logDataChangeDO.getCreateBy() != null, LogDataChangeDO::getCreateBy, logDataChangeDO.getCreateBy());
        wrapper.eq(logDataChangeDO.getUpdateBy() != null, LogDataChangeDO::getUpdateBy, logDataChangeDO.getUpdateBy());
        wrapper.gt(logDataChangeDO.getStartCreateTime() != null, LogDataChangeDO::getCreateTime, logDataChangeDO.getStartCreateTime());
        wrapper.le(logDataChangeDO.getEndCreateTime() != null, LogDataChangeDO::getCreateTime, logDataChangeDO.getEndCreateTime());
        return wrapper;
    }

    public void saveWhenListChange(String tableName, Long businessId, IHumanReadableStrategy readableStrategy, List<?> beforeData, List<?> afterData) {
        this.checkDataClassEquals(beforeData, afterData);
        List<DiffModel> changeData = DiffUtils.diffLists(beforeData, afterData);
        this.saveChanges(tableName, businessId, beforeData, afterData, changeData, readableStrategy);
    }

    public void saveWhenMapChange(String tableName, Long businessId, IHumanReadableStrategy readableStrategy, Map<String, String> beforeData, Map<String, String> afterData, String... ignoreKeys) {
        this.checkDataClassEquals(beforeData, afterData);
        List<DiffModel> changeData = DiffUtils.diffMaps(beforeData, afterData, ignoreKeys);
        this.saveChanges(tableName, businessId, beforeData, afterData, changeData, readableStrategy);
    }

    public void saveWhenBeanChange(String tableName, Long businessId, IHumanReadableStrategy readableStrategy, Object beforeData, Object afterData, String... ignoreFields) {
        this.checkDataClassEquals(beforeData, afterData);
        List<DiffModel> changeData = DiffUtils.diffBeans(beforeData, afterData, ignoreFields);
        this.saveChanges(tableName, businessId, beforeData, afterData, changeData, readableStrategy);
    }

    public void setHumanReadable(List<LogDataChangeVO> voList) {
        for (LogDataChangeVO vo : voList) {
            IHumanReadableStrategy strategy = SpringUtil.getBean(vo.getReadableStrategy(), IHumanReadableStrategy.class);
            // JsonNode changeDataJsonNode = vo.getChangeData();
            // List<DiffModel> changeData = ObjectMapperUtils.getObjectMapperWrapper().treeToValue(changeDataJsonNode, List.class);
            List<String> humanReadable = strategy.toHumanReadable(vo.getChangeData());
            vo.setReadableMessages(humanReadable);
        }
    }

    private void checkDataClassEquals(Object beforeData, Object afterData) throws ServerException {
        if(!beforeData.getClass().equals(afterData.getClass())) {
            String message = StrUtil.format("beforeData and afterData must be the same class. beforeData: {}, afterData: {}", beforeData.getClass(), afterData.getClass());
            log.error(message);
            throw new ServerException(message);
        }
    }

    private LogDataChangeDO buildLogDataChangeDO(String tableName, Long businessId, Object beforeObject, Object afterObject, List<DiffModel> changeData, IHumanReadableStrategy readableStrategy) {
        Long sessionUserId = LoginHelper.getSessionUserId();
        LocalDateTime dateTime = LocalDateTime.now();

        LogDataChangeDO logDataChangeDO = new LogDataChangeDO();
        logDataChangeDO.setTableName(tableName);
        logDataChangeDO.setBusinessId(businessId);
        logDataChangeDO.setBeforeData(ObjectMapperUtils.getObjectMapperWrapper().valueToTree(beforeObject));
        logDataChangeDO.setAfterData(ObjectMapperUtils.getObjectMapperWrapper().valueToTree(afterObject));
        logDataChangeDO.setChangeData(changeData);
        logDataChangeDO.setReadableStrategy(readableStrategy.getBeanName());
        logDataChangeDO.setCreateBy(sessionUserId);
        logDataChangeDO.setCreateTime(dateTime);
        logDataChangeDO.setUpdateBy(sessionUserId);
        logDataChangeDO.setUpdateTime(dateTime);
        return logDataChangeDO;
    }

    private void saveChanges(String tableName, Long businessId, Object beforeObject, Object afterObject, List<DiffModel> changeData, IHumanReadableStrategy readableStrategy) {
        if(changeData.isEmpty()) {
            log.info("No data change, no need to save logDataChangeDO.");
        } else {
            LogDataChangeDO logDataChangeDO = this.buildLogDataChangeDO(tableName, businessId, beforeObject, afterObject, changeData, readableStrategy);
            // 保存日志
            this.save(logDataChangeDO);
        }
    }

}
