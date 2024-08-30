package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.DictData;
import com.github.mengweijin.vitality.system.service.DictDataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  DictData Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/dict-data")
public class DictDataController {

    private DictDataService dictDataService;

    /**
     * <p>
     * Get DictData page by DictData
     * </p>
     * @param page page
     * @param dictData {@link DictData}
     * @return Page<DictData>
     */
    @SaCheckPermission("system_dictData_query")
    @GetMapping("/page")
    public IPage<DictData> page(Page<DictData> page, DictData dictData) {
        return dictDataService.page(page, dictData);
    }

    /**
     * <p>
     * Get DictData list by DictData
     * </p>
     * @param dictData {@link DictData}
     * @return List<DictData>
     */
    @SaCheckPermission("system_dictData_query")
    @GetMapping("/list")
    public List<DictData> list(DictData dictData) {
        return dictDataService.list(new QueryWrapper<>(dictData));
    }

    /**
     * <p>
     * Get DictData by id
     * </p>
     * @param id id
     * @return DictData
     */
    @SaCheckPermission("system_dictData_query")
    @GetMapping("/{id}")
    public DictData getById(@PathVariable("id") Long id) {
        return dictDataService.getById(id);
    }

    /**
     * <p>
     * Add DictData
     * </p>
     * @param dictData {@link DictData}
     */
    @SaCheckPermission("system_dictData_create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody DictData dictData) {
        boolean bool = dictDataService.save(dictData);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update DictData
     * </p>
     * @param dictData {@link DictData}
     */
    @SaCheckPermission("system_dictData_update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody DictData dictData) {
        boolean bool = dictDataService.updateById(dictData);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete DictData by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system_dictData_delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = dictDataService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

