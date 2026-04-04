package com.github.mengweijin.vita.framework.mybatis.typehandler;

import com.github.mengweijin.vita.framework.constant.Const;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 逗号分隔字符串 和 List<String> 类型互转处理器
 * @author mengweijin
 * @since 2026/4/4
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CommaDelimitedListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(Const.COMMA, parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return parseToList(value);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return parseToList(value);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return parseToList(value);
    }

    private List<String> parseToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(value.split(Const.COMMA));
    }
}