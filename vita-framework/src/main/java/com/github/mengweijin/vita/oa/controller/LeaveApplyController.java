package com.github.mengweijin.vita.oa.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.oa.domain.bo.LeaveApplyBO;
import com.github.mengweijin.vita.oa.domain.entity.LeaveApplyDO;
import com.github.mengweijin.vita.oa.domain.vo.LeaveApplyVO;
import com.github.mengweijin.vita.oa.service.LeaveApplyService;
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
 * 员工请假申请表 LeaveApply Controller
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/hr/leave-apply")
public class LeaveApplyController {

    private static final String LOG_TITLE = "LeaveApply 管理";

    private LeaveApplyService leaveApplyService;

    /**
     * Get LeaveApplyVO page by LeaveApplyDO
     *
     * @param page       page
     * @param leaveApply {@link LeaveApplyDO}
     * @return PageQuery<LeaveApplyVO>
     */
    @SaCheckPermission("system:leaveApply:select")
    @GetMapping("/page")
    public PageQuery<LeaveApplyVO> page(PageQuery<LeaveApplyDO> page, LeaveApplyDO leaveApply) {
        LambdaQueryWrapper<LeaveApplyDO> wrapper = leaveApplyService.buildQueryWrapper(leaveApply);
        return leaveApplyService.pageVo(page, wrapper);
    }

    /**
     * Get LeaveApplyVO list by LeaveApplyDO
     *
     * @param leaveApply {@link LeaveApplyDO}
     * @return List<LeaveApplyVO>
     */
    @SaCheckPermission("system:leaveApply:select")
    @GetMapping("/list")
    public List<LeaveApplyVO> list(LeaveApplyDO leaveApply) {
        return leaveApplyService.listVo(Wrappers.lambdaQuery(leaveApply));
    }

    /**
     * Get LeaveApplyVO by id
     *
     * @param id id
     * @return LeaveApplyVO
     */
    @GetMapping("/{id}")
    public LeaveApplyVO getById(@PathVariable("id") Long id) {
        return leaveApplyService.getVoById(id);
    }

    /**
     * Add LeaveApply
     *
     * @param leaveApply {@link LeaveApplyDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:leaveApply:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody LeaveApplyBO leaveApply) {
        boolean bool = leaveApplyService.save(leaveApply);
        return R.result(bool);
    }

    /**
     * Update LeaveApply
     *
     * @param leaveApply {@link LeaveApplyBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:leaveApply:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody LeaveApplyBO leaveApply) {
        boolean bool = leaveApplyService.updateById(leaveApply);
        return R.result(bool);
    }

    /**
     * Remove LeaveApply by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:leaveApply:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = leaveApplyService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

