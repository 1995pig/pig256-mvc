package cn.mldn.action;

import java.util.ArrayList;
import java.util.List;

import cn.mldn.util.action.ActionMessageUtil;
import cn.mldn.util.action.ActionSplitPageUtil;
import cn.mldn.util.web.ModelAndView;
import cn.mldn.util.web.ParameterValueUtil;
import cn.mldn.vo.Dept;

public class DeptAction {
	public  String add(String attr,int age,Dept vo){
		return "Hello";
	} 
	public String edit(Dept vo){
 		System.out.println(vo  + "-----------------");
 		return null;
	}
	public ModelAndView list(){
		//实例化分页的处理程序类，就可以帮助用户实现分页的处理以及所有分页相关属性的传递了
		ActionSplitPageUtil aspu = new ActionSplitPageUtil("部门名称:dname|部门位置:loc|","dept.list.action");
		ModelAndView mav = new ModelAndView(ActionMessageUtil.getUrl("dept.list.page"));
 		List<Dept> all = new ArrayList<Dept>();
 		for(int x=(aspu.getCurrentPage()-1) * aspu.getLineSize() ; 
 				x<aspu.getCurrentPage() * aspu.getLineSize();x++){
			Dept dept= new Dept() ;
			dept.setDeptno(x);
			dept.setDname(aspu.getColumn() + " - " + aspu.getKeyWord() + " - 部门名称 - " + x);
			dept.setLoc(aspu.getColumn() + " - " + aspu.getKeyWord() + " - 部门位置 - " + x);
			all.add(dept) ;
		}
		mav.add("allDepts",all);
		mav.add("allRecorders",150);		//假设总记录是150行
 		return mav;	
	}
}
