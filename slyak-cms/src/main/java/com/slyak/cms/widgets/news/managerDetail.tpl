<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.js"></script>
<div class="btn-toolbar pull-right clearfix">
	<button class="btn btn-small" disabled='disabled' id="widget${widget.id}-edit">新增</button>
	<button class="btn btn-small" disabled='disabled' id="widget${widget.id}-remove">修改</button>
	<button class="btn btn-small" disabled='disabled' id="widget${widget.id}-top">删除</button>
</div>
<table class="table table-bordered table-striped table-hover" id="widget${widget.id}-table">
	<tr><th><input type="checkbox" class="no-margin"></th><th>标题</th><th>创建时间</th><th>作者</th></tr>
	<#if page??&&page.content??>
		<#list page.content as c>
			<tr><td><input type="checkbox" class="no-margin" newsId="${c.id}"></td><td>${c.title}</td><td>${c.createAt}</td><td><#if c.creator??>${c.creator.name!!}</#if></td></tr>			
		</#list>
		<#else>
		<tr><td colspan="4">暂无记录</td></tr>
	</#if>
</table>
<#include "pagination.tpl">
<form action="${action}/news/addNews" method="post">
	<fieldset>
		<legend>编辑内容</legend>
		<input type="hidden" name="owner" value="${newsType!!}">
		<input type="hidden" name="biz" value="news">
		<label>标题</label>
		<input type="text" name="title" placeholder="必填">
		<label>内容</label>
		<textarea name="content" style="width:100%;height:250px;"></textarea>
		<hr>
		<button type="submit" class="btn btn-primary">发布</button>
	</fieldset>
</form>
<script>
	var newsParam = {};
	newsParam.cssPath = '${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.css';
	newsParam.uploadJson = '${ctx}/file/textEditor/upload';
	newsParam.fileManagerJson = '${ctx}/file/textEditor';
	newsParam.widgetId = '${widget.id}';
	newsParam.deleteNews = '${action}/news/deleteNews';
	newsParam.removeType = '${action}/news/removeType';
	newsParam.topNews = '${action}/news/topNews';
	newsParam.editNews = '${action}/news/editNews';
	newsParam.newsType = '${newsType!!}';
</script>
<script charset="utf-8" src="${resource}/manager.js"></script>