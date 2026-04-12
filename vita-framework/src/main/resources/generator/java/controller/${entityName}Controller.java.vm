package ${package}.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import ${package}.domain.bo.${entityName}BO;
import ${package}.domain.entity.${entityName}DO;
import ${package}.domain.vo.${entityName}VO;
import ${package}.service.${entityName}Service;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private static final String LOG_TITLE = "${entityName} 管理";

    private ${entityName}Service ${entityPropertyName}Service;

    /**
     * Get ${entityName}VO page by ${entityName}DO
     * @param page page
     * @param ${entityPropertyName} {@link ${entityName}DO}
     * @return PageQuery<${entityName}VO>
     */
    @SaCheckPermission("${module}:${entityPropertyName}:select")
    @GetMapping("/page")
    public PageQuery<${entityName}VO> page(PageQuery<${entityName}DO> page, ${entityName}DO ${entityPropertyName}) {
        LambdaQueryWrapper<${entityName}DO> wrapper = ${entityPropertyName}Service.buildQueryWrapper(${entityPropertyName});
        return ${entityPropertyName}Service.pageVo(page, wrapper);
    }

    /**
     * Get ${entityName}VO list by ${entityName}DO
     * @param ${entityPropertyName} {@link ${entityName}DO}
     * @return List<${entityName}VO>
     */
    @SaCheckPermission("${module}:${entityPropertyName}:select")
    @GetMapping("/list")
    public List<${entityName}VO> list(${entityName}DO ${entityPropertyName}) {
        return ${entityPropertyName}Service.listVo(Wrappers.lambdaQuery(${entityPropertyName}));
    }

    /**
     * Get ${entityName}VO by id
     * @param id id
     * @return ${entityName}VO
     */
    @GetMapping("/{id}")
    public ${entityName}VO getById(@PathVariable("id") ${idField.columnType.type} id) {
        return ${entityPropertyName}Service.getVoById(id);
    }

    /**
     * Add ${entityName}
     * @param ${entityPropertyName} {@link ${entityName}DO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("${module}:${entityPropertyName}:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody ${entityName}Bo ${entityPropertyName}) {
        boolean bool = ${entityPropertyName}Service.saveByBo(${entityPropertyName});
        return R.result(bool);
    }

    /**
     * Update ${entityName}
     * @param ${entityPropertyName} {@link ${entityName}BO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("${module}:${entityPropertyName}:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody ${entityName}Bo ${entityPropertyName}) {
        boolean bool = ${entityPropertyName}Service.updateByBoById(${entityPropertyName});
        return R.result(bool);
    }

    /**
     * Remove ${entityName} by id(s), Multiple ids can be separated by commas ",".
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("${module}:${entityPropertyName}:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") ${idField.columnType.type}[] ids) {
        boolean bool = ${entityPropertyName}Service.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

