package cn.mldn.util.action;

import cn.mldn.util.web.ParameterValueUtil;
import cn.mldn.util.web.ServletObjectUtil;

public class ActionSplitPageUtil {
	private int currentPage = 1;	 		 	
	private int lineSize = 10;
	private String column;
	private String keyWord ;
	public ActionSplitPageUtil(String columnData,String urlKey){
		ServletObjectUtil.getRequest().setAttribute("columnData",columnData);
		ServletObjectUtil.getRequest().setAttribute("url", ActionMessageUtil.getUrl(urlKey));
		this.handleParameter(); 
	}
	private void handleParameter(){				//针对于所有的分页参数进行处理
		try{
			currentPage = Integer.parseInt(ParameterValueUtil.getParameter("cp")) ;
		}catch(Exception e){}
		try{
			lineSize = Integer.parseInt(ParameterValueUtil.getParameter("ls")) ;
		}catch(Exception e){}
		this.column = ParameterValueUtil.getParameter("col") ;
		this.keyWord = ParameterValueUtil.getParameter("kw") ;
		ServletObjectUtil.getRequest().setAttribute("currentPage", this.currentPage);
		ServletObjectUtil.getRequest().setAttribute("lineSize",this.lineSize);
		ServletObjectUtil.getRequest().setAttribute("keyWord", this.keyWord);
		ServletObjectUtil.getRequest().setAttribute("column",this.column);  	
	}
	public int getCurrentPage(){
		return currentPage ;
	}
	public int getLineSize(){
		return lineSize ;
	}
	public String getColumn(){
		return column ;
	}
	public String getKeyWord(){
		return keyWord ;
	} 
}
