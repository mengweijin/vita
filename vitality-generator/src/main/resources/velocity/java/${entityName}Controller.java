package ${package}.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import ${package}.domain.entity.${entityName};
import ${package}.service.${entityName}Service;
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
 * $!{table.comment} ${entityName} Controller
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("${requestMapping}")
public class ${entityName}Controller {

    private ${entityName}Service ${entityPropertyName}Service;

    /**
     * Get ${entityName} page by ${entityName}
     * @param page page
     * @param ${entityPropertyName} {@link ${entityName}}
     * @return Page<${entityName}>
     */
    @SaCheckPermission("${module}:${entityPropertyName}:query")
    @GetMapping("/page")
    public IPage<${entityName}> page(Page<${entityName}> page, ${entityName} ${entityPropertyName}) {
        return ${entityPropertyName}Service.page(page, ${entityPropertyName});
    }

    /**
     * Get ${entityName} list by ${entityName}
     * @param ${entityPropertyName} {@link ${entityName}}
     * @return List<${entityName}>
     */
    @SaCheckPermission("${module}:${entityPropertyName}:query")
    @GetMapping("/list")
    public List<${entityName}> list(${entityName} ${entityPropertyName}) {
        return ${entityPropertyName}Service.list(new LambdaQueryWrapper<>(${entityPropertyName}));
    }

    /**
     * Get ${entityName} by id
     * @param id id
     * @return ${entityName}
     */
    @SaCheckPermission("${module}:${entityPropertyName}:query")
    @GetMapping("/{id}")
    public ${entityName} getById(@PathVariable("id") ${idField.columnType.type} id) {
        return ${entityPropertyName}Service.getById(id);
    }

    /**
     * Add ${entityName}
     * @param ${entityPropertyName} {@link ${entityName}}
     */
    @SaCheckPermission("${module}:${entityPropertyName}:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody ${entityName} ${entityPropertyName}) {
        boolean bool = ${entityPropertyName}Service.save(${entityPropertyName});
        return R.ajax(bool);
    }

    /**
     * Update ${entityName}
     * @param ${entityPropertyName} {@link ${entityName}}
     */
    @SaCheckPermission("${module}:${entityPropertyName}:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody ${entityName} ${entityPropertyName}) {
        boolean bool = ${entityPropertyName}Service.updateById(${entityPropertyName});
        return R.ajax(bool);
    }

    /**
     * Delete ${entityName} by id(s), Multiple ids can be separated by commas ",".
     * @param ids id
     */
    @SaCheckPermission("${module}:${entityPropertyName}:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") ${idField.columnType.type}[] ids) {
        boolean bool = ${entityPropertyName}Service.removeByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

}

