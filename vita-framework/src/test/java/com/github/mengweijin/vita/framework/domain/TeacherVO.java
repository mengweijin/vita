package com.github.mengweijin.vita.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.DiffExclude;

import java.time.LocalDateTime;

/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {

    private Long id;

    private String name;

    private Integer age;

    private Long createBy;

    @DiffExclude
    private LocalDateTime createTime;
}
