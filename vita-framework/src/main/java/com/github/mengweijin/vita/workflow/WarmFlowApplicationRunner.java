package com.github.mengweijin.vita.workflow;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.io.resource.MultiResource;
import cn.hutool.v7.core.io.resource.ResourceFinder;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.entity.Definition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author mengweijin
 * @since 2026/5/30
 */
@Slf4j
@Component
@AllArgsConstructor
public class WarmFlowApplicationRunner implements ApplicationRunner {

    private final TransactionTemplate transactionTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化流程定义......");
        MultiResource resources = ResourceFinder.of().find("workflow/*.json");

        // 执行事务操作。如果抛出未捕获的异常，事务会自动回滚
        transactionTemplate.executeWithoutResult(status ->
                resources.forEach(resource -> {
                    String resourceName = resource.getName();
                    String str = resource.readUtf8Str();
                    DefJson defJson = FlowEngine.jsonConvert.strToBean(str, DefJson.class);
                    defJson.setCreateBy(String.valueOf(VitaConst.USER_ADMIN_ID));
                    defJson.setUpdateBy(String.valueOf(VitaConst.USER_ADMIN_ID));
                    List<Definition> definitions = FlowEngine.defService().getByFlowCode(defJson.getFlowCode());
                    if (CollUtil.isEmpty(definitions)) {
                        // 导入流程定义
                        Definition definition = FlowEngine.defService().importDef(defJson);
                        // 发布流程定义
                        FlowEngine.defService().publish(definition.getId());
                        log.info("成功导入流程定义: {}", resourceName);
                    } else {
                        log.info("流程定义已存在: {}", resourceName);
                    }
                })
        );

        log.info("初始化流程定义已完成。");
    }
}
