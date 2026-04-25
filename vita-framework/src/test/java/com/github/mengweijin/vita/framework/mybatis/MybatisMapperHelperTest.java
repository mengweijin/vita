package com.github.mengweijin.vita.framework.mybatis;

import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author mengweijin
 * @since 2026/1/18
 */
@ExtendWith(MockitoExtension.class)
class MybatisMapperHelperTest {

    private static MybatisMapperHelper mybatisMapperHelper;
    @Mock
    private UserMapper userMapper;

    @BeforeAll
    static void beforeAll() {
        mybatisMapperHelper = new MybatisMapperHelper();
    }

    @Test
    void mockSimpleTest() {
        Mockito.when(userMapper.selectById(1L)).thenReturn(new UserDO());
        UserDO userDO = userMapper.selectById(1L);
        Assertions.assertNotNull(userDO);
    }

    @Test
    void getEntityClassByMapper() {
        Class<?> entityClass = mybatisMapperHelper.getEntityClassByMapper(userMapper);
        Assertions.assertEquals(UserDO.class, entityClass);
    }


}