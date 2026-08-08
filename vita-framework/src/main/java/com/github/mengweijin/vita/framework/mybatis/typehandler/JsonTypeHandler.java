package com.github.mengweijin.vita.framework.mybatis.typehandler;

import cn.hutool.v7.db.driver.DriverNames;
import cn.hutool.v7.db.driver.DriverUtil;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 * H2:
 * 数据库使用 JSON 类型。
 * </p>
 * <p>
 * POSTGRESQL:
 * 数据库使用 jsonb 类型；
 * JDBC 连接串需添加参数：?stringtype=unspecified，否则可能报类型不匹配错误。
 * </p>
 *
 * @author mengweijin
 * @since 2026/1/31
 */
@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.OTHER, JdbcType.VARCHAR})
public class JsonTypeHandler extends JacksonTypeHandler {

    public JsonTypeHandler(Class<?> type) {
        super(type);
    }

    /**
     * 自 mybatis-plus 3.5.6 版本开始支持泛型,需要加上此构造.
     *
     * @param type  type
     * @param field field
     */
    public JsonTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String json = toJson(parameter);
        String url = ps.getConnection().getMetaData().getURL();
        String driverClassName = DriverUtil.identifyDriver(url);
        if (DriverNames.DRIVER_POSTGRESQL.equals(driverClassName)) {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(json);
            ps.setObject(i, pgObject);
        } else if (DriverNames.DRIVER_H2.equals(driverClassName)) {
            // 在 H2 中，虽然它有 JSON 类型，但为了更好的兼容性，通常建议将字段定义为 VARCHAR 或 TEXT 类型。
            ps.setString(i, json);
        } else {
            // 其他数据库
            ps.setObject(i, json);
        }
    }

}