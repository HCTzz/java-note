package com.jftt.wifi.search;

import java.util.List;

import com.jftt.wifi.bean.SysFieldBean;

public class Entity {

	private String entityName;
	
	private String entityCaption;
	
	private Integer entityRelation;
	
	private List<SysFieldBean> fieldList;

	public String getEntityName() {
		return entityName;
	}

	public String getEntityCaption() {
		return entityCaption;
	}



	public void setEntityCaption(String entityCaption) {
		this.entityCaption = entityCaption;
	}



	public Integer getEntityRelation() {
		return entityRelation;
	}



	public void setEntityRelation(Integer entityRelation) {
		this.entityRelation = entityRelation;
	}



	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<SysFieldBean> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<SysFieldBean> fieldList) {
		this.fieldList = fieldList;
	}
	
}
