<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<c:choose>
	<c:when test="${formType=='logTypes'}">
		<c:set var="formAction" value="${ctx}/account/type/save" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="formAction" value="${ctx}/account/member/save" scope="request"/>
	</c:otherwise>
</c:choose>
<form class="form-horizontal logform" action="${formAction}" method="post">
	<div class="control-group" style="border-top: 0px">
		<label class="control-label" for="inputEmail">名称</label>
		<div class="controls">
			<input type="text" class="description" name="name">
			<input type="hidden" name="id">
			<c:choose>
				<c:when test="${formType=='logTypes'}">
					<input type="hidden" name="parent.id">
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form>