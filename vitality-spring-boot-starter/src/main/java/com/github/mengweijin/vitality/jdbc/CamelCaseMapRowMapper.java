package com.github.mengweijin.vitality.jdbc;

import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.util.Locale;

/**
 * When use the JdbcTemplate to query data, you can set a ColumnMapRowMapper,
 * this class can convert underline to camel case when you use JdbcTemplate to return a Map data list.
 *
 * @author mengweijin
 */
public class CamelCaseMapRowMapper extends ColumnMapRowMapper {

    /**
     * column name under line to camel
     *
     * @param columnName columnName
     * @return columnName
     */
    @Override
    protected String getColumnKey(String columnName) {
        return CharSequenceUtil.toCamelCase(columnName.toLowerCase(Locale.ROOT));
    }
}
