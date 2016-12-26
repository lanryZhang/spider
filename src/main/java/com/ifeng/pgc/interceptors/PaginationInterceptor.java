package com.ifeng.pgc.interceptors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Pattern;

@Intercepts(@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }))
public class PaginationInterceptor implements Interceptor {

	private final static String SQL_SELECT_REGEX = "(?is)^\\s*SELECT.*$";
	private final static String SQL_COUNT_REGEX = "(?is)^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$";

	@Override
	public Object intercept(Invocation inv) throws Throwable {
		StatementHandler target = (StatementHandler) inv.getTarget();
		BoundSql boundSql = target.getBoundSql();
		String sql = boundSql.getSql();
		if (sql.isEmpty()) {
			inv.proceed();
		}
		if (sql.matches(SQL_SELECT_REGEX) && !Pattern.matches(SQL_COUNT_REGEX, sql)) {
			Object obj = FieldUtils.readField(target, "delegate", true);
			RowBounds rowBounds = (RowBounds) FieldUtils.readField(obj, "rowBounds", true);
			if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {

				FieldUtils.writeField(boundSql, "sql", newSql(sql, rowBounds), true);
				FieldUtils.writeField(rowBounds, "offset", RowBounds.NO_ROW_OFFSET, true);
				FieldUtils.writeField(rowBounds, "limit", RowBounds.NO_ROW_LIMIT, true);
			}
		}

		return inv.proceed();
	}

	private String newSql(String oldSql, RowBounds rowBounds) {
		return oldSql.concat(" limit ") + rowBounds.getLimit() + " ," + rowBounds.getOffset();
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {

	}
}
