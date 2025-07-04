package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.service.CategoryService;
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
 * Category Controller
 *
 * @author mengweijin
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/category")
public class CategoryController {

    private static final String LOG_TITLE = "分类管理";

    private CategoryService categoryService;

    /**
     * Get Category page by Category
     *
     * @param page     page
     * @param categoryDO {@link CategoryDO}
     * @return Page<Category>
     */
    @SaCheckPermission("system:category:select")
    @GetMapping("/page")
    public IPage<CategoryDO> page(Page<CategoryDO> page, CategoryDO categoryDO) {
        LambdaQueryWrapper<CategoryDO> wrapper = categoryService.getQueryWrapper(categoryDO);
        return categoryService.page(page, wrapper);
    }

    /**
     * Get Category list by Category
     *
     * @param categoryDO {@link CategoryDO}
     * @return List<Category>
     */
    @SaCheckPermission("system:category:select")
    @GetMapping("/list")
    public List<CategoryDO> list(CategoryDO categoryDO) {
        LambdaQueryWrapper<CategoryDO> wrapper = categoryService.getQueryWrapper(categoryDO);
        return categoryService.list(wrapper.orderByAsc(CategoryDO::getSeq));
    }

    /**
     * Get Category by id
     *
     * @param id id
     * @return Category
     */
    @SaCheckPermission("system:category:select")
    @GetMapping("/{id}")
    public CategoryDO getById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    /**
     * Add Category
     *
     * @param categoryDO {@link CategoryDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:category:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody CategoryDO categoryDO) {
        boolean bool = categoryService.save(categoryDO);
        return R.result(bool);
    }

    /**
     * Update Category
     *
     * @param categoryDO {@link CategoryDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:category:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody CategoryDO categoryDO) {
        boolean bool = categoryService.updateById(categoryDO);
        return R.result(bool);
    }

    /**
     * Delete Category by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:category:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = categoryService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}


