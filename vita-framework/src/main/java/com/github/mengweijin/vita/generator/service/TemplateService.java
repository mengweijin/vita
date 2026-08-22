package com.github.mengweijin.vita.generator.service;

import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.util.ResourceUtils;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Slf4j
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "generator";

    public static final String LOCATION_PATTERN = "generator/**/*.*";

    public List<TemplateVO> loadTemplateList() {
        List<ResourceUtils.ResourceInfo> list = ResourceUtils.load(LOCATION_PATTERN);
        return list.stream()
                .map(TemplateVO::new)
                .toList();
    }

    public List<TemplateVO> filledParentTemplateList(List<TemplateVO> templateList) {
        Map<String, TemplateVO> map = new HashMap<>();
        for (TemplateVO template : templateList) {
            this.buildParentTemplateList(map, template.getParentId());
        }
        List<TemplateVO> list = new ArrayList<>(templateList);
        list.addAll(map.values());
        return list;
    }


    public void buildParentTemplateList(Map<String, TemplateVO> map, String parentPath) {
        if (StrUtil.isBlank(parentPath) || map.containsKey(parentPath)) {
            return;
        }

        TemplateVO vo = new TemplateVO();
        vo.setId(parentPath);
        vo.setParentId(StrUtil.subBefore(vo.getId(), Const.SLASH, true));
        vo.setName(StrUtil.subAfter(vo.getId(), Const.SLASH, true));
        vo.setContent(null);

        map.put(vo.getId(), vo);

        buildParentTemplateList(map, vo.getParentId());
    }

    public TemplateVO getTemplateById(String id) {
        List<TemplateVO> templateList = this.loadTemplateList();
        return templateList.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}