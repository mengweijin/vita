package com.github.mengweijin.vita.framework.log.datachange;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.jdbc.template.ColumnUpperCaseMapRowMapper;
import com.github.mengweijin.vita.framework.log.operation.EOperationType;
import com.github.mengweijin.vita.framework.mybatis.MybatisMapperHelper;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
import com.github.mengweijin.vita.system.constant.VitaConst;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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

    private JdbcTemplate jdbcTemplate;

    private MybatisMapperHelper mybatisMapperHelper;

    private LogDataChangeService logDataChangeService;

    private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

    private static final String SQL_TEMPLATE = "SELECT * FROM {} WHERE {} = ?";

    private static final String[] IGNORE_COLUMNS = new String[]{"CREATE_BY", "CREATE_TIME", "UPDATE_BY", "UPDATE_TIME"};

    @Pointcut("@annotation(dataChangeLog)")
    public void pointCut(DataChangeLog dataChangeLog) {}

    @Around("pointCut(dataChangeLog)")
    public Object around(ProceedingJoinPoint joinPoint, DataChangeLog dataChangeLog) throws Throwable {
        Long businessId = this.parseBusinessId(joinPoint, dataChangeLog.businessId());
        Class<? extends BaseEntity> entityClass = dataChangeLog.entityClass();
        String tableName = mybatisMapperHelper.getTableName(entityClass);
        EOperationType operationType = dataChangeLog.operationType();

        // 执行原方法前获取旧对象
        Map<String, Object> beforeData = this.queryForMap(tableName, businessId);

        // 执行原方法
        Object proceed = joinPoint.proceed();

        // 执行原方法后获取新对象
        Map<String, Object> afterData = this.queryForMap(tableName, businessId);

        // 保存日志
        logDataChangeService.saveWhenChange(tableName, businessId, operationType, beforeData, afterData, IGNORE_COLUMNS);

        return proceed;
    }

    private Map<String, Object> queryForMap(String tableName, Long businessId) {
        Map<String, Object> beforeData;
        try {
            String sql = StrUtil.format(SQL_TEMPLATE, tableName, VitaConst.COLUMN_ID);
            // beforeData = jdbcTemplate.queryForMap(sql, businessId);
            beforeData = jdbcTemplate.queryForObject(sql, new ColumnUpperCaseMapRowMapper(), businessId);
        } catch (Throwable e) {
            log.warn("No existing record found for table: {}, id: {}", tableName, businessId);
            beforeData = Map.of();
        }
        return beforeData;
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
        } catch (Throwable e) {
            log.error("The parsing of the businessId expression failed: {}", expression, e);
            throw new ServerException(e);
        }
    }

}
