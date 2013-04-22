<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>	


<c:forEach items="${layoutNames}" var="layout" varStatus="status">
	<c:if test="${status.index%4==0}">
		<div class="row-fluid">
	</c:if>
	<div class="span3">
		<div class="layout-demo" layout="${layout}.tpl">
			<c:forEach items="${fn:split(layout,'_')}" var="row" varStatus="st">
				<c:set var="rowClass" value="row-1-${fn:length(fn:split(layout,'_'))}"/>
				<c:set var="counter" scope="page" value="0"/>
				<c:forEach items="${fn:split(row,'-')}" var="col">
					<c:set var="counter" scope="page" value="${counter+col}"/>
				</c:forEach>
				<c:set var="unit" scope="page">
					<fmt:formatNumber value="${12/counter}"/>
				</c:set>
				<div class="row-fluid">
					<c:forEach items="${fn:split(row,'-')}" var="col">
						<div class="span${col*unit} ${rowClass}<c:if test="${st.last}"> last</c:if>"></div>
					</c:forEach>
				</div>		
			</c:forEach>	
		</div>
	</div>
	<c:if test="${status.last||(status.index+1)%4==0}">
		</div>
	</c:if>
</c:forEach>
