package com.jftt.wifi.search.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jftt.wifi.bean.MMGridPageVoBean;
import com.jftt.wifi.bean.SysFieldBean;
import com.jftt.wifi.bean.SysShowSchemeBean;
import com.jftt.wifi.service.SysEntityService;
import com.jftt.wifi.service.SysShowSchemeService;
import com.jftt.wifi.util.CommonUtil;

/**展示方案配置模块
 * @author Administrator
 *
 */
@Controller
@RequestMapping("showScheme")
public class SysShowSchemeAction {

	private Logger log = LoggerFactory.getLogger(SysShowSchemeAction.class);
	
	@Autowired
	private SysEntityService sysEntityService;
	
	@Autowired
	private SysShowSchemeService sysShowSchemeService;
	
	
	/**到用户展示配置页面
	 * @param model
	 * @param entityRelation
	 * @param moudle
	 * @return
	 */
	@RequestMapping("userViewPage")
	public String userViewPage(ModelMap model,String entityRelation,String moudle) {
//		String userName = CommonUtil.getUserName();
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("userName", userName);
//		List<SysShowSchemeBean> schemeList = sysShowSchemeService.getViewSchemeList(map);
//		model.put("schemeList", schemeList);
		model.put("moudle", moudle);
		model.put("entityRelation", entityRelation);
		return "search/showConfig/entityconfig";
	}
	
	/**可用字段列表
	 * @param entityRelation
	 * @param moudle
	 * @return
	 */
	@RequestMapping("fieldList")
	@ResponseBody
	public Object fieldList(String entityRelation,String moudle) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		//查询当前人设置的默认的
		
		try {
			resultMap.put("status","y");
			map.put("entityRelation", entityRelation);
			map.put("userName", CommonUtil.getUserName());
			map.put("moudle", moudle);
			map.put("defaultFlag", "1");
			List<SysFieldBean> fieldList = sysEntityService.getUserUsedFieldList(map);
			if(fieldList.size() > 0) {
				resultMap.put("data", fieldList);
				return resultMap;
			}
			map.clear();
			map.put("entityRelation", entityRelation);
			//默认展示的字段
			map.put("defaultShow", "1");
			fieldList = sysEntityService.getFieldList(map);
			resultMap.put("data", fieldList);
		}catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "n");
			log.error("获取展示字段出错：=》", e.getMessage());
		}
		return resultMap;
	}
	
	/**查询用户已配置展示方案列表
	 * @param moudle
	 * @return
	 */
	@RequestMapping("userShowSchemeList")
	@ResponseBody
	public Object userShowSchemeList(String moudle) {
		String userName = CommonUtil.getUserName();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("moudle", moudle);
		List<SysShowSchemeBean> schemeList = sysShowSchemeService.getViewSchemeList(map);
		return schemeList;
	}
	
	@RequestMapping("userFieldList")
	@ResponseBody
	public Object userFieldList(String showSchemeKey,String entityRelation,String moudle) {
		MMGridPageVoBean<Map<String,Object>> mm = new MMGridPageVoBean<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("showSchemeKey", showSchemeKey);
		map.put("entityRelation", entityRelation);
		map.put("moudle", moudle);
		map.put("userName", CommonUtil.getUserName());
		List<Map<String,Object>> fieldList = sysShowSchemeService.getUserFieldList(map);
		mm.setRows(fieldList);
		mm.setTotal(fieldList.size());
		return mm;
	}
	
	/**保存展示方案
	 * @param moudle
	 * @param showSchemeName
	 * @param fields
	 * @return
	 */
	@RequestMapping("createShowScheme")
	@ResponseBody
	public Object createShowScheme(String moudle,String showSchemeName,String fields) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			int showScheme = sysShowSchemeService.createShowScheme(moudle,showSchemeName, fields);
			map.put("key", showScheme);
			map.put("status", "y");
		}catch (Exception e) {
			e.printStackTrace();
			log.error("保存展示方案失败",e.getMessage());
			map.put("status", "n");
			map.put("msg", "保存展示方案失败");
		}
		return map;
	}
	
	/**更新展示方案
	 * @param shcemeId
	 * @param moudle
	 * @param fields
	 * @return
	 */
	@RequestMapping("updateShowScheme")
	@ResponseBody
	public Object updateShowScheme(String shcemeId,String moudle,String fields) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
//			SysShowSchemeBean scheme = sysShowSchemeService.getSysShowSchemeById(shcemeId);
//			String showSchemeName = scheme.getShowSchemeName();
			int showScheme = sysShowSchemeService.updateShowScheme(Integer.parseInt(shcemeId), moudle, fields);
			map.put("key", showScheme);
			map.put("status", "y");
		}catch (Exception e) {
			e.printStackTrace();
			log.error("保存展示方案失败",e.getMessage());
			map.put("status", "n");
			map.put("msg", "保存展示方案失败");
		}
		return map;
	}
	
	/**删除展示方案
	 * @param shcemeId
	 * @return
	 */
	@RequestMapping("deleteShowScheme")
	@ResponseBody
	public Object deleteShowScheme(String shcemeId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		try {
			sysShowSchemeService.deleteSysScheme(shcemeId);
			paramMap.put("status", "y");
		}catch (Exception e) {
			e.printStackTrace();
			paramMap.put("status", "n");
		}
		return paramMap;
	}
	
	/**使用展示方案
	 * @param showSchemeKey
	 * @param moudle
	 * @return
	 */
	@RequestMapping("setShowScheme")
	@ResponseBody
	public Object setShowScheme(Integer showSchemeKey,String moudle) {
		Map<String,Object> map = new HashMap<String,Object>();
		SysShowSchemeBean schemeBean = new SysShowSchemeBean();
		schemeBean.setDefaultFlag(1);
		schemeBean.setShowSchemeKey(showSchemeKey);
		try {
			sysShowSchemeService.updateUserAllSchemeDefaultFlag("0", CommonUtil.getUserName(), moudle);
			sysShowSchemeService.update(schemeBean);
			map.put("status", "y");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "n");
		}
		return map;
	}
	
}
