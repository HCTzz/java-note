package com.jftt.wifi.search;

import java.util.Properties;

import org.apache.ibatis.mapping.MappedStatement;

public interface Dialect {

	String getSearchSql(MappedStatement ms,String sql);
	
	void setProperties(Properties var1);
	
	void afterSearch();
}
