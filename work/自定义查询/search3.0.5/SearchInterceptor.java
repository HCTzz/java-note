package com.jftt.wifi.search;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/** 
 * 	mybatis拦截器，修改时请注意版本问题。
 * 	当前mybatis版本（3.0.5）
 * @author Administrator
 *
 */
@Intercepts({@Signature(
	    type = Executor.class,
	    method = "query",
	    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
	)})
public class SearchInterceptor implements Interceptor{

	private volatile Dialect dialect;
	
	private String default_dialect_class = "com.jftt.wifi.search.SearchHelper";
	
	public SearchInterceptor() {
    }
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		try {
			resetSql(invocation);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.proceed();
	}

	private void resetSql(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        BoundSql boundSql;
        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
        } else {
            boundSql = (BoundSql)args[5];
        }
        String sql = boundSql.getSql();
		if(StringUtils.isNotEmpty(sql)) {
			String newSql = this.dialect.getSearchSql(ms, sql);
			if(!StringUtils.isEmpty(newSql)) {
				BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
				if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
	                MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
	                Reflections.setFieldValue(newBoundSql, "metaParameters", mo);
	            }
				MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
		        args[0] = newMs;
			}
		}
    }
	
	public static void main(String[] args) {
		String str = "AND u.user_assort = ?  /where/";
		System.out.println(str.replaceAll("/where/", ""));
	}
	
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(ms.getKeyProperty());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
 
    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
        String dialectClass = this.default_dialect_class;
        try {
            Class<?> aClass = Class.forName(dialectClass);
            this.dialect = (Dialect)aClass.newInstance();
        } catch (Exception var4) {
            
        }
        this.dialect.setProperties(properties);
	}

}
