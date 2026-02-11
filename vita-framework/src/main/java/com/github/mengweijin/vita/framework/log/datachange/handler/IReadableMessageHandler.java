package com.github.mengweijin.vita.framework.log.datachange.handler;

import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;

import java.util.Comparator;
import java.util.List;

/**
 *  LogDataChangeDO human readable 转换接口
 *  {@link LogDataChangeDO}
 *
 * @author mengweijin
 * @since 2023/5/20
 */
public interface IReadableMessageHandler {

    String I18N_KEY_ADDED = "monitor.log.datachange.human.readable.added";

    String I18N_KEY_MODIFIED = "monitor.log.datachange.human.readable.modified";

    String I18N_KEY_REMOVED = "monitor.log.datachange.human.readable.removed";

    /**
     * 是否支持
     * @param tableName {@link LogDataChangeDO} 的 table name
     * @return true/false
     */
    boolean supported(String tableName);

    /**
     * 数据翻译
     *
     * @param businessId 业务ID
     * @param changeData 需要被翻译的值
     * @return 返回转换后的值
     */
    default List<String> toHumanReadable(Long businessId, List<DiffModel> changeData) {
        ordered(changeData);
        return buildMessages(businessId, changeData);
    }

    /**
     * 构建消息
     * @param businessId 业务ID
     * @param changeData List<DiffModel>
     * @return List<String>
     */
    default List<String> buildMessages(Long businessId, List<DiffModel> changeData) {
        return changeData.stream().map(i ->
            switch (i.getDiffType()) {
                case ADDED -> I18nUtils.msg(I18N_KEY_ADDED, i.getFieldName(), i.getNewValue());
                case REMOVED -> I18nUtils.msg(I18N_KEY_REMOVED, i.getFieldName(), i.getOldValue());
                default -> I18nUtils.msg(I18N_KEY_MODIFIED, i.getFieldName(), i.getOldValue(), i.getNewValue());
            }
        ).toList();
    }

    /**
     * 排序
     * @param changeData List<DiffModel>
     */
    default void ordered(List<DiffModel> changeData) {
        changeData.sort(Comparator.comparing(DiffModel::getDiffType));
    }

}
