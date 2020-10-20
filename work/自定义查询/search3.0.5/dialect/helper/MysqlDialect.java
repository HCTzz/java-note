package com.jftt.wifi.search.dialect.helper;


import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.util.StringUtils;

import com.jftt.wifi.search.SearchHelper;
import com.jftt.wifi.search.dialect.AbstractHelperDialect;

/**
 * 	处理mysql数据库自定义查询查询语句替换
 * @author Administrator
 *
 */
public class MysqlDialect extends AbstractHelperDialect{
	
	@Override
	public String getSearchSql(MappedStatement ms,String sql) {
		if(!sql.contains(WHERE)) {
			return null;
		}
		String where = SearchHelper.getSearch();
		if( StringUtils.isEmpty(where)) {
			return sql.replace(WHERE, SearchHelper.SPACE);
		}
		int whereIndex = sql.indexOf(WHERE);
		String tempSql = sql.substring(0,whereIndex);
		int lastWhereIndex = tempSql.toLowerCase().lastIndexOf(WHERE_LOCAL);
		tempSql = sql.substring(lastWhereIndex + WHERE_LOCAL.length(), whereIndex);
		if(!StringUtils.isEmpty(tempSql) && tempSql.trim().length() > 0) {
			//+ OPEN + where + CLOSE
			sql = sql.replaceAll(WHERE, SearchHelper.AND + SearchHelper.SPACE + where );
		}else {
			sql = sql.replaceAll(WHERE, SearchHelper.SPACE + where );
		}
		return sql;
	}

	@Override
	public void afterSearch() {
		
	}

	

}
