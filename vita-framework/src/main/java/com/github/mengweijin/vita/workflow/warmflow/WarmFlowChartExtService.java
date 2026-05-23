package com.github.mengweijin.vita.workflow.warmflow;

import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.PromptContent;
import org.dromara.warm.flow.core.utils.MapUtil;
import org.dromara.warm.flow.ui.service.ChartExtService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author mengweijin
 * @since 2026/5/23
 */
@Component
@AllArgsConstructor
public class WarmFlowChartExtService implements ChartExtService {

    @Override
    public void execute(DefJson defJson) {
        defJson.setTopText("流程名称: " + defJson.getFlowName());
        defJson.getNodeList().forEach(nodeJson -> {
            // extMap是在分派监听器中设置的, 用户使用的时候不用局限于这种方式, 可以临时查询出来, 或者通过其他方式获取提示信息
            Map<String, Object> extMap = nodeJson.getExtMap();
            if (MapUtil.isNotEmpty(extMap)) {
                for (Map.Entry<String, Object> entry : extMap.entrySet()) {
                    // 添加第二个条目
                    PromptContent.InfoItem item = new PromptContent.InfoItem();
                    item.setPrefix(entry.getKey() + ": ");
                    item.setContent((String) entry.getValue());
                    nodeJson.getPromptContent().getInfo().add(item);
                }
            }
        });
    }
}
