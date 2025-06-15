package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.service.DictDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RestController
@RequestMapping("/system/dict-data")
public class DictDataController {

    private static final String LOG_TITLE = "字典数据";

    private DictDataService dictDataService;

    /**
     * <p>
     * Get DictData page by DictData
     * </p>
     * @param page page
     * @param dictData {@link DictDataDO}
     * @return Page<DictData>
     */
    @SaCheckPermission("system:dictData:select")
    @GetMapping("/page")
    public IPage<DictDataDO> page(Page<DictDataDO> page, DictDataDO dictData) {
        LambdaQueryWrapper<DictDataDO> wrapper = dictDataService.getQueryWrapper(dictData);
        return dictDataService.page(page, wrapper.orderByAsc(DictDataDO::getSeq));
    }

    /**
     * <p>
     * Get DictData list by DictData
     * </p>
     * @param dictData {@link DictDataDO}
     * @return List<DictData>
     */
    @SaCheckPermission("system:dictData:select")
    @GetMapping("/list")
    public List<DictDataDO> list(DictDataDO dictData) {
        return dictDataService.list(new LambdaQueryWrapper<>(dictData).orderByAsc(DictDataDO::getCode).orderByAsc(DictDataDO::getSeq));
    }

    /**
     * <p>
     * Get DictData by id
     * </p>
     * @param id id
     * @return DictData
     */
    @SaCheckPermission("system:dictData:select")
    @GetMapping("/{id}")
    public DictDataDO getById(@PathVariable("id") Long id) {
        return dictDataService.getById(id);
    }

    @SaCheckPermission("system:dictData:select")
    @GetMapping("/get-by-code/{code}")
    public List<DictDataDO> getByCode(@PathVariable("code") String code) {
        return dictDataService.getByCode(code);
    }

    /**
     * <p>
     * Add DictData
     * </p>
     * @param dictData {@link DictDataDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:dictData:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody DictDataDO dictData) {
        dictDataService.checkValDuplicate(dictData.getCode(), dictData.getVal());
        boolean bool = dictDataService.save(dictData);
        return R.result(bool);
    }

    /**
     * <p>
     * Update DictData
     * </p>
     * @param dictData {@link DictDataDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dictData:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody DictDataDO dictData) {
        dictDataService.checkValDuplicate(dictData.getCode(), dictData.getVal());
        boolean bool = dictDataService.updateById(dictData);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete DictData by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:dictData:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(dictDataService.removeByIds(Arrays.asList(ids)));
    }

}

