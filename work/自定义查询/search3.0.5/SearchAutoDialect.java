package com.jftt.wifi.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.util.StringUtils;

import com.jftt.wifi.search.dialect.AbstractHelperDialect;
import com.jftt.wifi.search.dialect.helper.MysqlDialect;

public class SearchAutoDialect {

	private static Map<String, Class<? extends Dialect>> dialectAliasMap = new HashMap<String, Class<? extends Dialect>>();
	private boolean autoDialect = true;
	private boolean closeConn = true;
	private Properties properties;
	private Map<String, AbstractHelperDialect> urlDialectMap = new ConcurrentHashMap<String, AbstractHelperDialect>();
	private ReentrantLock lock = new ReentrantLock();
	private AbstractHelperDialect delegate;
	private ThreadLocal<AbstractHelperDialect> dialectThreadLocal = new ThreadLocal<AbstractHelperDialect>();

	public SearchAutoDialect() {
	}

	public static void registerDialectAlias(String alias, Class<? extends Dialect> dialectClass) {
		dialectAliasMap.put(alias, dialectClass);
	}

	public void initDelegateDialect(MappedStatement ms) {
		if (this.delegate == null) {
			if (this.autoDialect) {
				this.delegate = this.getDialect(ms);
			} else {
				this.dialectThreadLocal.set(this.getDialect(ms));
			}
		}
	}

	public AbstractHelperDialect getDelegate() {
		return this.delegate != null ? this.delegate : (AbstractHelperDialect) this.dialectThreadLocal.get();
	}

	public void clearDelegate() {
		this.dialectThreadLocal.remove();
	}

	private String fromJdbcUrl(String jdbcUrl) {
		Iterator var2 = dialectAliasMap.keySet().iterator();
		String dialect;
		do {
			if (!var2.hasNext()) {
				return null;
			}
			dialect = (String) var2.next();
		} while (jdbcUrl.indexOf(":" + dialect + ":") == -1);

		return dialect;
	}

	private Class resloveDialectClass(String className) throws Exception {
		return dialectAliasMap.containsKey(className.toLowerCase())
				? (Class) dialectAliasMap.get(className.toLowerCase())
				: Class.forName(className);
	}

	private AbstractHelperDialect initDialect(String dialectClass, Properties properties) {
		if (StringUtils.isEmpty(dialectClass)) {
			throw new SearchException("请在mybatis配置文件中配置search插件属性searchDialect");
		} else {
			AbstractHelperDialect dialect;
			try {
				Class sqlDialectClass = this.resloveDialectClass(dialectClass);
				if (!AbstractHelperDialect.class.isAssignableFrom(sqlDialectClass)) {
					throw new SearchException(
							"使用 搜索插件 时，方言必须是实现 " + AbstractHelperDialect.class.getCanonicalName() + " 接口的实现类!");
				}

				dialect = (AbstractHelperDialect) sqlDialectClass.newInstance();
			} catch (Exception var5) {
				throw new SearchException("初始化 helper [" + dialectClass + "]时出错:" + var5.getMessage());
			}

			dialect.setProperties(properties);
			return dialect;
		}
	}

	private String getUrl(DataSource dataSource) {
		Connection conn = null;
		String var3 = null;
		try {
			conn = dataSource.getConnection();
			var3 = conn.getMetaData().getURL();
		} catch (SQLException var12) {

		} finally {
			if (conn != null) {
				try {
					if (this.closeConn) {
						conn.close();
					}
				} catch (SQLException var11) {
				}
			}
		}
		return var3;
	}

	private AbstractHelperDialect getDialect(MappedStatement ms) {
		DataSource dataSource = ms.getConfiguration().getEnvironment().getDataSource();
		String url = this.getUrl(dataSource);
		if (this.urlDialectMap.containsKey(url)) {
			return (AbstractHelperDialect) this.urlDialectMap.get(url);
		} else {
			AbstractHelperDialect var4;
			try {
				this.lock.lock();
				if (!this.urlDialectMap.containsKey(url)) {
					if (StringUtils.isEmpty(url)) {
						throw new SearchException("无法自动获取jdbcUrl，请在分页插件中配置dialect参数!");
					}

					String dialectStr = this.fromJdbcUrl(url);
					if (dialectStr == null) {
						throw new SearchException("无法自动获取数据库类型，请通过 helperDialect 参数指定!");
					}

					AbstractHelperDialect dialect = this.initDialect(dialectStr, this.properties);
					this.urlDialectMap.put(url, dialect);
					AbstractHelperDialect var6 = dialect;
					return var6;
				}

				var4 = (AbstractHelperDialect) this.urlDialectMap.get(url);
			} finally {
				this.lock.unlock();
			}

			return var4;
		}
	}

	public void setProperties(Properties properties) {
		String dialect = properties.getProperty("searchDialect");
		if(StringUtils.isEmpty(dialect)) {
			this.autoDialect = true;
            this.properties = properties;
		}else{
			this.autoDialect = false;
	        this.delegate = this.initDialect(dialect, properties);
		}
		
	}

	static {
		registerDialectAlias("mysql", MysqlDialect.class);
	}

}
