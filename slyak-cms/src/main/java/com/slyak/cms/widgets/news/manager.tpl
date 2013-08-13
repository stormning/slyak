<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.js"></script>

<link rel="stylesheet" href="${resource}/manager.css" />
<link rel="stylesheet" href="${ctx}/static/thirdparty/zTree/css/zTreeStyle/zTreeStyle.css" />
<script charset="utf-8" src="${ctx}/static/thirdparty/zTree/js/jquery.ztree.core-3.5.min.js"></script>

<style>
	#widget${widget.id}-table td{
		vertical-align:middle;
	}
	
	#widget${widget.id}-table input{
		margin:0px;
	}
</style>

<div class="row-fluid">
	<div class="span3">
		<div class="nav">
			<a id="saveTypeBtn" class="btn" data-toggle='modal' data-target='#saveType' href="${action}/news/saveTypeView">新增</a>
			<a id="editTypeBtn" class="btn" disabled='disabled' data-toggle='modal' data-target='#editType' href="${action}/news/saveTypeView?id=${newsType!!}">修改</a>
			<a id="removeTypeBtn" class="btn" disabled='disabled' href="${action}/news/removeType?id=${newsType!!}">删除</a>
		</div>
		<div id="tree" class="ztree"></div>
	</div>
	<div class="span9">
		<div class="row-fluid">
			<div class="span6">
				<form class="form-search" method="get" action="${view}">
					<div class="input-append">
				 		<input type="hidden" name="newsType" value="${newsType}">
				   	 	<input type="text" class="search-query" name="keyword" value="${keyword}">
				    	<button type="submit" class="btn">搜索</button>
				  	 </div>
				</form>
			</div>
			<div class="pull-right">
				<button autocomplete="off" class="btn" id="widget${widget.id}-add">新增</button>
				<#if page.content?size gt 0>
					<button autocomplete="off" class="btn" disabled='disabled' id="widget${widget.id}-edit" rel="${action}/news/getNews">修改</button>
					<button autocomplete="off" class="btn" disabled='disabled' id="widget${widget.id}-remove" rel="${action}/news/deleteNews">删除</button>
				</#if>
			</div>
		</div>
		<table class="table table-bordered table-striped table-hover" id="widget${widget.id}-table">
			<tr><th><input type="checkbox" class="no-margin"></th><th>标题</th><th>分类</th><th>创建时间</th><th>作者</th></tr>
			<#if page??&&page.content??>
				<#list page.content as c>
					<tr class="form-horizontal" newsId="${c.id}"><td><input autocomplete="off" type="checkbox" class="no-margin" newsId="${c.id}"></td><td>${c.title}</td><td>
						<#if leafGroups??>
							<select name="owner" autocomplete="off">
								<option value="0">未分类</option>
								<#list leafGroups as g>
									<option value="${g.id}" <#if g.id == c.owner?number>selected="selected"</#if>>${g.name}</option>
								</#list>
							</select>
						</#if>
					</td><td>${c.createAt}</td><td><#if c.creator??>${c.creator.name!!}</#if></td></tr>			
				</#list>
				<#else>
				<tr><td colspan="4">暂无记录</td></tr>
			</#if>
		</table>
		<#include "pagination.tpl">
		<#if leafGroups??>
			<form action="${action}/news/addNews" method="post" id="newsManagerForm">
				<fieldset>
					<legend>编辑内容</legend>
					<input type="hidden" name="id" value="">
					<input type="hidden" name="biz" value="news">
					<label>标题</label>
					<input type="text" name="title" placeholder="必填">
					<#if leafGroups?size gt 1>
						<label>分类</label>
						<select name="owner" autocomplete="off">
							<#list leafGroups as g>
							<option value="${g.id}">${g.name}</option>
							</#list>
						</select>					
						<#else>
						<input type="hidden" name="owner" value="${leafGroups[0].id}">
					</#if>
					<label>内容</label>
					<textarea name="content" style="width:100%;height:250px;"></textarea>
					<hr>
					<button type="submit" class="btn btn-primary">发布</button>
				</fieldset>
			</form>
		</#if>
	</div>	
</div>
<script>
	var newsParam = {};
	newsParam.cssPath = '${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.css';
	newsParam.uploadJson = '${ctx}/file/textEditor/upload';
	newsParam.fileManagerJson = '${ctx}/file/textEditor';
	newsParam.widgetId = '${widget.id}';
	newsParam.newsType = '${newsType!!}';
	newsParam.deleteNews = '${action}/news/deleteNews';
	newsParam.removeType = '${action}/news/removeType';
	newsParam.topNews = '${action}/news/topNews';
	newsParam.editNews = '${action}/news/editNews';
	newsParam.allGroups = ${allGroups?default('[]')};
	newsParam.loadChildTypeUrl = '${action}/news/loadChildren';
	newsParam.reloadUrl = '${view}?newsType=';
	newsParam.changeTypeUrl = '${action}/news/changeType';
</script>
<script charset="utf-8" src="${resource}/manager.js"></script>