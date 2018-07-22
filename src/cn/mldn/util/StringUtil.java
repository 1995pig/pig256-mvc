package cn.mldn.util;

public class StringUtil {
	public static String initCap(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
}
