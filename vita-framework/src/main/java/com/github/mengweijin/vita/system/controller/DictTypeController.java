package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.DictTypeBO;
import com.github.mengweijin.vita.system.domain.entity.DictTypeDO;
import com.github.mengweijin.vita.system.domain.vo.DictTypeVO;
import com.github.mengweijin.vita.system.service.DictTypeService;
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
 * DictType Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/dict-type")
public class DictTypeController {

    private static final String LOG_TITLE = "字典类型";

    private DictTypeService dictTypeService;

    /**
     * <p>
     * Get DictType page by DictType
     * </p>
     *
     * @param page     page
     * @param dictType {@link DictTypeDO}
     * @return Page<DictType>
     */
    @SaCheckPermission("system:dictType:select")
    @GetMapping("/page")
    public PageQuery<DictTypeVO> page(PageQuery<DictTypeDO> page, DictTypeDO dictType) {
        LambdaQueryWrapper<DictTypeDO> wrapper = dictTypeService.buildQueryWrapper(dictType);
        wrapper.orderByAsc(DictTypeDO::getCode);
        return dictTypeService.pageVo(page, wrapper);
    }

    /**
     * <p>
     * Get DictType list by DictType
     * </p>
     *
     * @param dictType {@link DictTypeDO}
     * @return List<DictType>
     */
    @SaCheckPermission("system:dictType:select")
    @GetMapping("/list")
    public List<DictTypeVO> list(DictTypeDO dictType) {
        return dictTypeService.listVo(Wrappers.lambdaQuery(dictType));
    }

    /**
     * <p>
     * Get DictType by id
     * </p>
     *
     * @param id id
     * @return DictType
     */
    @GetMapping("/{id}")
    public DictTypeVO getById(@PathVariable("id") Long id) {
        return dictTypeService.getVoById(id);
    }

    /**
     * <p>
     * Add DictType
     * </p>
     *
     * @param dictType {@link DictTypeDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:dictType:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody DictTypeBO dictType) {
        boolean bool = dictTypeService.saveByBo(dictType);
        return R.result(bool);
    }

    /**
     * <p>
     * Update DictType
     * </p>
     *
     * @param dictType {@link DictTypeDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dictType:update")
    @PostMapping("update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody DictTypeBO dictType) {
        boolean bool = dictTypeService.updateByBoById(dictType);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete DictType by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:dictType:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(dictTypeService.removeByIds(Arrays.asList(ids)));
    }

}

