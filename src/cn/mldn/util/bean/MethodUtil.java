package cn.mldn.util.bean;

import java.lang.reflect.Method;

public class MethodUtil {
	private MethodUtil(){}
	/**
	 * 取得指定类中指定方法的对象，在取得时候并不知道该方法中的参数类型
	 * @param cls 操纵的class类对象
	 * @param methodName 方法名称
	 * @return 如果取得有方法则以Method对象返回，如果没有该方法返回的就是null
	 */
	public static Method getMethod(Class<?> cls,String methodName){
		Method methodResult[] = cls.getMethods();
		for(int x=0;x<methodResult.length;x++){
			if(methodName.equals(methodResult[x].getName())){
				return methodResult[x];
			}
		}
		return null;
	}
}
