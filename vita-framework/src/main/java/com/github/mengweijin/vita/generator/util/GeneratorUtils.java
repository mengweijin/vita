package com.github.mengweijin.vita.generator.util;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.date.DateFormatPool;
import cn.hutool.v7.core.date.DateUtil;
import cn.hutool.v7.core.net.NetUtil;
import cn.hutool.v7.core.reflect.FieldUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.StrValidator;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.generator.domain.bo.GeneratorBO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author mengweijin
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratorUtils {

    public static final PropertyPlaceholderHelper PLACEHOLDER_HELPER = new PropertyPlaceholderHelper("${", "}");
    private static final Class<BaseEntity> BASE_ENTITY_CLASS = BaseEntity.class;

    /**
     * If the user configured superEntityColumns, the configuration will prevail;
     * if not, the default configuration of superEntityColumns will be generated according to the superEntityClass.
     *
     * @return String
     */
    public static List<String> resolveBaseEntityColumns() {
        Field[] declaredFields = FieldUtil.getFieldsDirectly(BASE_ENTITY_CLASS, true);
        return Arrays.stream(declaredFields).map(field -> CharSequenceUtil.toUnderlineCase(field.getName()).toUpperCase()).toList();
    }

    public static String[] trimItems(String[] items) {
        if (items == null) {
            return new String[]{};
        }
        return Arrays.stream(items).map(String::trim).toArray(String[]::new);
    }

    public static String[] parseTablePrefix(String str) {
        if (StrValidator.isBlank(str)) {
            return new String[]{};
        }
        return trimItems(str.split("[,，;； ]"));
    }

    public static String resolveEntityName(String tableName, String tablePrefix) {
        String val = tableName;
        String[] tablePrefixArray = parseTablePrefix(tablePrefix);
        for (String prefix : tablePrefixArray) {
            if (tableName.toLowerCase().startsWith(prefix.toLowerCase())) {
                val = tableName.substring(prefix.length());
                break;
            }
        }
        return CharSequenceUtil.upperFirst(CharSequenceUtil.toCamelCase(val.toLowerCase()));
    }

    public static List<String> resolveCommonColumns(List<TableField> commonFields) {
        return commonFields.stream().map(tableField -> tableField.getColumnName().toUpperCase()).toList();
    }

    public static List<String> resolveEntityColumns(List<TableField> entityFields) {
        return entityFields.stream().map(tableField -> tableField.getColumnName().toUpperCase()).toList();
    }

    public static List<TableField> resolveCommonFields(TableInfo tableInfo, List<String> baseEntityColumns) {
        return tableInfo.getFields().stream().filter(tableField -> baseEntityColumns.contains(tableField.getColumnName().toUpperCase())).toList();
    }

    public static List<TableField> resolveEntityFields(TableInfo tableInfo, List<String> baseEntityColumns) {
        return tableInfo.getFields().stream().filter(tableField -> !baseEntityColumns.contains(tableField.getColumnName().toUpperCase())).toList();
    }

    public static TableField getIdField(TableInfo tableInfo) {
        return tableInfo.getFields().stream().filter(TableField::isKeyFlag).findFirst().orElse(null);
    }

    public static String getPackages(String packages, String moduleName) {
        if (StrValidator.isBlank(moduleName)) {
            return packages;
        }
        return String.join(".", packages, moduleName);
    }

    public static String replaceTemplateString(String templateString, Map<String, Object> objectMap) {
        Properties props = new Properties();
        objectMap.forEach((k, v) -> props.setProperty(k, CharSequenceUtil.toString(v)));
        return PLACEHOLDER_HELPER.replacePlaceholders(templateString, props);
    }

    public static Map<String, Object> getObjectMap(GeneratorBO args, TableInfo tableInfo) {
        List<String> baseEntityColumns = GeneratorUtils.resolveBaseEntityColumns();
        String entityName = GeneratorUtils.resolveEntityName(tableInfo.getName(), args.getTablePrefix());
        List<TableField> entityFields = GeneratorUtils.resolveEntityFields(tableInfo, baseEntityColumns);
        List<TableField> commonFields = GeneratorUtils.resolveCommonFields(tableInfo, baseEntityColumns);

        List<String> entityColumns = GeneratorUtils.resolveEntityColumns(entityFields);
        List<String> commonColumns = GeneratorUtils.resolveCommonColumns(commonFields);

        String requestMapping = "/" + CharSequenceUtil.toSymbolCase(entityName, '-');
        if (StrValidator.isNotBlank(args.getModuleName())) {
            requestMapping = CharSequenceUtil.addPrefixIfNot(args.getModuleName(), "/") + requestMapping;
        }

        Map<String, Object> objectMap = new HashMap<>(32);
        objectMap.put("module", args.getModuleName());
        objectMap.put("package", GeneratorUtils.getPackages(args.getPackages(), args.getModuleName()));
        objectMap.put("author", args.getAuthor());
        objectMap.put("date", DateUtil.format(LocalDateTime.now(Const.ZONE), DateFormatPool.NORM_DATE_PATTERN));
        objectMap.put("baseEntity", BASE_ENTITY_CLASS.getName());
        objectMap.put("baseEntityPackage", CharSequenceUtil.subBefore(BASE_ENTITY_CLASS.getName(), ".", true));
        objectMap.put("baseEntityName", CharSequenceUtil.subAfter(BASE_ENTITY_CLASS.getName(), ".", true));
        objectMap.put("baseEntityColumns", baseEntityColumns);
        objectMap.put("table", tableInfo);
        objectMap.put("idField", GeneratorUtils.getIdField(tableInfo));
        objectMap.put("entityName", entityName);
        objectMap.put("entityPropertyName", CharSequenceUtil.lowerFirst(entityName));
        objectMap.put("entityNameSymbolCase", StrUtil.toSymbolCase(CharSequenceUtil.lowerFirst(entityName), '-'));
        objectMap.put("entityFields", entityFields);
        objectMap.put("commonFields", commonFields);
        objectMap.put("allFields", CollUtil.addAll(new ArrayList<>(entityFields), new ArrayList<>(commonFields)));
        objectMap.put("entityColumns", entityColumns);
        objectMap.put("commonColumns", commonColumns);
        objectMap.put("allColumns", CollUtil.addAll(new ArrayList<>(entityColumns), new ArrayList<>(commonColumns)));
        objectMap.put("requestMapping", requestMapping);

        objectMap.put("vueApiName", CharSequenceUtil.toSymbolCase(entityName, '-'));
        objectMap.put("hutoolStrUtil", StrUtil.class);
        String nextId = new DefaultIdentifierGenerator(NetUtil.getLocalhostV4()).nextId(null).toString();
        objectMap.put("generatedId", CharSequenceUtil.subPre(nextId, nextId.length() - 2));
        return objectMap;
    }


}
