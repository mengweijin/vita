package com.github.mengweijin.vita.framework.log.datachange.strategy;

import com.github.mengweijin.vita.framework.log.datachange.DiffModel;

import java.util.List;

/**
 *  LogDataChangeDO human readable 转换接口
 *  {@link com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO}
 *
 * @author mengweijin
 * @since 2023/5/20
 */
public interface IHumanReadableStrategy {

    /**
     * 获取 beanName
     * @return beanName
     */
    String getBeanName();

    /**
     * 数据翻译
     *
     * @param changeData 需要被翻译的值
     * @return 返回转换后的值
     */
    List<String> toHumanReadable(List<DiffModel> changeData);

}
