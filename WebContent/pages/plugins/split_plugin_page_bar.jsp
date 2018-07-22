<%@page pageEncoding="UTF-8" %>
 
<%
	String basePath = request.getScheme() + "://" + request.getServerName()
		+ ":" + request.getServerPort() + request.getContextPath()  ;
%> 
  <%-- 
		<jsp:include page="/pages/plugins/split_plugin_page_bar.jsp"/> 
 --%>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
<%
	request.setCharacterEncoding("UTF-8");
	String url = basePath + request.getAttribute("url");
	int currentPage = 1;
	int lineSize = 1;	
	int allRecorders = 0;
	int pageSize = 1;
	String keyWord = (String)request.getAttribute("keyWord");
	String column = (String)request.getAttribute("column");

%>
<%
	try{
		currentPage = (Integer)request.getAttribute("currentPage");
	}catch(Exception e){}
	try{
		lineSize = (Integer)request.getAttribute("lineSize");
	}catch(Exception e){}
	try{											   
		allRecorders = (Integer)request.getAttribute("allRecorders");
	}catch(Exception e){}
	if(keyWord==""||keyWord=="null"||"".equals(keyWord)){
		keyWord = "";
	}
	if(column==""||column=="null"||"".equals(column)){
		column = "";
	}
%>
<%
	int seed = 2 ;	//定义一颗种子数，判断是否有...
	if (allRecorders > 0) {
		pageSize = (allRecorders + lineSize - 1) / lineSize ;
	}
%>
<div id="pageCtr">
<ul class="pagination">
	<li class="<%=currentPage==1?"active":""%>">
		<%
			if(currentPage==1){
		%>
			<span>1</span>
		<%
			}else{
		%>
			<a href="<%=url%>?cp=1&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>"><span>1</span></a>
		<%
			}
		%>
	</li> 
	<%
		if(pageSize > seed * 2){		//数据量很大时，需要省略号的出现
			if(currentPage <= seed * 2){	
				int startPage = 2;		//开始的页面
				int endPage = currentPage + seed * 2;
				if(endPage >= pageSize){
					endPage = pageSize - 1 ;
				}
				for(int x =startPage;x<=endPage;x++){
 	%>			
			<li class="<%=currentPage==x?"active":""%>">
 				<a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>"><span><%=x%></span></a>
			</li>
	<%
				}
				if(endPage < pageSize-1){
	%>				
			<li> <span>...</span></li>
	<% 				
				}
			}else{
	%>	
			<li><span>...</span></li>
	<%	 
				int startPage = currentPage - seed;
 				int endPage = currentPage + seed ;
  				if(endPage >= pageSize){
					endPage = pageSize -1 ;
				}
				for(int x = startPage ; x <= endPage;x++){
	%>			
				<li class="<%=currentPage==x?"active":""%>">
 					<a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>"><span><%=x%></span></a>
				</li>			
	<% 				
				}
				if ((currentPage + seed * 2) <= pageSize) {	// 后面还有很多页
 	%>
				<li class="disabled"><span>...</span></li>
		<%  			
				} 			
			}
		}else{				//当查询数量少于等于四个的时候 
			for(int x = 2; x < pageSize;x++){
	%>
			<li class="<%=currentPage==x?"active":""%>"> 
			<a href="<%=url%>?cp=<%=x%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>"><%=x %></a>
			</li>
	<%	
			}
		}
	%>
	<%
		if(pageSize !=1){
	%>
		<li class="<%=currentPage == pageSize?"active":""%>">
		<% 
			if(currentPage == pageSize){
				%>	
			<span><%=pageSize %></span>
		<% 
			}else{
		%>
			<a href="<%=url%>?cp=<%=pageSize%>&ls=<%=lineSize%>&kw=<%=keyWord%>&col=<%=column%>"><%=pageSize%></a>
		<%
			}
		%>	
		</li>
	<% 		
		}
	%>	
</ul>

</div>



