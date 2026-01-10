package com.github.mengweijin.vita.framework.util;

import com.github.mengweijin.vita.framework.domain.TeacherVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@Slf4j
class DiffUtilsTest {

    @Test
    void diffBean() {
        TeacherVO t1 = new TeacherVO();
        t1.setId(1L);
        t1.setName("张三");
        t1.setAge(13);
        t1.setCreateBy(0L);
        t1.setCreateTime(LocalDateTime.now().minusDays(1L));

        TeacherVO t2 = new TeacherVO();
        t2.setId(1L);
        t2.setName("李四");
        t2.setAge(16);
        t2.setCreateBy(0L);
        t2.setCreateTime(LocalDateTime.now());

        DiffBuilder<Object> builder = DiffBuilder.builder().setLeft(t1).setRight(t2).setStyle(ToStringStyle.JSON_STYLE).build();
        ReflectionDiffBuilder<Object> diffBuilder = ReflectionDiffBuilder.builder().setDiffBuilder(builder).build();
        DiffResult<?> diffResult = diffBuilder.build();

        List<Diff<?>> diffs = diffResult.getDiffs();
        if (diffs.isEmpty()) {
            log.info("两个对象完全相同。");
        } else {
            log.info("发现 {} 处差异：", diffs.size());
            for (Diff<?> diff : diffs) {
                // 获取发生变化的字段名
                String fieldName = diff.getFieldName();
                // 获取两个对象中该字段的值
                Object oldValue = diff.getLeft();
                Object newValue = diff.getRight();

                log.info("字段 “{}” 从 “{}” 变更为 “{}”", fieldName, oldValue, newValue);
            }
        }

        Assertions.assertEquals(2, diffs.size());
    }
}