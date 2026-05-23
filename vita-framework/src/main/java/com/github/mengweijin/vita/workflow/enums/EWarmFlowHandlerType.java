package com.github.mengweijin.vita.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author mengweijin
 * @since 2026/5/23
 */
@Getter
@AllArgsConstructor
public enum EWarmFlowHandlerType {

    USER("user:", "用户"),
    ROLE("role:", "角色"),
    DEPT("dept:", "部门"),
    POST("post:", "岗位"),
    ;

    private final String code;

    private final String desc;

    public static List<String> getDescList() {
        return List.of(USER.desc, ROLE.desc, DEPT.desc, POST.desc);
    }

    public static EWarmFlowHandlerType fromDesc(String desc) {
        for (EWarmFlowHandlerType value : values()) {
            if (value.desc.equals(desc)) {
                return value;
            }
        }
        return USER;
    }

}
