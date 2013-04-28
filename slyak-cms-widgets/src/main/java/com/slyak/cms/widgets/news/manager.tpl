<div class="row-fluid">
	<div class="span3">
		<ul class="nav nav-list bs-docs-sidenav">
		  <#if newsTypes??>
			<#list newsTypes as t>
				<li <#if t['id']==newsType>class="active"</#if>><a href="${view}/news/manager?newsType=${t['id']}">${t['name']}</a></li>		
			</#list>
		  </#if>
	    </ul>
    </div>
    
    <div class="span9">
    	<div class="row-fluid">
	    	<div class="span8 pull-right">
	    		<div class="btn-group">
	    			<button class="btn">修改</button>
	    		</div>
	    		<div class="btn-group">
	    			<button class="btn">删除</button>
	    		</div>
	    		<div>	
	    			<button class="btn">置顶</button>
	    		</div>	
			</div>
		</div>
    	
    	<div class="row-fluid">
			<table class="table table-bordered table-striped table-hover">
				<tr><th><input type="checkbox" class="no-margin"></th><th>标题</th><th>创建时间</th><th>作者</th></tr>
				<#if page??&&page.content??>
					<#list page.content as c>
						<tr><td><input type="checkbox" class="no-margin"></td><td>${c.title}</td><td>${c.createAt}</td><td>${c}</td></tr>			
					</#list>
				</#if>
			</table>
			<#include "pagination.tpl">
			<form action="${action}/news/addNews" method="post">
				<fieldset>
					<legend>发布内容</legend>
					<input type="hidden" name="owner" value="${newsType!!}"/>
					<input type="hidden" name="biz" value="news"/>
					<label>标题</label>
					<input type="text" name="title" placeholder="必填"/>
					<label>内容</label>
					<textarea name="content" style="width:100%;height:250px;"></textarea>
					<hr>
					<button type="submit" class="btn">发布</button>
				</fieldset>
			</form>
		</div>
	</div>	
</div>
<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/themes/default/default.css" />
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
			allowFileManager : true
		});
		prettyPrint();
	});
</script>