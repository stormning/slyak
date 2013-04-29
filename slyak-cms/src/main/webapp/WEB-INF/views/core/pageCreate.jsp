<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>	
<form action="${ctx}/page/create" method="post" class="form-horizontal">
	<input type="hidden" name="pageId" value="${param.pageId}">
	<div class="control-group">
		<label for="pageName" class="control-label">名称</label>
		<div class="controls">
			<input type="text" id="pageName" name="name" class="input-xlarge">
		</div>
	</div>
	<div class="control-group">
		<label for="pageTitle" class="control-label">标题</label>
		<div class="controls">
			<input type="text" id="pageTitle" name="title" class="input-xlarge">
		</div>
	</div>
	<div class="control-group">
		<label for="pageAlias" class="control-label">URL别名</label>
		<div class="controls">
			<input type="text" id="pageAlias" name="alias" class="input-xlarge">
			<p class="help-block">若不设置用页面ID作为别名（唯一）</p>
		</div>
	</div>
	<c:if test="${not empty param.pageId}">
		<div class="control-group">
			<label for="copyThis" class="control-label">拷贝本页</label>
			<div class="controls">
				<input type="checkbox" id="copyThis" name="copy" class="input-xlarge">
			</div>
		</div>
	</c:if>
	<c:if test="${not empty param.pageId&& empty currentPage.parent}">
		<div class="control-group">
			<label for="isChildren" class="control-label">作为子页</label>
			<div class="controls">
				<input type="checkbox" id="isChildren" name="isChildren" class="input-xlarge">
			</div>
		</div>
	</c:if>
</form>