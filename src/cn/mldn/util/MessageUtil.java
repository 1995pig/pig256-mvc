package cn.mldn.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageUtil {
	private ResourceBundle resource ;
	public MessageUtil(String baseName){
		this.resource = ResourceBundle.getBundle(baseName);
	}
	public String getText(String key){
		try{
	 		return this.resource.getString(key);
		}catch(Exception e){
			return null; 
		}
	}	
	public String getText(String key,Object... param){
		try{
			return MessageFormat.format(this.resource.getString(key), param);
		}catch(Exception e){
			return null;
		}
	}
}






