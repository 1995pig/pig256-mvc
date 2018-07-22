<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() 
		+ ":" + request.getServerPort() + request.getContextPath() + "/" ;
%>
<%!
	public static final String DEPT_ADD_URL ="pages/back/admin/dept/DeptAction!add.action" ;	
%>
<base href="<%=basePath%>"/>
<html>
<head>
 <title>Insert title here</title>
</head>
<body>	
	<h1>哈哈</h1>		 
	<h1>${allDepts}</h1>
	<form action="<%=DEPT_ADD_URL%>" method="post" >
		部门编号(long)  :<input type="text" name="deptno" value="10"/>${errors['deptno']}<br/>
		部门姓名(String):<input type="text" name="dname" value="开发部"/>${errors['dname'] }<br/>
		部门位置(String):<input type="text" name="loc" value="北京"/>${errors['loc'] }<br/> 
 		<input type="submit" value="注册"> 		
	</form>
</body>
</html>