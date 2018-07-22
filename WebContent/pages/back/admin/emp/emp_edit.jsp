<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() 
		+ ":" + request.getServerPort() + request.getContextPath() + "/" ;
%>
<%!
	public static final String EMP_EDIT_URL ="pages/back/admin/emp/EmpAction!edit.action" ;	
%>
<base href="<%=basePath%>"/>
<html>
<head>
 <title>Insert title here</title>
</head>
<body>
	<h1>哈哈</h1>
	<h1>${allDepts}</h1>
	<form action="<%=EMP_EDIT_URL%>">
		操作人员信息:<input type="text" name="mid" value="mldnjava"/><br/>
		雇员编号(long):<input type="text" name="empno" value="10"/><br/>
		雇员姓名(String):<input type="text" name="name" value="SMITH"/><br/>
		雇员工资(double):<input type="text" name="sal" value="550000.78"/><br/>
		雇员年龄(int):<input type="text" name="age" value="230"/><br/>
		雇佣日期(Date):<input type="text" name="hiredate" value="2009-10-11"/><br/>
		<input type="submit" value="注册"> 		
	</form>
</body>
</html>