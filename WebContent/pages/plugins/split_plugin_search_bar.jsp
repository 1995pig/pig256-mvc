<%@page pageEncoding="UTF-8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName()
		+ ":" + request.getServerPort() + request.getContextPath()  ;
%> 
 <%--  
<jsp:include page="/pages/plugins/split_plugin_search_bar.jsp"/>
 --%>

<%
	request.setCharacterEncoding("UTF-8");
	String url = basePath + request.getAttribute("url");
	String column = (String)request.getAttribute("column");
	String keyWord = (String)request.getAttribute("keyWord");
	String columnData = (String)request.getAttribute("columnData");
 	int allRecorders = 0;
	
	if ("null".equals(column) || "".equals(column) || column == null) {
		column = "";
	}
	if("null".equals(keyWord) || "".equals(keyWord) || keyWord== null){
		keyWord ="";
	}  
%>
<%
	try{
		allRecorders = (Integer)request.getAttribute("allRecorders");
	}catch(Exception e){}
%> 
<div id="searchDIV">
<form action="<%=url%>" method="post">
	<select id="col" name="col" >
	<%
		if(!(columnData==null || "".equals(columnData))){
	 	//"部门编号:deptno|雇员姓名:ename|雇员职位:job";
			String str[] = columnData.split("\\|");
			for(int x=0;x<str.length;x++){
				String temp[]= str[x].split(":");
	%>		
 			<option  value="<%=temp[1]%>"  <%=temp[1].equals(column)? "selected" : "" %>><%=temp[0] %></option>
	<% 		
		}
			}
	%>
	</select>
	<input type="text" name = "kw" value="<%=keyWord%>"/>
	<input type="submit"  value="查询"/> 
 	<p>共查询到<%=allRecorders%>行记录</p>
</form>
</div>

 







