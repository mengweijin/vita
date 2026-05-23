package com.github.mengweijin.vita.workflow.warmflow;

import com.github.mengweijin.vita.system.domain.vo.FormVO;
import com.github.mengweijin.vita.system.service.FormService;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.dto.Tree;
import org.dromara.warm.flow.ui.service.FormPathService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/4/12
 */
@Component
@AllArgsConstructor
public class WarmFlowFormPathService implements FormPathService {

    private final FormService formService;

    @Override
    public List<Tree> queryFormPath() {
        List<FormVO> list = formService.listVo();
        return list.stream().map(f -> {
            Tree tree = new Tree();
            tree.setId(f.getId().toString());
            tree.setName(f.getName());
            tree.setParentId(f.getParentId() == null ? null : f.getParentId().toString());
            tree.setChildren(null);
            return tree;
        }).toList();
    }
}
