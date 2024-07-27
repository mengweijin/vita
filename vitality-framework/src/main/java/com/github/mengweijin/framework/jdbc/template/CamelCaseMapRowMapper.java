package com.github.mengweijin.framework.jdbc.template;

import org.dromara.hutool.core.text.CharSequenceUtil;
import org.jetbrains.annotations.NotNull;
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
     * column name underline to camel
     *
     * @param columnName columnName
     * @return columnName
     */
    @NotNull
    @Override
    protected String getColumnKey(String columnName) {
        return CharSequenceUtil.toCamelCase(columnName.toLowerCase(Locale.ROOT));
    }
}
