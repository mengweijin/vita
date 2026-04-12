package com.github.mengweijin.vita.generator.service;

import cn.hutool.v7.core.collection.CollUtil;
import com.github.mengweijin.vita.framework.util.JarFileUtils;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Slf4j
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "generator";

    public List<TemplateVO> getTemplateList() {
        List<JarFileUtils.ContentInfo> list = JarFileUtils.loadContentInfo(TEMPLATE_DIR);

        List<TemplateVO> templateVOList = list.stream()
                .map(TemplateVO::new)
                .toList();

        templateVOList.forEach(item -> log.debug(item.toString()));
        return templateVOList;
    }

    public TemplateVO getTemplateById(String templateId) {
        List<TemplateVO> templateList = this.getTemplateList();
        return templateList.stream()
                .filter(i -> i.getId().equals(templateId))
                .findFirst()
                .orElse(null);
    }

    /**
     * @deprecated 此方法仅为示例，演示旧版逻辑，请勿在新代码中使用。
     * 保留原因：供学习和参考使用。
     * @param list List<TemplateVO>
     * @param parentId parentId
     * @return tree
     */
    @SuppressWarnings("java:S1133")
    @Deprecated(since = "2.0")
    private List<TemplateVO> treeTemplateVO(List<TemplateVO> list, String parentId) {
        Map<String, List<TemplateVO>> collect = list.stream().collect(Collectors.groupingBy(TemplateVO::getParentId));
        for (TemplateVO node : list) {
            List<TemplateVO> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

}
