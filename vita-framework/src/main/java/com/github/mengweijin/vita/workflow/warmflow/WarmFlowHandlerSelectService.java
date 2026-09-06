package com.github.mengweijin.vita.workflow.warmflow;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.date.DateUtil;
import cn.hutool.v7.core.date.TimeUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.DeptVO;
import com.github.mengweijin.vita.system.domain.vo.PostVO;
import com.github.mengweijin.vita.system.domain.vo.RoleVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserVO;
import com.github.mengweijin.vita.system.service.DeptService;
import com.github.mengweijin.vita.system.service.PostService;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserService;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowHandlerType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.utils.StreamUtils;
import org.dromara.warm.flow.ui.dto.HandlerFunDto;
import org.dromara.warm.flow.ui.dto.HandlerQuery;
import org.dromara.warm.flow.ui.dto.TreeFunDto;
import org.dromara.warm.flow.ui.service.HandlerSelectService;
import org.dromara.warm.flow.ui.vo.HandlerFeedBackVo;
import org.dromara.warm.flow.ui.vo.HandlerSelectVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author mengweijin
 * @since 2026/5/23
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowHandlerSelectService implements HandlerSelectService {

    private final UserService userService;

    private final RoleService roleService;

    private final DeptService deptService;

    private final PostService postService;

    @Override
    public List<String> getHandlerType() {
        return EWarmFlowHandlerType.getDescList();
    }

    @Override
    public HandlerSelectVo getHandlerSelect(HandlerQuery query) {
        EWarmFlowHandlerType handlerType = EWarmFlowHandlerType.fromDesc(query.getHandlerType());
        return switch (handlerType) {
            case USER -> getUser(query);
            case ROLE -> getRole(query);
            case DEPT -> getDept(query);
            case POST -> getPost(query);
        };
    }

    @Override
    public List<HandlerFeedBackVo> handlerFeedback(List<String> storageIds) {
        if (CollUtil.isEmpty(storageIds)) {
            return Collections.emptyList();
        }

        Map<String, List<Long>> map = storageIds.stream()
                .collect(Collectors.groupingBy(
                        str -> {
                            if (str.startsWith(EWarmFlowHandlerType.ROLE.getCode())) {
                                return EWarmFlowHandlerType.ROLE.getDesc();
                            }
                            if (str.startsWith(EWarmFlowHandlerType.DEPT.getCode())) {
                                return EWarmFlowHandlerType.DEPT.getDesc();
                            }
                            if (str.startsWith(EWarmFlowHandlerType.POST.getCode())) {
                                return EWarmFlowHandlerType.POST.getDesc();
                            }
                            return EWarmFlowHandlerType.USER.getDesc();
                        },
                        Collectors.mapping(
                                // 只取后面的值，转换为 Long
                                str -> {
                                    String[] split = str.split(Const.COLON, 2);
                                    String id = split.length == 1 ? str : split[1];
                                    return NumberUtil.parseLong(id);
                                },
                                Collectors.toList()
                        )
                ));

        List<Long> userIdList = map.get(EWarmFlowHandlerType.USER.getDesc());
        List<Long> roleIdList = map.get(EWarmFlowHandlerType.ROLE.getDesc());
        List<Long> deptIdList = map.get(EWarmFlowHandlerType.DEPT.getDesc());
        List<Long> postIdList = map.get(EWarmFlowHandlerType.POST.getDesc());

        List<HandlerFeedBackVo> handlerFeedBackVos = new ArrayList<>();

        Map<String, String> authMap = new HashMap<>();

        // 查询用户id对应的名称
        if (CollUtil.isNotEmpty(userIdList)) {
            List<UserDO> userList = userService.listByIds(userIdList);
            authMap.putAll(StreamUtils.toMap(userList, user -> EWarmFlowHandlerType.USER.getCode() + user.getId(), UserDO::getNickname));
        }

        // 查询角色id对应的名称
        if (CollUtil.isNotEmpty(roleIdList)) {
            // 查询角色列表
            List<RoleDO> roleList = roleService.listByIds(roleIdList);
            authMap.putAll(StreamUtils.toMap(roleList, role -> EWarmFlowHandlerType.ROLE.getCode() + role.getId(), RoleDO::getName));
        }

        // 查询部门id对应的名称
        if (CollUtil.isNotEmpty(deptIdList)) {
            List<DeptDO> deptList = deptService.listByIds(deptIdList);
            authMap.putAll(StreamUtils.toMap(deptList, dept -> EWarmFlowHandlerType.DEPT.getCode() + dept.getId(), DeptDO::getName));
        }

        // 查询岗位id对应的名称
        if (CollUtil.isNotEmpty(postIdList)) {
            List<PostDO> postList = postService.listByIds(postIdList);
            authMap.putAll(StreamUtils.toMap(postList, post -> EWarmFlowHandlerType.POST.getCode() + post.getId(), PostDO::getName));
        }

        // 遍历storageIds，按照原本的顺序回显名称
        for (String storageId : storageIds) {
            handlerFeedBackVos.add(new HandlerFeedBackVo(storageId, StrUtil.defaultIfNull(authMap.get(storageId), Const.EMPTY)));
        }

        return handlerFeedBackVos;
    }

    /**
     * 获取用户列表, 同时构建左侧部门树状结构
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getUser(HandlerQuery query) {
        // 查询用户列表
        Long deptId = NumberUtil.parseLong(query.getGroupId(), null);

        UserDO param = new UserDO();
        param.setDeptId(deptId);
        param.setDisabled(EYesNo.N.getValue());
        param.setUsername(query.getHandlerCode());
        param.setNickname(query.getHandlerName());
        this.setParamQueryTimeRange(query, param);

        PageQuery<UserDO> pageQuery = PageQuery.of(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<UserDO> userQueryWrapper = userService.buildQueryWrapper(param);
        PageQuery<UserVO> userPage = userService.pageVo(pageQuery, userQueryWrapper);
        userPage.getPageRecords().forEach(user -> user.setDeptName(deptService.getNameById(user.getDeptId())));

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<UserVO> handlerFunDto = new HandlerFunDto<>(userPage.getPageRecords(), userPage.getPageTotal())
                .setStorageId(user -> EWarmFlowHandlerType.USER.getCode() + user.getId()) // 前面拼接 user: 是为了防止用户、角色的主键重复
                .setHandlerCode(UserVO::getUsername) // 权限编码
                .setHandlerName(UserVO::getNickname) // 权限名称
                .setCreateTime(user -> DateUtil.formatLocalDateTime(user.getCreateTime()))
                .setGroupName(user -> StrUtil.defaultIfBlank(user.getDeptName(), Const.EMPTY));

        // 查询部门列表，构建树状结构
        DeptDO deptParam = new DeptDO();
        deptParam.setDisabled(EYesNo.N.getValue());
        LambdaQueryWrapper<DeptDO> deptQueryWrapper = deptService.buildQueryWrapper(deptParam);
        List<DeptVO> deptList = deptService.listVo(deptQueryWrapper.orderByAsc(DeptDO::getSeq));
        // 业务系统机构，转成组件内部左侧树列表能够显示的数据
        TreeFunDto<DeptVO> treeFunDto = new TreeFunDto<>(deptList)
                .setId(dept -> StrUtil.toStringOrNull(dept.getId())) // 左侧树ID
                .setName(DeptVO::getName) // 左侧树名称
                .setParentId(dept -> StrUtil.toStringOrNull(dept.getParentId())); // 左侧树父级ID

        return getHandlerSelectVo(handlerFunDto, treeFunDto);
    }

    /**
     * 获取角色列表
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getRole(HandlerQuery query) {
        // 查询角色列表
        RoleDO param = new RoleDO();
        param.setDisabled(EYesNo.N.getValue());
        param.setCode(query.getHandlerCode());
        param.setName(query.getHandlerName());
        this.setParamQueryTimeRange(query, param);

        PageQuery<RoleDO> pageQuery = PageQuery.of(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<RoleDO> roleQueryWrapper = roleService.buildQueryWrapper(param);
        PageQuery<RoleVO> rolePage = roleService.pageVo(pageQuery, roleQueryWrapper);

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<RoleVO> handlerFunDto = new HandlerFunDto<>(rolePage.getPageRecords(), rolePage.getPageTotal())
                // 以下设置获取内置变量的Function
                .setStorageId(role -> EWarmFlowHandlerType.ROLE.getCode() + role.getId()) // 前面拼接 role: 是为了防止用户、角色的主键重复
                .setHandlerCode(RoleVO::getCode) // 权限编码
                .setHandlerName(RoleVO::getName) // 权限名称
                .setCreateTime(role -> DateUtil.formatLocalDateTime(role.getCreateTime()));

        return getHandlerSelectVo(handlerFunDto);
    }

    /**
     * 获取部门列表
     *
     * @param query 查询条件
     * @return HandlerSelectVo
     */
    private HandlerSelectVo getDept(HandlerQuery query) {
        // 查询部门列表
        DeptDO param = new DeptDO();
        param.setDisabled(EYesNo.N.getValue());
        param.setCode(query.getHandlerCode());
        param.setName(query.getHandlerName());
        this.setParamQueryTimeRange(query, param);

        PageQuery<DeptDO> pageQuery = PageQuery.of(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<DeptDO> deptQueryWrapper = deptService.buildQueryWrapper(param);
        PageQuery<DeptVO> deptPage = deptService.pageVo(pageQuery, deptQueryWrapper);

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<DeptVO> handlerFunDto = new HandlerFunDto<>(deptPage.getPageRecords(), deptPage.getPageTotal())
                .setStorageId(dept -> EWarmFlowHandlerType.DEPT.getCode() + dept.getId()) // 前面拼接 dept: 是为了防止用户、部门的主键重复
                .setHandlerCode(DeptVO::getCode) // 权限编码
                .setHandlerName(DeptVO::getName) // 权限名称
                .setCreateTime(dept -> DateUtil.formatLocalDateTime(dept.getCreateTime()));

        return getHandlerSelectVo(handlerFunDto);
    }

    private HandlerSelectVo getPost(HandlerQuery query) {
        // 查询岗位列表
        PostDO param = new PostDO();
        param.setDisabled(EYesNo.N.getValue());
        param.setCode(query.getHandlerCode());
        param.setName(query.getHandlerName());
        this.setParamQueryTimeRange(query, param);

        PageQuery<PostDO> pageQuery = PageQuery.of(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<PostDO> postQueryWrapper = postService.buildQueryWrapper(param);
        PageQuery<PostVO> postPage = postService.pageVo(pageQuery, postQueryWrapper);

        // 业务系统数据，转成组件内部能够显示的数据, total是业务数据总数，用于分页显示
        HandlerFunDto<PostVO> handlerFunDto = new HandlerFunDto<>(postPage.getPageRecords(), postPage.getPageTotal())
                .setStorageId(post -> EWarmFlowHandlerType.POST.getCode() + post.getId()) // 前面拼接 post: 是为了防止用户、部门的主键重复
                .setHandlerCode(PostVO::getCode) // 权限编码
                .setHandlerName(PostVO::getName) // 权限名称
                .setCreateTime(post -> DateUtil.formatLocalDateTime(post.getCreateTime()));

        return getHandlerSelectVo(handlerFunDto);
    }

    private <E extends BaseEntity> void setParamQueryTimeRange(HandlerQuery query, E entity) {
        LocalDate startLocalDate = TimeUtil.parseDate(query.getBeginTime(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime startTime = Optional.ofNullable(startLocalDate).map(LocalDate::atStartOfDay).orElse(null);
        LocalDate endLocalDate = TimeUtil.parseDate(query.getEndTime(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime endTime = Optional.ofNullable(endLocalDate).map(LocalDate::atStartOfDay).orElse(null);

        entity.setStartCreateTime(startTime);
        entity.setEndCreateTime(endTime);
    }
}
