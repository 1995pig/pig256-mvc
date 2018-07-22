package cn.mldn.action;

import cn.mldn.util.action.ActionMessageUtil;
import cn.mldn.util.web.ModelAndView;

public class MemberAction {
	public ModelAndView login(String mid,String password){
		System.out.println("编写业务层调用，处理登录。。。");
		ModelAndView mav = new ModelAndView(ActionMessageUtil.getUrl("forward.page"));
		if("mldn".equals(mid) && "java".equals(password)){
			mav.add("msg",ActionMessageUtil.getMsg("login.success"));
			mav.add("url",ActionMessageUtil.getUrl("welcome.page"));
		}else{
			mav.add("msg",ActionMessageUtil.getMsg("login.failure"));
			mav.add("url",ActionMessageUtil.getUrl("login.page"));
		}
		return mav ;
		
	}
}
