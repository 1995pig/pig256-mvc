package cn.mldn.util.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;

import cn.mldn.util.StringUtil;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class ParameterValueUtil {
	private ParameterValueUtil(){}
	
	/**
	 * 取得指定的参数，不管表单是否封装了，那么都可以取得参数，除非没有传递参数
	 * @param param
	 * @return
	 */
	public static String getParameter(String param){
		if(ServletObjectUtil.getRequest().getContentType() != null){
			if(ServletObjectUtil.getRequest().getContentType().contains("multipart/form-data")){
				return ServletObjectUtil.getSmartUpload().getRequest().getParameter(param);
			}
		}
		return ServletObjectUtil.getRequest().getParameter(param) ;
	}
	/**
	 * 请求取得一组的参数
	 * @param param
	 * @return
	 */
	public static String[] getParameterValues(String param){		 
		if(ServletObjectUtil.getRequest().getContentType() != null){
			if(ServletObjectUtil.getRequest().getContentType().contains("multipart/form-data")){
				return ServletObjectUtil.getSmartUpload().getRequest().getParameterValues(param) ;
			}
		}	
		return ServletObjectUtil.getRequest().getParameterValues(param) ;
	}
	
	/**
	 * 判断方法上的参数是否是一个数组类型
	 * @param paramType 操作类型
	 * @return
	 */
	public static boolean isArray(String paramType){
		return "int[]".equals(paramType) || "String[]".equals(paramType);
	}
	/**
	 * 判断当前操作的属性的类型是否是简单类型还是vo类型
	 * @param paramType 操作类型
	 * @return 如果是基础类型返回true,否则返回false
	 */
	public static boolean isBasic(String paramType){
		return "int".equals(paramType) 
				||"java.lang.Integer".equals(paramType)
				||"double".equals(paramType)
				||"java.lang.Double".equals(paramType)
				||"java.util.Date".equals(paramType)
				||"java.lang.String".equals(paramType)
				||"long".equals(paramType)
				||"java.lang.Long".equals(paramType);
	}
	/**
	 * 进行指定参数内容的取得
	 * @param paramName 参数名称
	 * @param paramType 参数类型
	 * @return 返回各自参数的内容并且根据指定的参数类型进行转型
	 * @throws Exception
	 */
	public static Object getBasicParameterValue(String paramName,String paramType) throws Exception{
		String val =  getParameter(paramName);
		if("int".equals(paramType) || "java.lang.Integer".equals(paramType)){
			if(val == null || "".equals(val)){
				return 0 ;
			}else{
 				return Integer.parseInt(val);
			}
		}else if("double".equals(paramType) || "java.lang.Double".equals(paramType)){
			if(val == null || "".equals(val)){
				return 0.0;
			}else{
 				return Double.parseDouble(val);
			}
		}else if("long".equals(paramType)  || "java.lang.Long".equals(paramType)){
 			if(val == null || "".equals(val)){
 				return 0 ;
			}else{
 				return Long.parseLong(val) ;
			}
		}else if("java.util.Date".equals(paramType)){
			if(val == null || "".equals(val)){
				return null;
			}else{
				String pattern = "yyyy-MM-dd";
				if(val.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")){
					pattern = "yyyy-MM-dd hh:mm:ss";
				}
  				return new SimpleDateFormat(pattern).parse(val);
			}
		} 
		return val ;	 
	}  
	
	
	/**
	 * 进行数组的操作处理，可以处理组合的字符串以及复选框提交
	 * @param paramName
	 * @param paramType
	 * @return
	 */
	public static Object getArrayParameterValue(String paramName,String paramType){
		String val =  getParameter(paramName) ;	//取得参数内容
		if(val.contains(",")){		//如果有“,”，需要拆分处理，只接收一次数据即可
			String result[] = val.split(",") ;
			if("int[]".equals(paramType) || "java.lang.Integer[]".equals(paramType)){
				int temp[] = new int[result.length];
				for(int x=0;x<temp.length;x++){
					temp[x] = Integer.parseInt( result[x]);
				}
				return temp;
			}
			return result;	
		}else{			//现在提交的是一个复选框  ids=1&ids=2&ids=3&ids=4
			String result[] =  getParameterValues(paramName);
			if("int[]".equals(paramType) || "java.lang.Integer".equals(paramType)){
				int temp[] = new int[result.length];
				for(int x=0;x<temp.length;x++){
					temp[x] = Integer.parseInt(result[x]) ;
				}
				return temp;
			}
			return result ;
		}
		
	}
	/**
	 * 根据传入的参数进行对象的反射实例化处理
	 * @param voClass 参数的类型，依靠此类型进行反射对象实例化
	 * @return 一个实例化好的VO类对象
	 * @throws Exception
	 */
	public static Object getParameterValue(Class<?> voClass) throws Exception{
		Object voObject = voClass.newInstance() ;			//反射实例化VO类对象
		Field fields[] = voClass.getDeclaredFields();		//取得指定类中全部操作属性名称
		for(int x=0;x<fields.length;x++){
 			Method method = voClass.getMethod("set"+StringUtil.initCap(fields[x].getName()),
							fields[x].getType());
 			method.invoke(voObject, getBasicParameterValue(fields[x].getName(),fields[x].getType().getName())) ;
 		}
		return voObject;
 	} 
	public static Object[] getMethodParameter(Class<?> actionClass,Method actionMethod) throws Exception{
 		Class<?> params [] = actionMethod.getParameterTypes() ; // 取得指定方法的参数信息
		ClassPool classPool = ClassPool.getDefault(); // 是javassist包定义的内容，用于进行要解析类的工具定义 
		// Javassist开发包如果要进行类的加载操作，那么需要通过Classpath完成，但是如果要是在tomcat中运行的话，
		// 这个ClassPath会被Tomcat干掉，那么现在就必须自己来手工设置CLASSPATH 
		ClassPath classPath = new ClassClassPath(actionClass) ;// 将指定类型的Class类对象（ClassLoader）放到ClassPath类中 
		classPool.insertClassPath(classPath) ; // 明确的告诉javassist开发要通过那个Classpath加载程序类 
		CtClass ctClass = classPool.get(actionClass.getName()) ; 
		CtMethod ctMethod = ctClass.getDeclaredMethod(actionMethod.getName()) ; // 取得要操作的方法对象
		MethodInfo methodInfo = ctMethod.getMethodInfo() ; // 取得方法的信息 	
		// 以上是取得了方法的相关操作信息项，但是随后才是关键性的问题，如果要想取得参数的名称 
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute() ; // 获取代码的属性
		LocalVariableAttribute attribute = (LocalVariableAttribute) 
				codeAttribute.getAttribute(LocalVariableAttribute.tag) ; // 取得属性的标签（名称）
		Object dataObj [] = new Object[params.length] ;// 返回的数组的内容 
		// 必须要考虑到方法上会带有static标志的问题，所以必须确定开始点
		int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1 ;
 		for (int x = 0 ; x < params.length ; x ++) { 
			String paramName = attribute.variableName(x + pos) ; // 取得参数名称 
			String paramType = params[x].getName() ; // 取得参数类型 
			System.out.println(paramName + "、" + params[x].getSimpleName());
			if(isBasic(paramType)){		//参数为基础操作类型
  				dataObj[x] = getBasicParameterValue(paramName,paramType) ; // 保存数据到数组之中  
			}else if(isArray(params[x].getSimpleName())){		 //判断是否为数组类型
 				dataObj[x] = getArrayParameterValue(paramName,paramType);
			}
			else{
  				dataObj[x] = getParameterValue(params[x]);	//此时这个参数就是一个Class类对象
			}
 		}
  		return dataObj ; 
 			
	} 
}
