package com.jftt.wifi.search.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EntityRelationEnum {
	
	user(1,"内部人员"),
	user_out(2,"内部人员"),
	trian_arrange(3,"培训班计划"),
	trian_act(4,"培训班管理");
	
	private int code;
	
	private String desc;
	
	EntityRelationEnum(int code,String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	
	private static List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(13);
	
	public static List<Map<String,Object>> getEntityRelationList(){
		return list;
	}
	
	static {
		EntityRelationEnum[] values = EntityRelationEnum.values();
		for(EntityRelationEnum en : values) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", en.getDesc());
			map.put("value", en.getCode());
			list.add(map);
		}
	}
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
