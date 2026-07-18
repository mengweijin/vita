package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.core.map.MapUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.TeacherVO;
import com.github.mengweijin.vita.framework.log.datachange.DataChangeLogAspect;
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
        t1.setCreateTime(LocalDateTime.now(Const.ZONE).minusDays(1L));

        t2 = new TeacherVO();
        t2.setId(1L);
        t2.setName("李四");
        t2.setAge(16);
        t2.setCreateBy(0L);
        t2.setCreateTime(LocalDateTime.now(Const.ZONE));
    }

    @Test
    void diffMaps() {
        Map<String, String> m1 = MapUtil.map(BeanUtil.beanToMap(t1), (k, v) -> StrUtil.toStringOrNull(v));
        Map<String, String> m2 = MapUtil.map(BeanUtil.beanToMap(t2), (k, v) -> StrUtil.toStringOrNull(v));
        List<DiffModel> list = DiffUtils.diffMaps(m1, m2, DataChangeLogAspect.IGNORE_FIELDS.toArray(new String[0]));
        printChange(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3() {
        List<DiffModel> list = DiffUtils.diffBeans(t1, t2, DataChangeLogAspect.IGNORE_FIELDS.toArray(new String[0]));
        printChange(list);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3ThrowsNullPointerException() {
        TeacherVO t0 = new TeacherVO();
        String[] ignoreFields = DataChangeLogAspect.IGNORE_FIELDS.toArray(new String[0]);
        Assertions.assertThrows(NullPointerException.class, () -> DiffUtils.diffBeans(null, t0, ignoreFields));
        Assertions.assertThrows(NullPointerException.class, () -> DiffUtils.diffBeans(t0, null, ignoreFields));
    }

    @Test
    void diffBeanBeanByApacheCommonsLang3Create() {
        TeacherVO t0 = new TeacherVO();

        List<DiffModel> list = DiffUtils.diffBeans(t0, t2, DataChangeLogAspect.IGNORE_FIELDS.toArray(new String[0]));
        printChange(list);
        Assertions.assertEquals(3, list.size());
    }

    private void printChange(List<DiffModel> list) {
        list.forEach(i -> log.info("字段 “{}” 从 “{}” 变更为 “{}”", i.getFieldName(), i.getOldValue(), i.getNewValue()));
    }

}