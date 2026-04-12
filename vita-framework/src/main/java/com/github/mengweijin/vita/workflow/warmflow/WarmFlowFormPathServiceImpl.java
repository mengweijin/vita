package com.github.mengweijin.vita.workflow.warmflow;

import com.github.mengweijin.vita.workflow.domain.vo.WorkflowFormVO;
import com.github.mengweijin.vita.workflow.service.WorkflowFormService;
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
public class WarmFlowFormPathServiceImpl implements FormPathService {

    private WorkflowFormService workflowFormService;

    @Override
    public List<Tree> queryFormPath() {
        List<WorkflowFormVO> list = workflowFormService.listVo();
        return list.stream().map(f -> {
            Tree tree = new Tree();
            tree.setId(f.getId().toString());
            tree.setName(f.getName());
            tree.setParentId(null);
            tree.setChildren(null);
            return tree;
        }).toList();
    }
}
