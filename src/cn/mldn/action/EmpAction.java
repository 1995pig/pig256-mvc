package cn.mldn.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.mldn.util.action.ActionUploadUtil;
import cn.mldn.util.web.ModelAndView;
import cn.mldn.vo.Dept;
import cn.mldn.vo.Emp;

public class EmpAction {
	public String add(long empno,String name,double sal,int age,Date hiredate) throws Exception{
		ActionUploadUtil actionUpload = new ActionUploadUtil("upload/emp");		//该路径要首先存在
		System.out.println(actionUpload.createSingleFileName());
		actionUpload.saveSingleFile(); 				//文件保存
 		System.out.println(empno + "、" + name + "、" + sal + "、" + age +"、" + hiredate +"、");
		return null;
 		//return "/pages/back/admin/emp/EmpAction!addPre.action";
 	}
	public ModelAndView addPre(){		//是一个无参的操作
		ModelAndView mav = new ModelAndView("/pages/back/admin/emp/emp_add.jsp");
		List<Dept> all = new ArrayList<Dept>();
		for(int x=0;x<10;x++){
			Dept vo = new Dept();
			vo.setDeptno(x);
			vo.setDname("部门名称" + x);
			vo.setLoc("部门位置" + x);
			all.add(vo);
		}
		mav.add("allDepts",all);
 		return mav;
	}
	public String edit(String mid,Emp vo){
 		System.out.println("## mid = " + mid);
		System.out.println("## vo = " + vo);
		return null;
	}
	public String editPre(){		//是一个无参的操作
 		//中间可能是你的业务的操作方法调用
 		return "/pages/back/admin/emp/emp_edit.jsp";
	}
	public String delete1(String ids[]){
		System.out.println("## delete1 = " + Arrays.toString(ids));
		return null;
	}
	public String delete2(int ids[]){
		System.out.println("## delete2 = " + Arrays.toString(ids));
		return null;
	}
	 
}
	