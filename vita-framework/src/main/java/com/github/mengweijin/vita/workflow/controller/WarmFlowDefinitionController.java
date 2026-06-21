package com.github.mengweijin.vita.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.util.UploadUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.FormDO;
import com.github.mengweijin.vita.system.service.FormService;
import com.github.mengweijin.vita.workflow.domain.vo.FlowDefinitionVO;
import com.github.mengweijin.vita.workflow.service.WarmFlowDefinitionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.ApiResult;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.dromara.warm.flow.ui.controller.WarmFlowController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 流程定义表 FlowDefinition Controller
 * <p>
 * {@link org.dromara.warm.flow.ui.controller.WarmFlowController}
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/workflow/definition")
public class WarmFlowDefinitionController extends WarmFlowController {

    private static final String LOG_TITLE = "流程定义";

    private final WarmFlowDefinitionService warmFlowDefinitionService;

    private final FormService formService;

    /**
     * Get FlowDefinitionVO page by FlowDefinitionDO
     *
     * @param pageQuery      pageQuery
     * @param flowDefinition {@link FlowDefinition}
     * @return PageQuery<FlowDefinitionVO>
     */
    @SaCheckPermission("workflow:definition:select")
    @GetMapping("/page")
    public PageQuery<FlowDefinitionVO> page(PageQuery<FlowDefinition> pageQuery, FlowDefinition flowDefinition) {
        LambdaQueryWrapper<FlowDefinition> wrapper = warmFlowDefinitionService.buildQueryWrapper(flowDefinition);
        IPage<FlowDefinition> page = warmFlowDefinitionService.page(pageQuery.toPage(), wrapper);
        return MapstructUtils.convertToPageQuery(page, FlowDefinitionVO.class);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @Override
    @SaCheckPermission(mode = SaMode.OR, value = {"workflow:definition:create", "workflow:definition:update"})
    @PostMapping("/save-json")
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Void> saveJson(@RequestBody DefJson defJson, @RequestHeader("onlyNodeSkip") boolean onlyNodeSkip) throws Exception {
        return super.saveJson(defJson, onlyNodeSkip);
    }

    /**
     * Add FlowDefinition
     *
     * @param flowDefinition {@link FlowDefinition}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("workflow:definition:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody FlowDefinition flowDefinition) {
        boolean bool = FlowEngine.defService().checkAndSave(flowDefinition);
        return R.result(bool);
    }

    /**
     * Update FlowDefinition
     *
     * @param flowDefinition {@link FlowDefinition}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("workflow:definition:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FlowDefinition flowDefinition) {
        boolean bool = warmFlowDefinitionService.updateById(flowDefinition);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.IMPORT)
    @SaCheckPermission("workflow:definition:create")
    @PostMapping("/import")
    public void importDefinition(HttpServletRequest request) {
        List<MultipartFile> list = UploadUtils.upload(request, file -> file);
        warmFlowDefinitionService.importDefinition(list);
    }

    /**
     * Remove FlowDefinition by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("workflow:definition:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = FlowEngine.defService().removeDef(Arrays.asList(ids));
        return R.result(bool);
    }

    /**
     * Copy FlowDefinition by id
     *
     * @param id id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.COPY)
    @SaCheckPermission("workflow:definition:copy")
    @PostMapping("/copy/{id}")
    public R<Void> copy(@PathVariable("id") Long id) {
        boolean bool = FlowEngine.defService().copyDef(id);
        return R.result(bool);
    }


    /**
     * Publish FlowDefinition by id
     *
     * @param id id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.PUBLISH)
    @SaCheckPermission("workflow:definition:publish")
    @PostMapping("/publish/{id}")
    public R<Void> publish(@PathVariable("id") Long id) {
        boolean bool = FlowEngine.defService().publish(id);
        return R.result(bool);
    }

    /**
     * Publish FlowDefinition by id
     *
     * @param id id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UNPUBLISH)
    @SaCheckPermission("workflow:definition:unpublish")
    @PostMapping("/unpublish/{id}")
    public R<Void> unpublish(@PathVariable("id") Long id) {
        boolean bool = FlowEngine.defService().unPublish(id);
        return R.result(bool);
    }

    @GetMapping("/query/publishedDefinitionStartFormRoutePath/by/flowCode/{flowCode}")
    public String queryPublishedDefinitionStartFormRoutePathByFlowCode(@PathVariable("flowCode") String flowCode) {
        Definition definition = FlowEngine.defService().getPublishByFlowCode(flowCode);
        if (definition == null) {
            throw new ServerException("No published definition found for flowCode: " + flowCode);
        }
        String formCustom = definition.getFormCustom();
        String formPath = definition.getFormPath();
        if (EYesNo.N.getValue().equalsIgnoreCase(formCustom)) {
            return formPath;
        }
        // 动态表单
        FormDO form = formService.getById(formPath);
        return form.getFormPath();
    }
}

