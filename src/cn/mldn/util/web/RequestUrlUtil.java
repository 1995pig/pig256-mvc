 package cn.mldn.util.web;
 
import javax.servlet.http.HttpServletRequest;
 public class RequestUrlUtil {
	private RequestUrlUtil(){}
	/**
	 * 进行给定的请求路径的信息拆分，要求可以拆分出要操作的Action路径和方法名称
	 * @param request 包含所有的请求信息，通过此对象取得路径信息
	 * @return 需要返回两个结果：0 = 访问的action路径、1 = 方法名称
	 */ 
	public static String[] splitUrl(HttpServletRequest request) { 
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "") ; // 得到请求的URI
		String result [] = uri.split("!") ;
  		String str [] = new String [] {result[0],result[1].substring(0,result[1].indexOf("."))} ;
		return str ; 
	} 
	public static String getActionName(HttpServletRequest request){	//取得Action的名字
		String uri = request.getRequestURI() ;	
		return uri.substring(uri.lastIndexOf("/") + 1,uri.lastIndexOf("."));	
	}
}
