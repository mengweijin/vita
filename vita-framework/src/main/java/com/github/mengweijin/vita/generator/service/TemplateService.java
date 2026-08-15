package com.github.mengweijin.vita.generator.service;

import com.github.mengweijin.vita.framework.util.ResourceUtils;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Slf4j
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "generator";

    public static final String LOCATION_PATTERN = "generator/**/*.*";

    public List<TemplateVO> getTemplateList() {
        List<ResourceUtils.ResourceInfo> list = ResourceUtils.load(LOCATION_PATTERN);
        return list.stream()
                .map(TemplateVO::new)
                .toList();
    }

    public TemplateVO getTemplateById(String id) {
        List<TemplateVO> templateList = this.getTemplateList();
        return templateList.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
