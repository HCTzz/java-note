package com.jftt.wifi.search;

/**搜索异常
 * @author Administrator
 *
 */
public class SearchException extends RuntimeException{
	
	private String message ;
	
	public SearchException(String message) {
		this.message = message;
	}
	

}
