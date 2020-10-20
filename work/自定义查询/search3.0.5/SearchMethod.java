package com.jftt.wifi.search;

public class SearchMethod {

	private static final ThreadLocal<String> LOCAL_QUERY = new ThreadLocal<String>();
	
	
	protected static void setLocalSearch(String search) {
		LOCAL_QUERY.set(search);
	}
	
	protected static String getLocalSearch() {
		return LOCAL_QUERY.get();
	}
	
	protected static void clearLocalSearch() {
		LOCAL_QUERY.remove();
	}
}
