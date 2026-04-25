package com.github.mengweijin.vita.framework.log.datachange;

import cn.hutool.v7.core.map.MapUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.jdbc.template.ColumnUpperCaseMapRowMapper;
import com.github.mengweijin.vita.framework.mybatis.MybatisMapperHelper;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DataAccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mengweijin
 * @since 2026/1/18
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class DataChangeLogAspect {

    @SuppressWarnings({"java:S2386"})
    public static final List<String> IGNORE_FIELDS = Arrays.asList("createBy", "createTime", "updateBy", "updateTime", "createByName", "updateByName");
    private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();
    private static final String SQL_TEMPLATE = "SELECT * FROM {} WHERE {} = ?";
    private static final List<String> IGNORE_COLUMNS = Arrays.asList("CREATE_BY", "CREATE_TIME", "UPDATE_BY", "UPDATE_TIME");
    private JdbcTemplate jdbcTemplate;
    private MybatisMapperHelper mybatisMapperHelper;
    private LogDataChangeService logDataChangeService;

    @Pointcut("@annotation(dataChangeLog)")
    public void pointCut(DataChangeLog dataChangeLog) {
    }

    @Around("pointCut(dataChangeLog)")
    public Object around(ProceedingJoinPoint joinPoint, DataChangeLog dataChangeLog) throws Throwable {
        Long businessId = this.parseBusinessId(joinPoint, dataChangeLog.businessId());
        Class<? extends BaseEntity> entityClass = dataChangeLog.entityClass();
        String tableName = mybatisMapperHelper.getTableName(entityClass);

        // 执行原方法前获取旧对象
        Map<String, String> beforeData = this.queryForMap(tableName, businessId);

        // 执行原方法
        Object proceed = joinPoint.proceed();

        // 执行原方法后获取新对象
        Map<String, String> afterData = this.queryForMap(tableName, businessId);

        // 保存数据变动日志
        logDataChangeService.saveWhenMapChange(tableName, businessId, beforeData, afterData, IGNORE_COLUMNS.toArray(new String[0]));

        return proceed;
    }

    private Map<String, String> queryForMap(String tableName, Long businessId) {
        try {
            String sql = StrUtil.format(SQL_TEMPLATE, tableName, VitaConst.COLUMN_ID);
            Map<String, Object> map = jdbcTemplate.queryForObject(sql, new ColumnUpperCaseMapRowMapper(), businessId);
            if (map != null) {
                return MapUtil.map(map, (k, v) -> StrUtil.toStringOrNull(v));
            }
        } catch (DataAccessException e) {
            log.warn("No existing record found for table: {}, id: {}", tableName, businessId);
        }
        return Map.of();
    }

    /**
     * 解析业务ID的Spring EL表达式
     */
    private Long parseBusinessId(JoinPoint joinPoint, String expression) {
        try {
            EvaluationContext context = new StandardEvaluationContext();

            // 添加方法参数到上下文
            Object[] args = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();

            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            Expression exp = SPEL_EXPRESSION_PARSER.parseExpression(expression);
            Object value = exp.getValue(context);
            return NumberUtil.parseLong(StrUtil.toStringOrNull(value));
        } catch (RuntimeException e) {
            log.error("The parsing of the businessId expression failed: {}", expression, e);
            throw new ServerException(e);
        }
    }

}
