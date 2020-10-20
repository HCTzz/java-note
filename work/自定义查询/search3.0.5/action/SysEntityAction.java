package com.jftt.wifi.search.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jftt.wifi.bean.MMGridPageVoBean;
import com.jftt.wifi.bean.SysEntity;
import com.jftt.wifi.bean.SysFieldBean;
import com.jftt.wifi.search.Entity;
import com.jftt.wifi.search.SearchHelper;
import com.jftt.wifi.search.enums.CommonEnum;
import com.jftt.wifi.search.enums.CommonEnum.tableableEnum;
import com.jftt.wifi.search.enums.EntityRelationEnum;
import com.jftt.wifi.service.SysEntityService;
import com.jftt.wifi.util.CommonUtil;
import com.sun.star.uno.RuntimeException;
/**
 *	可用 表配置
 * @author Administrator
 *
 */
@Controller
@RequestMapping("entity")
public class SysEntityAction {

	@Autowired
	private SysEntityService sysEntityService;
	
	private final String dataBase = "zeln";
	
	/**表配置首页
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap model) {
		if(!"admin".equals(CommonUtil.getUserName().toLowerCase())) {
			throw new RuntimeException("权限不足！");
		}
		return "search/entityconfig";
	}
	
	/**表结构
	 * @return
	 */
	@RequestMapping("entityTreeList")
	@ResponseBody
	public Object entityTreeList() {
		SysEntity entity = new SysEntity();
		entity.setEntityKey(0);
		entity.setEntityCaptionName( "可用表(all)");
		List<SysEntity> entityList = sysEntityService.getEntityList();
		tableableEnum[] enums = CommonEnum.tableableEnum.values();
		Map<Integer, String> collect = Arrays.stream(enums).collect(Collectors.toMap(CommonEnum.tableableEnum::getCode, CommonEnum.tableableEnum::getDesc));
		for(SysEntity e : entityList) {
			e.setEntityCaptionName(e.getEntityCaptionName() + "(" + collect.getOrDefault(e.getEntityRelation(), SearchHelper.SPACE) + ")");
		}
		entityList.add(entity);
		return entityList;
	}
	
	/**	字段列表
	 * @param entityKey
	 * @return
	 */
	@RequestMapping("fieldList")
	@ResponseBody
	public Object fieldList(String entityKey) {
		MMGridPageVoBean<SysFieldBean> mm = new MMGridPageVoBean<SysFieldBean>();
		if(StringUtils.isEmpty(entityKey)) {
			mm.setTotal(0);
			return mm;
		}
		List<SysFieldBean> fieldList = sysEntityService.getFieldList(Integer.parseInt(entityKey));
		mm.setRows(fieldList);
		mm.setTotal(fieldList.size());
		return mm;
	}
	
	/**	可用表
	 * @param entityRelation
	 * @return
	 */
	@RequestMapping("entityList")
	@ResponseBody
	public Object entityList(String entityRelation) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entityRelation", entityRelation);
		List<SysEntity> entityList = sysEntityService.getEntityList(map);
		return entityList;
	}
	
	/**到新增可用表配置页面
	 * @param model
	 * @param entityKey
	 * @return
	 */
	@RequestMapping("toAddEntity")
	public String toAddEntity(ModelMap model,String entityKey) {
		List<String> tables = sysEntityService.getDatabbaseTables(dataBase);
		model.put("tables", tables);
		if(!StringUtils.isEmpty(entityKey)) {
			SysEntity entity = sysEntityService.getSysEntity(entityKey);
			model.put("entity", entity);
		}
		List<Map<String,Object>> relationList = EntityRelationEnum.getEntityRelationList();
		model.put("relationList", relationList);
		return "search/addEntity";
	}
	
	/**获取数据库表字段
	 * @param tableName
	 * @param entityRelation
	 * @return
	 */
	@RequestMapping("getTableColumns")
	@ResponseBody
	public Object getTableColumns(String tableName,String entityRelation) {
		MMGridPageVoBean<SysFieldBean> mm = new MMGridPageVoBean<SysFieldBean>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tableName", tableName);
			map.put("dataBase", dataBase);
			List<SysFieldBean> columns = sysEntityService.getTableColumns(map);
			//查看是否已配置
			map.put("entityRelation", entityRelation);
			List<SysFieldBean> fieldList = sysEntityService.getFieldList(map);
			if(fieldList.size() > 0) {
				Map<String,SysFieldBean> cMap = new HashMap<String,SysFieldBean>();
				for(SysFieldBean bean : fieldList) {
					cMap.put(bean.getFieldName(), bean);
				}
				List<SysFieldBean> newColumns = new ArrayList<SysFieldBean>(columns.size());
				for(SysFieldBean bean : columns) {
					if(cMap.containsKey(bean.getFieldName())) {
						newColumns.add(cMap.get(bean.getFieldName()));
					}else {
						newColumns.add(bean);
					}
				}
				mm.setRows(newColumns);
			}else {
				mm.setRows(columns);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mm;
	}
	
	/**新增/更新配置项
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdateEntity" , consumes = "application/json")
	@ResponseBody
	public Object saveOrUpdateEntity(@RequestBody Entity entity) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			sysEntityService.saveOrEditEntity(entity);
			resultMap.put("status", "y");
		}catch (Exception e) {
			resultMap.put("status", "n");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**删除字段
	 * @param fieldKey
	 * @return
	 */
	@RequestMapping("deleteField")
	@ResponseBody
	public Object deleteField(Integer fieldKey) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			sysEntityService.deleteField(fieldKey);
			map.put("status", "y");
		}catch (Exception e) {
			map.put("status", "n");
		}
		return map;
	}
	
	/**删除用户可用表
	 * @param entityKey
	 * @return
	 */
	@RequestMapping("deleteEntity")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public Object deleteEntity(Integer entityKey) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			sysEntityService.delteEntity(entityKey);
			sysEntityService.deleteFieldByEntityKey(entityKey);
			map.put("status", "y");
		}catch (Exception e) {
			map.put("status", "n");
		}
		return map;
	}
	
}
