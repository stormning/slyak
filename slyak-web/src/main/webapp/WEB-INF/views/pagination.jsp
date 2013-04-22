<%@page import="org.springframework.data.domain.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>

<%
	Page<?> p = (Page<?>) request.getAttribute("page");
	if (p != null) {
		int totalPages = p.getTotalPages();
		if(totalPages>1){
		
		int currentPage = p.getNumber()+1;
		int size = p.getSize();
		//7 buttons
		int maxButton = 7;
		int half = maxButton/2;
		
		
		int startButton = currentPage-half <0 ?1:currentPage-half;
		int endButton = Math.max(currentPage-half, totalPages);
		%>
		<div class="pagination pagination-centered">
			<ul>
				<% for (int i = startButton;i<=endButton;i++) {
					if(startButton>1 && i==startButton-1){
				    %>
					<li><a href="?page.page=">Prev</a></li>
				    <%}%>
					<li <%if(i==currentPage){%>class="active"<%} %>>
						<a <%if(i!=currentPage){%>href="?page.page=<%=i%>"<%}%>><%=i%></a>
					</li>
			        <%if(endButton<totalPages && i==endButton-1){%>
					<li><a href="?page.page=">Next</a></li>
				    <%}
				}%>	
			</ul>
		</div>
		<%
	}
	}
%>