package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.CategoryBO;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.domain.vo.CategoryVO;
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

    @SaCheckPermission("system:category:select")
    @GetMapping("/page/root")
    public PageQuery<CategoryVO> pageRootTree(PageQuery<CategoryDO> page, CategoryDO category) {
        LambdaQueryWrapper<CategoryDO> wrapper = categoryService.buildRootQueryWrapper(category);
        return categoryService.pageRootTree(page, wrapper);
    }

    @GetMapping("/list/children/by/code/{code}")
    public List<CategoryVO> listChildrenByCode(@PathVariable("code") String code, boolean withSelf) {
        List<CategoryDO> list = categoryService.getChildrenListByCode(code, withSelf);
        return MapstructUtils.getConverter().convert(list, CategoryVO.class);
    }

    /**
     * Get Category list by Category
     *
     * @param categoryDO {@link CategoryDO}
     * @return List<Category>
     */
    @SaCheckPermission("system:category:select")
    @GetMapping("/list")
    public List<CategoryVO> list(CategoryDO categoryDO) {
        LambdaQueryWrapper<CategoryDO> wrapper = categoryService.buildQueryWrapper(categoryDO);
        wrapper.orderByAsc(CategoryDO::getSeq);
        return categoryService.listVo(wrapper);
    }

    /**
     * Get Category by id
     *
     * @param id id
     * @return Category
     */
    @GetMapping("/{id}")
    public CategoryVO getById(@PathVariable("id") Long id) {
        return categoryService.getVoById(id);
    }

    /**
     * Add Category
     *
     * @param categoryBO {@link CategoryBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:category:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody CategoryBO categoryBO) {
        boolean bool = categoryService.save(categoryBO);
        return R.result(bool);
    }

    /**
     * Update Category
     *
     * @param categoryBO {@link CategoryBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:category:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody CategoryBO categoryBO) {
        boolean bool = categoryService.updateById(categoryBO);
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

    @Log(title = LOG_TITLE, operationType = EOperationType.ENABLE)
    @SaCheckPermission("system:category:update")
    @PostMapping("/enable/{id}")
    public R<Void> enable(@PathVariable("id") Long id) {
        boolean bool = categoryService.enable(id);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.DISABLE)
    @SaCheckPermission("system:category:update")
    @PostMapping("/disable/{id}")
    public R<Void> disable(@PathVariable("id") Long id) {
        boolean bool = categoryService.disable(id);
        return R.result(bool);
    }
}


