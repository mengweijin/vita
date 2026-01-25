package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.bean.BeanUtil;
import com.github.mengweijin.vita.framework.domain.TeacherVO;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@Slf4j
class DiffUtilsTest {

    private static final TeacherVO t1;

    private static final TeacherVO t2;

    static {
        t1 = new TeacherVO();
        t1.setId(1L);
        t1.setName("张三");
        t1.setAge(13);
        t1.setCreateBy(0L);
        t1.setCreateTime(LocalDateTime.now().minusDays(1L));

        t2 = new TeacherVO();
        t2.setId(1L);
        t2.setName("李四");
        t2.setAge(16);
        t2.setCreateBy(0L);
        t2.setCreateTime(LocalDateTime.now());
    }

    @Test
    void diffMaps() {
        Map<String, Object> m1 = BeanUtil.beanToMap(t1);
        Map<String, Object> m2 = BeanUtil.beanToMap(t2);
        List<DiffModel<String, Object>> list = DiffUtils.diffMaps(m1, m2);

        Assertions.assertEquals(2, list.size());
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3() {
        List<DiffModel<String, String>> list = DiffUtils.getDiffModelByApacheCommonsLang3(t1, t2);
        printChange(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3ThrowsNullPointerException() {
        TeacherVO t1 = new TeacherVO();
        Assertions.assertThrows(NullPointerException.class, () -> DiffUtils.getDiffModelByApacheCommonsLang3(null, t1));
        Assertions.assertThrows(NullPointerException.class, () -> DiffUtils.getDiffModelByApacheCommonsLang3(t1, null));
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3Create() {
        TeacherVO t1 = new TeacherVO();

        TeacherVO t2 = new TeacherVO();
        t2.setId(1L);
        t2.setName("李四");
        t2.setAge(16);
        t2.setCreateBy(0L);
        t2.setCreateTime(LocalDateTime.now());

        List<DiffModel<String, String>> list = DiffUtils.getDiffModelByApacheCommonsLang3(t1, t2);
        printChange(list);
    }

    private void printChange(List<DiffModel<String, String>> list) {
        list.forEach(i -> log.info("字段 “{}” 从 “{}” 变更为 “{}”", i.getFieldName(), i.getOldValue(), i.getNewValue()));
    }

}