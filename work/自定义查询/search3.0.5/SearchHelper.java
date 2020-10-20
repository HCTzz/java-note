package com.jftt.wifi.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.util.StringUtils;

import com.jftt.wifi.bean.SysQueryConditionBean;
import com.jftt.wifi.search.enums.ComparisonCharactersEnum;
import com.jftt.wifi.service.SysQuerySchemeService;

public class SearchHelper extends SearchMethod implements Dialect{

	private SearchAutoDialect autoDialect;
	
	private static Map<String,String> quotaMap;
	
	public final static String AND = "AND";
	
	public final static String OR = "OR";
	
	private final static String AND_KEY = "\\+";
	
	private final static String OR_KEY = "\\,";
	
	private final static Pattern NUMREG = Pattern.compile("\\d+");
	
	public final static String SPACE = " ";
	
	/**	
	 * 	处理自定义查询语句
	 * 		1、存在schemeKey，则根据对应的方案进行查询条件处理
	 * 		2、不存在schemeKey，若存在逻辑表达式以及查询条件，则根据该参数生成查询条件
	 * 		3、没有查询相关的参数，则忽略此次查询请求。
	 * 		4、查询条件分隔符使用#，前端请勿出现#符号。
	 * 	生成的条件语句放入该线程的本地存储中方便后续查询使用。
	 * @param request
	 * @param sysQuerySchemeService
	 */
	public static void startQuery(HttpServletRequest request,SysQuerySchemeService sysQuerySchemeService) {
		String schemeKey = request.getParameter("schemeKey");
		String expression ;
		List<SysQueryConditionBean> list = new ArrayList<SysQueryConditionBean>(1);
		if(StringUtils.isEmpty(schemeKey)) {
			String simpleConditions = request.getParameter("simpleConditions");
			expression = request.getParameter("simpleExpress");
			if(!StringUtils.isEmpty(expression)) {
				String[] cons = simpleConditions.split("##");
				List<Integer> filedList = new ArrayList<Integer>(cons.length);
				for(String str : cons) {
					String[] stra = str.split("#");
					SysQueryConditionBean conditionBean = new SysQueryConditionBean();
					int field = Integer.parseInt(stra[0]);
					filedList.add(field);
					conditionBean.setFieldKey(field);
					conditionBean.setOperator(stra[1]);
					if(stra.length > 2) {
						conditionBean.setDisplayValue(stra[2]);
					}
					if(stra.length > 3) {
						conditionBean.setFieldValue(stra[3]);
					}
					list.add(conditionBean);
				}
				List<SysQueryConditionBean> otherInfoList = sysQuerySchemeService.getConditionsByFieldKey(org.apache.commons.lang.StringUtils.join(filedList, ","));
				Map<Integer,String> map = otherInfoList.stream().collect(Collectors.toMap(SysQueryConditionBean::getFieldKey, SysQueryConditionBean::getWhereFormat));
				for(SysQueryConditionBean bean : list) {
					bean.setActionFlag(3);
					bean.setWhereFormat(map.get(bean.getFieldKey()));
				}
			}
		}else {
			list = sysQuerySchemeService.getConditionsByShcemeKey(schemeKey);
			expression = list.get(0).getConditionExpression();
		}
		if(list.size() > 0) {
			int length = list.size();
			Map<Integer,String> map = new HashMap<Integer,String>(length);
			for(int i = 0; i < list.size() ; i++) {
				SysQueryConditionBean bean  = list.get(i); 
				String whereFormat = bean.getWhereFormat();
				String operator = bean.getOperator();
				String fieldValue = bean.getFieldValue();
				String displayValue = bean.getDisplayValue();
				int actionFlag = bean.getActionFlag() ;
				String str = ComparisonCharactersEnum.valueOf(operator).handle(actionFlag,whereFormat,StringUtils.isEmpty(fieldValue) ? packQuotes(actionFlag, displayValue) :  packQuotes(actionFlag, fieldValue));
				map.put(i+1, str);
			}
			expression = expression.replaceAll(AND_KEY, quotaMap.get(AND_KEY)).replaceAll(OR_KEY, quotaMap.get(OR_KEY));
			Matcher matcher = NUMREG.matcher(expression);
			String[] splits = expression.split("\\d+");
			if(splits.length == 0) {
				splits = new String[]{""};
			}
			String[] positions = new String[splits.length];
			int i = 0;
			while (matcher.find()) {
				String m = matcher.group();
				positions[i] = m;
				i ++;
			}
			StringBuffer sb = new StringBuffer();
			for(int j = 0; j< splits.length ; j++) {
				sb.append(splits[j]).append(SPACE).append(map.get(Integer.parseInt(positions[j]))).append(SPACE);
			}
			setLocalStore(sb.toString());
		}
	}
	
	private static String packQuotes(int actionFlag,String val) {
//		if(actionFlag > 1) {
//			return "'"+val+"'";
//		}
		return "'"+val+"'";
	}
	
	public static void setLocalStore(String str) {
		setLocalSearch(str);
	}
	
	//获取本地存储
	public static String getSearch() {
		return getLocalSearch();
	}
	
	//清空本地存储
	public static void clearLocalStore() {
		clearLocalSearch();
	}
	
	@Override
	public String getSearchSql(MappedStatement ms,String sql) {
		this.autoDialect.initDelegateDialect(ms);
		return this.autoDialect.getDelegate().getSearchSql(ms,sql);
	}


	@Override
	public void setProperties(Properties properties) {
		this.autoDialect = new SearchAutoDialect();
		this.autoDialect.setProperties(properties);
	}

	@Override
	public void afterSearch() {
		clearLocalStore();
	}
	
	static {
		quotaMap = new HashMap<String,String>();
		quotaMap.put("\\+", "AND");
		quotaMap.put("\\,", "OR");
	}
	
	public static void main(String[] args) {
		String expression = "1,";
		String[] splits = expression.split("\\d+");
		for(String s : splits) {
			System.out.println(s);
		}
	}
}
