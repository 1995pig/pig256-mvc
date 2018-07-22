package cn.mldn.util.web;

import java.util.HashMap;
import java.util.Map;

import cn.mldn.util.MessageUtil;
import cn.mldn.util.action.ActionMessageUtil;

public class ParameterValidatorUtil {
	private static final MessageUtil VALIDATOR_MESSAGE= 
			new MessageUtil("cn.mldn.util.message.validator") ;
	private String validateRule = null ;			//保存验证规则
	//保存所有的验证信息，应该将此信息传递会jsp页面显示
	private Map<String,String> errors = new HashMap<String,String>() ;
	public ParameterValidatorUtil(String actionName){	//在构造方法里面读取出验证规则信息
		this.validateRule = VALIDATOR_MESSAGE.getText(actionName) ;
	}
	/**
	 * 该方法的主要功能是进行数据的验证使用，如果验证处理成功，则执行对应的action，否则返回到一个指定的路径
	 * @return
	 */	
	public  boolean validate(){
		if(this.validateRule == null || "".equals(this.validateRule)){	//没有验证规则直接表示验证成功，返回true
			return true ;
		}
		String result[] = this.validateRule.split("\\|") ;
		for(int x=0;x<result.length;x++){
			String temp[] = result[x].split(":") ;
			String paramName = temp[0];				//参数名称
			String validateRule = temp[1] ;			//规则名称
			String val = ParameterValueUtil.getParameter(paramName) ;
			switch(validateRule){
				case "int":{
					if(!this.validateInt(val)){
						this .errors.put(paramName, ActionMessageUtil.getMsg("validator.int.msg")) ;
					}
					break ;
				} 
				case "string":{
					if(!this.validateString(val)){
						this.errors.put(paramName, ActionMessageUtil.getMsg("validator.string.msg")) ;
					}
					break;
				}
				case "double":{
					if(!this.validateDouble(val)){
						this.errors.put(paramName, ActionMessageUtil.getMsg("validator.double.msg")) ;
					}
					break;
				}
				case "date":{
					if(!this.validateDate(val)){
						this.errors.put(paramName, ActionMessageUtil.getMsg("validator.date.msg")) ;
					}
					break;
				}
				case "datetime":{
					if(!this.validateDateTime(val)){
						this.errors.put(paramName, ActionMessageUtil.getMsg("validator.datetime.msg")) ;
					}
					break;
				}
				case "long":{
					if(!this.validateLong(val)){
						this.errors.put(paramName, ActionMessageUtil.getMsg("validator.long.msg"));
					}
					break; 
				}
			} 
		}
		if(errors.size() > 0){		//所有的错误信息应该保存到request属性范围中
			ServletObjectUtil.getRequest().setAttribute("errors", errors);
		}
		return this.errors.size() == 0 ;
	}

	
	
	
	public boolean validateDate(String str){
		if(this.validateString(str)){
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false ;
	}
	public boolean validateDateTime(String str){
		if(this.validateString(str)){
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false ;
	}
	public boolean validateDouble(String str){
		if(this.validateString(str)){
			return str.matches("\\d+(\\.\\d{1,2})?");
		} 	
		return false ;
	}
	public boolean validateLong(String str){
		if(this.validateString(str)){			//有数据
			return str.matches("\\d+");
		}
		return false ;
	}
	
	public boolean validateInt(String str){
		if(this.validateString(str)){			//有数据
			return str.matches("\\d+") ;
		}
		return false ;
	}
	/**
	 * 验证传入的字符串是否为null
	 * @param str
	 * @return 如果为空则表示失败，返回false，如果不为空表示成功
	 */
	public boolean validateString(String str){
		if(str == null || "".equals(str)){
			return false ;
		}
		return true ;
	}
}	
