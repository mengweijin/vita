package com.github.mengweijin.vita.framework.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EWorkflowCode implements IEnum<String> {

    /**
     * 请假流程
     */
    WF_OA_LEAVE("wf_oa_leave");

    private final String value;

}
