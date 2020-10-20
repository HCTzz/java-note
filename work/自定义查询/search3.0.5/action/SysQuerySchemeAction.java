package com.jftt.wifi.search.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jftt.wifi.bean.SysQueryConditionBean;
import com.jftt.wifi.bean.SysQuerySchemeBean;
import com.jftt.wifi.search.enums.ComparisonCharactersEnum;
import com.jftt.wifi.service.SysQuerySchemeService;
import com.jftt.wifi.util.CommonUtil;

/**查询方案配置模块
 * @author Administrator
 *
 */
@RequestMapping("queryScheme")
@Controller
public class SysQuerySchemeAction {

	@Autowired
	private SysQuerySchemeService sysQuerySchemeService;
	
	/**到高级搜索配置页面
	 * @param moudle
	 * @param entityRelation
	 * @param model
	 * @return
	 */
	@RequestMapping("queryConfigPage")
	public String queryConfigPage(String moudle,String entityRelation,ModelMap model) {
		model.put("entityRelation", entityRelation);
		model.put("moudle", moudle);
		List<Map<String,Object>> list = ComparisonCharactersEnum.getComparisonCharactersList();
		model.put("compareList", list);
		return "search/queryConfig/queryconfig";
	}
	
	/**查询用户查询方案列表
	 * @param moudle
	 * @return
	 */
	@RequestMapping("querySchemeList")
	@ResponseBody
	public Object querySchemeList(String moudle) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("moudle", moudle);
		map.put("userName",CommonUtil.getUserName());
		try {
			List<SysQuerySchemeBean> schemeList = sysQuerySchemeService.getUserQuerySchemeList(map);
			resultMap.put("status", "y");
			resultMap.put("data", schemeList);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("status", "n");
		}
		return resultMap;
	}
	
	/**创建查询方案
	 * @param schemeKey
	 * @param express
	 * @param moudle
	 * @param querySchemeName
	 * @param conditions
	 * @return
	 */
	@RequestMapping("createQueryScheme")
	@ResponseBody
	public Object createQueryScheme(String schemeKey,String express,String moudle,String querySchemeName,String conditions) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Assert.notNull(moudle);
			Assert.notNull(querySchemeName);
			Assert.notNull(conditions);
			int key = sysQuerySchemeService.saveQueryScheme(schemeKey,express, moudle, querySchemeName, conditions);
			resultMap.put("status", "y");
			resultMap.put("key", key);
		}catch (Exception e) {
			resultMap.put("status", "n");
		}
		return resultMap;
	}
	
	
	/**获取方案查询条件
	 * @param schemeKey
	 * @return
	 */
	@RequestMapping("getShcemeCondition")
	@ResponseBody
	public Object getShcemeCondition(String schemeKey){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<SysQueryConditionBean> list = sysQuerySchemeService.getConditionsByShcemeKey(schemeKey);
			map.put("list", list);
			map.put("status", "y");
		}catch(Exception e) {
			map.put("status", "n");
		}
		return map;
	}
	
	/**删除查询方案
	 * @param schemeKey
	 * @return
	 */
	@RequestMapping("deleteScheme")
	@ResponseBody
	public Object deleteScheme(String schemeKey) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			sysQuerySchemeService.deleteSchemeInfoByKey(schemeKey);
			map.put("status", "y");
		}catch(Exception e) {
			map.put("status", "n");
		}
		return map;
	}
	
}
