package com.github.mengweijin.vita.workflow.warmflow;

import com.github.mengweijin.vita.system.domain.vo.FormWorkflowVO;
import com.github.mengweijin.vita.system.service.FormWorkflowService;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.dto.Tree;
import org.dromara.warm.flow.ui.service.FormPathService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/4/12
 */
@Service
@AllArgsConstructor
public class WarmFlowFormPathService implements FormPathService {

    private final FormWorkflowService formWorkflowService;

    @Override
    public List<Tree> queryFormPath() {
        List<FormWorkflowVO> list = formWorkflowService.listVo();
        return list.stream().map(f -> {
            Tree tree = new Tree();
            tree.setId(f.getRoutePath());
            tree.setName(f.getName());
            tree.setParentId(null);
            tree.setChildren(null);
            return tree;
        }).toList();
    }
}
