package com.github.mengweijin.generator.system.controller;

import com.github.mengweijin.generator.system.dto.GenerateArgsDTO;
import com.github.mengweijin.generator.system.dto.GenerateConfigDTO;
import com.github.mengweijin.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.generator.system.service.GeneratorService;
import com.github.mengweijin.generator.system.service.TemplateService;
import com.github.mengweijin.vitality.dtree.DTreeDTO;
import com.github.mengweijin.vitality.layui.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@RestController
@RequestMapping(GeneratorController.PREFIX)
public class GeneratorController {
    public static final String PREFIX = "/gen";

    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private TemplateService templateService;

    @GetMapping("/table/list")
    public LayuiTable<TableInfoDTO> list(String name) {
        return new LayuiTable<>(generatorService.selectTable(name));
    }

    @GetMapping("/config/default/{tableName}")
    public GenerateConfigDTO getDefaultConfig(@PathVariable String tableName) {
        return generatorService.getDefaultConfig(tableName);
    }

    @GetMapping("/template/tree")
    public DTreeDTO tree() {
        return templateService.tree();
    }

    @PostMapping("/execute/{tableName}")
    public String execute(
                          @PathVariable("tableName") String tableName,
                          @RequestBody GenerateArgsDTO dto) {
        String codeContent = generatorService.generate(tableName, dto.getTemplateId(), dto.getConfig());
        return codeContent;
    }
}
