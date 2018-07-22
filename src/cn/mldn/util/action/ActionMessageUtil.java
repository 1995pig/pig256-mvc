package cn.mldn.util.action;

import cn.mldn.util.MessageUtil;

public class ActionMessageUtil {
	private static final MessageUtil PAGES_MESSAGE = new MessageUtil("cn.mldn.util.message.pages");
	private static final MessageUtil MESSAGES_MESSAGE = new MessageUtil("cn.mldn.util.message.messages");
	public static String getUrl(String key){
		return PAGES_MESSAGE.getText(key) ;
	}
	public static String getMsg(String key){
		return MESSAGES_MESSAGE.getText(key);
	}
}
