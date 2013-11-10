package org.matrix.mybatis;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年11月8日 下午11:00:04
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SQLInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		// MetaObject metaStatementHandler =
		// MetaObject.forObject(statementHandler);
		// String originalSql = (String)
		// metaStatementHandler.getValue("delegate.boundSql.sql");
		String originalSql = statementHandler.getBoundSql().getSql();
		File sqlFile = null;
		List<String> lines = null;
		Object param = statementHandler.getParameterHandler().getParameterObject();
		synchronized (this) {
			sqlFile = new File(DateFormatUtils.format(new Date(), "yyyyMMdd") + "-sql.txt");
			lines = new ArrayList<String>();
			lines.add(originalSql);
			System.out.println("SQL:" + originalSql);
			JSONObject jsonObject = JSONObject.fromObject(param);
			lines.add(jsonObject.toString());
			System.out.println("param:" + jsonObject.toString());
			FileUtils.writeLines(sqlFile, "utf-8", lines, true);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties);
	}
}
