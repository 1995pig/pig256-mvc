package cn.mldn.util.servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import cn.mldn.util.action.ActionMessageUtil;
import cn.mldn.util.web.ActionBeanUtil;
import cn.mldn.util.web.ParameterValidatorUtil;
import cn.mldn.util.web.RequestUrlUtil;
import cn.mldn.util.web.ServletObjectUtil;
//定义DispatcherServlet类，该类可以处理所有用户请求,只是负责Action类的调用处理
@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {//所有的请求交给服务方法创建请求处理线程
	 	String requestContentType = request.getContentType() ; // 取得当前的表单模式
  		if(requestContentType != null){
   	 	 	if(requestContentType.contains("multipart/form-data")) {	// 表示表单封装	 
  	 	 		//表单一旦被封装就意味着有可能进行文件的上传处理,就要准备好SmartUpload组件，
  	 	 		//但是这个组件最终肯定还是要交给各个Action去操作
  	 	 		SmartUpload smart = new SmartUpload();		//实例化SmartUpload对象
  	 	 		smart.initialize(super.getServletConfig(), request, response);	//程序需要将其封装在java类里面
  	 	 		try {
  					smart.upload();							//这个时候才表示能够接收所有的上传数据
  				} catch (SmartUploadException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				} 
  	 	 		ServletObjectUtil.setSmartUpload(smart);
  	 	 	} 
  		}
 		
  		ServletObjectUtil.setReponse(response);
		ServletObjectUtil.setRequest(request);
		String actionName = RequestUrlUtil.getActionName(request) ;
  		ParameterValidatorUtil pvu = new ParameterValidatorUtil(actionName);
		if(pvu.validate()){		//验证通过
	 		String urlResult[] = RequestUrlUtil.splitUrl(request);
	  		try {
				//DispatcherServlet程序类只是负责所有数据跳转操作
				String url = ActionBeanUtil.actionHandle(urlResult);
	 			ServletObjectUtil.clear(); 	//跳转之前清空保存的request、response
	 			if(url != null){
					request.getRequestDispatcher(url).forward(request, response);
				}
			} catch (Exception e) {
	 			e.printStackTrace();
			} 
		}else{		//验证不通过则应该跳转到一个错误页上
 			request.getRequestDispatcher(ActionMessageUtil.getUrl(actionName + ".error.page")).forward(request,response);
		}
	} 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		doGet(request, response);
	} 
	

}
