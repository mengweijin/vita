package com.github.mengweijin.vita.workflow.warm;

import cn.hutool.v7.core.math.NumberUtil;
import com.github.mengweijin.vita.framework.enums.ECategoryType;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.service.CategoryService;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.dto.Tree;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/4/4
 */
@Service
@AllArgsConstructor
public class WarmFlowCategoryServiceImpl implements org.dromara.warm.flow.ui.service.CategoryService {

    private CategoryService categoryService;

    @Override
    public List<Tree> queryCategory() {
        List<CategoryDO> list = categoryService.getChildrenListByCode(ECategoryType.VT_WORKFLOW.getValue(), false, false);
        return list.stream()
                .map(i -> {
                    String id = NumberUtil.toStr(i.getId());
                    String parentId = NumberUtil.toStr(i.getParentId(), null);
                    String name = i.getName();
                    return new Tree(id, name, parentId, null);
                })
                .toList();
    }
}
