package com.github.mengweijin.vita.framework.jdbc.template;

import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
 * When use the JdbcTemplate to query data, you can set a ColumnMapRowMapper,
 * this class can convert underline to camel case when you use JdbcTemplate to return a Map data list.
 *
 * @author mengweijin
 * @since 2022/10/29
 */
@SuppressWarnings({"unused"})
public class ColumnCamelCaseMapRowMapper extends ColumnMapRowMapper {

    /**
     * column name underline to camel
     *
     * @param columnName columnName
     * @return columnName
     */
    @Override
    protected String getColumnKey(String columnName) {
        return CharSequenceUtil.toCamelCase(columnName);
    }
}
