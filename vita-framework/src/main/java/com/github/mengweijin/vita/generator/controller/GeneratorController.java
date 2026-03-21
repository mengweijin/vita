package com.github.mengweijin.vita.generator.controller;

import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.generator.domain.bo.GeneratorBO;
import com.github.mengweijin.vita.generator.domain.vo.ContentVO;
import com.github.mengweijin.vita.generator.domain.vo.TableInfoVO;
import com.github.mengweijin.vita.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vita.generator.service.GeneratorService;
import com.github.mengweijin.vita.generator.service.TemplateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private TemplateService templateService;

    private GeneratorService generatorService;

    @GetMapping("/list/tableInfo")
    public List<TableInfoVO> listTableInfo(String name) {
        return generatorService.selectTableList(name);
    }

    @GetMapping("/list/template")
    public List<TemplateVO> getTemplateList() {
        return templateService.getTemplateList();
    }

    @GetMapping("/query/defaultArgs")
    public GeneratorBO getDefaultArgs() {
        return new GeneratorBO();
    }

    @PostMapping("/run")
    public R<ContentVO> run(@RequestBody GeneratorBO bo) {
        ContentVO contentVO = generatorService.generate(bo);
        return R.ok(contentVO);
    }

    @PostMapping("/download")
    public void download(@RequestBody GeneratorBO bo, HttpServletResponse response) {
        File file = generatorService.download(bo);
        ServletUtil.write(response, file);
        FileUtil.del(file);
    }
}
