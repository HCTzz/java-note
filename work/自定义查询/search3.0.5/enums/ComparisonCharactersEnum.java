package com.jftt.wifi.search.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jftt.wifi.search.SearchHelper;

/** 
 * 	逻辑关系处理
 * @author Administrator
 *
 */
public enum ComparisonCharactersEnum{

	eq("eq"," = ","等于"){
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	lt("lt"," < ","小于") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	gt("gt"," > ","大于") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	let("let"," <= ","小于等于") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	get("get"," >= ","大于等于") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	neq("neq"," != ","不等于") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			return whereFormat + this.getOperator() + fieldValue;
		}
	},
	none("none"," is null , = '' ","空值") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			String[] more = this.getOperator().split("\\,");
			StringBuffer sb = new StringBuffer("(");
			sb.append(whereFormat).append(SearchHelper.SPACE).append(more[0]).append(SearchHelper.SPACE).append(SearchHelper.OR).append(SearchHelper.SPACE);
			sb.append(whereFormat).append(SearchHelper.SPACE).append(more[1]);
			sb.append(")");
			return sb.toString();
		}
	},
	notnone("notnone"," is not null , != '' ","非空值") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			String[] more = this.getOperator().split("\\,");
			StringBuffer sb = new StringBuffer("(");
			sb.append(whereFormat).append(SearchHelper.SPACE).append(more[0]).append(SearchHelper.SPACE).append(SearchHelper.AND).append(SearchHelper.SPACE);
			sb.append(whereFormat).append(SearchHelper.SPACE).append(more[1]);
			sb.append(")");
			return sb.toString();
		}
	},
	includ("includ"," like '%_v_%' ","包含") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	},
	exclud("exclud"," not like '%_v_%' ","不包含") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	},
	startwith("startwith"," like '_v_%' ","以什么开头") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	},
	notstartwith("notstartwith"," not like '_v_%' ","不以什么开头") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	},
	endwith("endwith"," like '%_v_' ","以什么结尾") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	},
	notendwith("notendwith"," not like '%_v_' ","不以什么结尾") {
		@Override
		public String handle(int actionFlag,String whereFormat,String fieldValue) {
			fieldValue = fieldValue.replaceAll("'", "");
			return whereFormat + this.getOperator().replaceAll(placeholder, fieldValue);
		}
	};
	
	public final String placeholder = "_v_";
	
	private String name;
	
	private String operator;
	
	private String operatorName;
	
	ComparisonCharactersEnum(String name,String operator,String operatorName){
		this.name = name;
		this.operator = operator;
		this.operatorName = operatorName;
	}

	public abstract String handle(int actionFlag,String whereFormat,String fieldValue);
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private static List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(13);
	
	public static List<Map<String,Object>> getComparisonCharactersList(){
		return list;
	}
	
	static {
		ComparisonCharactersEnum[] values = ComparisonCharactersEnum.values();
		for(ComparisonCharactersEnum en : values) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", en.getOperatorName());
			map.put("value", en.getName());
			list.add(map);
		}
	}

}
