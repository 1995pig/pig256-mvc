package cn.mldn.util.web;

import java.util.Map;

public class ModelAndView {
	private String url ;
	public ModelAndView(){}
	public ModelAndView(String url){
		this.url = url ;
	}
	public  void setUrl(String url){
		this.url = url ;
	}
	public String getUrl(){
		return this.url ;
	}
	/**
	 * 设置request属性
	 * @param name
	 * @param value
	 */
	public void add(String name,Object value){
		ServletObjectUtil.getRequest().setAttribute(name, value);
	}
	public void add(Map<String,Object> map){
		for(Map.Entry<String, Object> me : map.entrySet()){
			ServletObjectUtil.getRequest().setAttribute(me.getKey(), me.getValue());
		}
		
	}
	
}
