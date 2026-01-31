package com.github.mengweijin.vita.framework.mybatis.typehandler;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.poi.ss.formula.functions.T;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mengweijin
 * @since 2026/1/31
 */
@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.OTHER})
public class JsonTypeHandler extends JacksonTypeHandler {

    public JsonTypeHandler(Class<T> type) {
        super(type);
    }

    /**
     * 自 mybatis-plus 3.5.6 版本开始支持泛型,需要加上此构造.
     * @param type type
     * @param field field
     */
    public JsonTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String databaseProductName = ps.getConnection().getMetaData().getDatabaseProductName();

        if(isPostgreSQL(databaseProductName)) {
            // PostgreSQL
            var jsonbObject = new PGobject();
            jsonbObject.setType("json");
            jsonbObject.setValue(toJson(parameter));
            ps.setObject(i, jsonbObject);
        } else if(isMySQL(databaseProductName)) {
            // MySQL、MariaDB
            ps.setObject(i, toJson(parameter), JdbcType.OTHER.TYPE_CODE);
        } else if(isH2(databaseProductName)) {
            // H2
            String json = toJson(parameter);
            ps.setString(i, toJson(parameter));
            // ps.setObject(i, parameter, H2Type.JSON);
        } else {
            // 其他数据库走 JacksonTypeHandler，默认处理为字符串
            super.setNonNullParameter(ps, i, parameter, jdbcType);
        }
    }

    private boolean isPostgreSQL(String databaseProductName) {
        return StrUtil.containsAnyIgnoreCase(databaseProductName, "PostgreSQL", "Postgres");
    }

    private boolean isH2(String databaseProductName) {
        return StrUtil.containsAnyIgnoreCase(databaseProductName, "H2");
    }

    private boolean isMySQL(String databaseProductName) {
        return StrUtil.containsAnyIgnoreCase(databaseProductName, "MySQL", "MariaDB");
    }

}