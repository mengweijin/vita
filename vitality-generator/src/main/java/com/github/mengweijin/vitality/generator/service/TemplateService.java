package com.github.mengweijin.vitality.generator.service;

import com.github.mengweijin.vitality.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vitality.generator.enums.EFileType;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.io.resource.ResourceUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/14
 */
@Service
public class TemplateService {

    public static final String TEMPLATE_DIR = "velocity";

    public static final String TEMPLATE_SUFFIX = ".vm";

    public static final File TEMPLATE_FILES = FileUtil.file(ResourceUtil.getResource(TEMPLATE_DIR).getUrl());

    private List<TemplateVO> templateCacheList = new CopyOnWriteArrayList<>();

    public TemplateVO findById(String templateId) {
        if (templateCacheList.isEmpty()) {
            this.loadTemplateCacheList();
        }
        return templateCacheList.stream().filter(tpl -> tpl.getId().equals(templateId)).findFirst().orElse(null);
    }

    public List<TemplateVO> getTemplateList() {
        if (templateCacheList.isEmpty()) {
            this.loadTemplateCacheList();
        }
        return templateCacheList;
    }

    public List<TemplateVO> buildTemplateTree() {
        if (templateCacheList.isEmpty()) {
            this.loadTemplateCacheList();
        }
        return this.treeTemplateVO(templateCacheList, TEMPLATE_FILES.getPath());
    }

    public void loadTemplateCacheList() {
        List<File> templateList = FileUtil.loopFiles(TEMPLATE_FILES, item -> item.isDirectory() || (item.isFile() && item.getName().toLowerCase().endsWith(TEMPLATE_SUFFIX)));
        if (CollUtil.isEmpty(templateCacheList)) {
            templateCacheList = this.buildTemplateList(templateList);
        }
    }

    private List<TemplateVO> buildTemplateList(List<File> templateList) {
        List<TemplateVO> list = new ArrayList<>();
        templateList.forEach(file -> {
            TemplateVO vo = new TemplateVO();
            vo.setId(file.getPath());
            vo.setParentId(file.getParentFile().getPath());
            if (file.isFile()) {
                vo.setName(StrUtil.subBefore(file.getName(), ".", true));
                vo.setType(EFileType.FILE.name());
                vo.setContent(FileUtil.readUtf8String(file));
            } else {
                vo.setName(file.getName());
                vo.setType(EFileType.DIR.name());
            }
            list.add(vo);

            collectParentTemplateNode(file.getParentFile(), list);
        });
        return list;
    }

    private void collectParentTemplateNode(File file, List<TemplateVO> list) {
        if (TEMPLATE_FILES.getPath().equals(file.getPath()) || list.stream().anyMatch(i -> i.getId().equals(file.getPath()))) {
            return;
        }
        TemplateVO vo = new TemplateVO();
        vo.setId(file.getPath());
        vo.setParentId(file.getParentFile().getPath());
        vo.setName(file.getName());
        list.add(vo);

        collectParentTemplateNode(file, list);
    }

    private List<TemplateVO> treeTemplateVO(List<TemplateVO> list, String parentId) {
        Map<String, List<TemplateVO>> collect = list.stream().collect(Collectors.groupingBy(TemplateVO::getParentId));
        for (TemplateVO node : list) {
            List<TemplateVO> children = collect.get(node.getId());
            if (CollUtil.isNotEmpty(children)) {
                children.sort(Comparator.comparingInt(TemplateVO::getSeq));
                node.setChildren(children);
            }
        }
        return collect.get(parentId);
    }

}
