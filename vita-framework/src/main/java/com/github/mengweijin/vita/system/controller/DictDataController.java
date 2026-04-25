package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.DictDataBO;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.domain.vo.DictDataVO;
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
 * DictData Controller
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
     *
     * @param page     page
     * @param dictData {@link DictDataDO}
     * @return Page<DictData>
     */
    @SaCheckPermission("system:dictData:select")
    @GetMapping("/page")
    public PageQuery<DictDataVO> page(PageQuery<DictDataDO> page, DictDataDO dictData) {
        LambdaQueryWrapper<DictDataDO> wrapper = dictDataService.buildQueryWrapper(dictData);
        return dictDataService.pageVo(page, wrapper.orderByAsc(DictDataDO::getSeq));
    }

    /**
     * <p>
     * Get DictData list by DictData
     * </p>
     *
     * @param dictData {@link DictDataDO}
     * @return List<DictData>
     */
    @GetMapping("/list")
    public List<DictDataVO> list(DictDataDO dictData) {
        LambdaQueryWrapper<DictDataDO> wrapper = dictDataService.defaultQueryWrapper(dictData).orderByAsc(DictDataDO::getSeq);
        return dictDataService.listVo(wrapper);
    }

    /**
     * <p>
     * Get DictData by id
     * </p>
     *
     * @param id id
     * @return DictData
     */
    @GetMapping("/{id}")
    public DictDataVO getById(@PathVariable("id") Long id) {
        return dictDataService.getVoById(id);
    }

    @GetMapping("/query/by/code/{code}")
    public List<DictDataVO> queryByCode(@PathVariable("code") String code) {
        List<DictDataDO> list = dictDataService.queryByCode(code);
        return MapstructUtils.getConverter().convert(list, DictDataVO.class);
    }

    /**
     * <p>
     * Add DictData
     * </p>
     *
     * @param bo {@link DictDataDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:dictData:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody DictDataBO bo) {
        dictDataService.checkValDuplicate(null, bo.getCode(), bo.getVal());
        boolean bool = dictDataService.saveByBo(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Update DictData
     * </p>
     *
     * @param bo {@link DictDataDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dictData:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody DictDataBO bo) {
        dictDataService.checkValDuplicate(bo.getId(), bo.getCode(), bo.getVal());
        boolean bool = dictDataService.updateByBoById(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete DictData by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:dictData:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(dictDataService.removeByIds(Arrays.asList(ids)));
    }

}

