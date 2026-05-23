package com.github.mengweijin.vita.workflow.warmflow;

import cn.hutool.v7.core.date.DateFormatPool;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.NodeJson;
import org.dromara.warm.flow.core.listener.GlobalListener;
import org.dromara.warm.flow.core.listener.ListenerVariable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author mengweijin
 * @since 2026/5/23
 */
@Slf4j
@Component
@AllArgsConstructor
public class WarmFlowGlobalListener implements GlobalListener {

    private static final String HANDLER_USER_NICKNAME_LABEL = "办理人";

    private static final String HANDLER_TIME_LABEL = "办理时间";

    private final UserService userService;

    /**
     * 分派监听器，动态修改代办任务信息
     *
     * @param listenerVariable 监听器变量
     */
    @Override
    public void assignment(ListenerVariable listenerVariable) {
        log.info("全局分派监听器开始执行......");
        String defJsonStr = listenerVariable.getInstance().getDefJson();
        if (StrUtil.isNotBlank(defJsonStr)) {
            DefJson defJson = FlowEngine.jsonConvert.strToBean(defJsonStr, DefJson.class);
            for (NodeJson nodeJson : defJson.getNodeList()) {
                if (nodeJson.getNodeCode().equals(listenerVariable.getNode().getNodeCode())) {
                    Long userId = Long.valueOf(listenerVariable.getFlowParams().getHandler());
                    UserDO user = userService.getById(userId);
                    if (user != null && StrUtil.isNotEmpty(user.getNickname())) {
                        nodeJson.getExtMap().put(HANDLER_USER_NICKNAME_LABEL, user.getNickname());
                    }
                    // 年月日时分秒
                    nodeJson.getExtMap().put(HANDLER_TIME_LABEL, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormatPool.NORM_DATETIME_PATTERN)));
                }
            }
            listenerVariable.getInstance().setDefJson(FlowEngine.jsonConvert.objToStr(defJson));
        }

        log.info("全局分派监听器执行结束......");
    }
}
