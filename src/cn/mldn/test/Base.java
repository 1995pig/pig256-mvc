package cn.mldn.test;

import java.lang.reflect.Method;

import cn.mldn.action.DeptAction;
import cn.mldn.util.bean.MethodUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class Base {
	public static final String METHOD_NAME ="add";	//要操作的方法
	public static void main(String args[]) throws  Exception{
 		Class<?> cls = DeptAction.class ;//必须有class对象，有了class对象就可以找到Class	Loader
		Method method = MethodUtil.getMethod(cls, METHOD_NAME);
		Class<?> params[] = method.getParameterTypes() ;
		ClassPool classPool = ClassPool.getDefault();				//是javassist包定义的内容，用于进行要解析类的工具
		CtClass ctClass = classPool.get(cls.getName()) ;			//理解为要操作类的字节码文件
		CtMethod ctMethod = ctClass.getDeclaredMethod(METHOD_NAME);	//取得要操作的方法对象
		MethodInfo methodInfo = ctMethod.getMethodInfo();			//取得方法的信息
		//以上是取得了方法的相关操作信息项，但是随后才是关键性的问题，如果要想取得参数的名称
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute() ;	//取得代码的属性
		LocalVariableAttribute attribute =							//取得属性的标签(名称)
				(LocalVariableAttribute)codeAttribute.getAttribute(LocalVariableAttribute.tag);
		//必须考虑到方法上会带有static标志的问题，所有必须确定开始点
		int pos = Modifier.isStatic(ctMethod.getModifiers()) ?0:1;
   		for(int x=0;x<params.length;x++){
			System.out.println(attribute.variableName(x+pos));
 		}
		
		
		
		
		
		
		
 	 
	}
}




















