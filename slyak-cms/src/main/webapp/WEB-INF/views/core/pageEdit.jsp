<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="${ctx}/page/edit" method="post" class="form-horizontal">
	<input type="hidden" name="id" class="input-xlarge" value="${param.id}">
	<div class="control-group">
		<label for="pageName" class="control-label">名称</label>
		<div class="controls">
			<input type="text" id="pageName" class="input-xlarge" name="name" value="${currentPage.name}">
		</div>
	</div>
	<div class="control-group">
		<label for="pageTitle" class="control-label">标题</label>
		<div class="controls">
			<input type="text" id="pageTitle" class="input-xlarge" name="title" value="${currentPage.title}">
		</div>
	</div>
	<div class="control-group">
		<label for="pageAlias" class="control-label">URL别名</label>
		<div class="controls">
			<input type="text" id="pageAlias" class="input-xlarge" name="alias" value="${currentPage.alias}">
			<p class="help-block">若未设置则用页面ID作为别名（唯一）</p>
		</div>
	</div>
	
	<div class="control-group">
		<label for="pageAlias" class="control-label">自定义样式</label>
		<div class="controls">
			<textarea name="customCss">
				${currentPage.customCss}
			</textarea>
		</div>
	</div>
</form>