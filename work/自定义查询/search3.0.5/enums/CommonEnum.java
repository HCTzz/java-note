package com.jftt.wifi.search.enums;

/**
 * 	无实际作用，仅做解释用
 * @author Administrator
 *
 */
public class CommonEnum {

	/**
	 * 	模块可使用表配置项 对应 sys_entity【entity_relation】
	 * @author Administrator
	 *
	 */
	public static enum tableableEnum{
		user_inner(1,"内部学员"),
		user_out(2,"外部学员"),
		trian_arrange(3,"培训班计划"),
		trian_act(4,"培训班管理");
		
		private int code;
		
		private String desc;
		tableableEnum(int code,String desc){
			this.code = code;
			this.desc = desc;
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
	
	
	/**
	 * 	页面显示方案配置项 （对应参数：moudle）
	 * @author Administrator
	 *
	 */
	public static enum pageViewEnum{
		
		inner(1,"内部学员配置项"),
		out(2,"内部学员配置项"),
		trian_arrange(3,"培训班计划"),
		trian_act(4,"培训班管理");
		
		private int code;
		
		private String desc;
		pageViewEnum(int code,String desc){
			this.code = code;
			this.desc = desc;
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
	
	
	
	public static enum ActionFlagEnum{
		
		dept(1,"部门"),
		syscode(2,"字典表"),
		imput(3,"普通输入");
		
		private int code;
		
		private String desc;
		ActionFlagEnum(int code,String desc){
			this.code = code;
			this.desc = desc;
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
}
