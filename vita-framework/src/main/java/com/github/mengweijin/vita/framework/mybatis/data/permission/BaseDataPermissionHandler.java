package com.github.mengweijin.vita.framework.mybatis.data.permission;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.mybatis.data.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link DataScope} Can only be used in *Mapper.java.
 * @author mengweijin
 * @since 2022/11/20
 */
@Slf4j
public abstract class BaseDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        log.debug("BaseDataPermissionHandler Expression where: {}", where);
        log.debug("BaseDataPermissionHandler mappedStatementId: {}", mappedStatementId);

        return MapperUtils.processMethodExpression(
                mappedStatementId,
                DataScope.class,
                dataScope -> this.dataScopeFilter(dataScope, where));
    }

    /**
     * 1、根据 dataScope 获取当前的用户的角色、部门信息
     * 2、再根据当前用户的角色、部门信息查询其下所有人员信息。
     * 3、拼接 where 条件 “ in user.id in (上一步的人员结果集合) ”
     * 4、返回拼接后的 Expression。返回 null 则表示不使用当前拦截器。
     */
    private Expression dataScopeFilter(DataScope dataScope, Expression where) {
        // 如果是特权用户，不控制数据权限
        if (this.isAdmin()) {
            return where;
        }

        Expression expression = null;
        switch (dataScope.scope()) {
            case USER -> {
                Long loginUserId = this.getLoginUserId();
                if (loginUserId != null) {
                    EqualsTo userEqualsTo = new EqualsTo();
                    userEqualsTo.setLeftExpression(buildColumn(dataScope));
                    userEqualsTo.setRightExpression(new LongValue(loginUserId));
                    expression = userEqualsTo;
                }
            }
            case DEPT -> {
                List<Long> loginUserDeptIdList = this.getLoginUserDeptIdList();
                if (CollUtil.isNotEmpty(loginUserDeptIdList)) {
                    InExpression deptInExpression = new InExpression();
                    deptInExpression.setLeftExpression(buildColumn(dataScope));
                    List<Expression> deptExpressionList = loginUserDeptIdList.stream().map(LongValue::new).collect(Collectors.toList());
                    deptInExpression.setRightExpression(new ExpressionList<>(deptExpressionList));
                    expression = deptInExpression;
                }
            }
            case ROLE -> {
                List<Long> loginUserRoleIdList = this.getLoginUserRoleIdList();
                if (CollUtil.isNotEmpty(loginUserRoleIdList)) {
                    InExpression roleInExpression = new InExpression();
                    roleInExpression.setLeftExpression(buildColumn(dataScope));
                    List<Expression> roleExpressionList = loginUserRoleIdList.stream().map(LongValue::new).collect(Collectors.toList());
                    roleInExpression.setRightExpression(new ExpressionList<>(roleExpressionList));
                    expression = roleInExpression;
                }
            }
            default -> {
                // ignore
            }
        }
        return where == null ? expression : new AndExpression(where, expression);
    }

    protected abstract Long getLoginUserId();

    protected abstract boolean isAdmin();

    protected abstract List<Long> getLoginUserDeptIdList();

    protected abstract List<Long> getLoginUserRoleIdList();

    /**
     * 构建Column
     *
     * @param dataScope dataScope
     * @return 带表别名字段
     */
    protected static Column buildColumn(DataScope dataScope) {
        String tableColumnName = dataScope.tableColumnName();
        if(StrValidator.isBlank(tableColumnName)) {
            tableColumnName = dataScope.scope().getColumnName();
        }
        String tableAlias = dataScope.tableAlias();
        tableAlias = StrValidator.isBlank(tableAlias) ? Const.EMPTY : tableAlias + Const.DOT;
        return new Column(tableAlias + tableColumnName);
    }
}
