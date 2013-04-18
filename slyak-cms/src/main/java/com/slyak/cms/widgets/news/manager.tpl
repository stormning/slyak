<div class="row-fluid">
	<ul class="nav nav-pills">
	  <#if newsTypes??>
		<#list newsTypes as t>
			<li <#if t.id==newsType.id>class="active"</#if>><a href="${view}/news/manager?newsType=${t.type}">${t.decription}</a></li>		
		</#list>
	  </#if>
    </ul>
	<table class="table table-bordered table-striped table-hover">
		<thead>
			<tr><th><input type="checkbox" class="no-margin"></th><th>标题</th><th>创建时间</th><th>操作</th></tr>
		</thead>
		<tbody>
			<#if page??&&page.content??>
				<#list page.content as c>
					<tr><td><input type="checkbox" class="no-margin"></td><td>${c.title}</td><td>${c.createAt}</td><td></td></tr>			
				</#list>
			</#if>
		</tbody>
	</table>
	<#include "pagination.tpl">
	<form action="${action}/news/addNews" method="post">
		<fieldset>
			<legend>发布内容</legend>
			<input type="hidden" name="commentType.id" value="${newsType.id}"/>
			<label>标题</label>
			<input type="text" name="title" placeholder="必填"/>
			<label>内容</label>
			<textarea name="content" style="width:100%;height:250px;"></textarea>
			<button type="submit" class="btn">发布</button>
		</fieldset>
	</form>
</div>
<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.js"></script>
<script>
	$(function() {
		var editor = KindEditor.create('textarea[name="content"]',{
			items : ['bold', 'italic', 'underline', 'strikethrough', 'removeformat','|','insertorderedlist', 'insertunorderedlist', 
				 'forecolor', 'hilitecolor', 'fontname', 'fontsize',  '|', 'link', 'unlink', 'emoticons', 
				 'shcode', 'image', 'flash', 'quote', '|','code','source','about'],
			cssPath : '${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.css',
			uploadJson : '${ctx}/file/textEditor/upload',
			filePostName: 'file',
			fileManagerJson : '${ctx}/file/textEditor',
			allowFileManager : true,
			afterCreate : function() {
				var self = this;
				KindEditor.ctrl(document, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
				KindEditor.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['example'].submit();
				});
			}	 
		});
		prettyPrint();
	});
</script>