<table class="table table-bordered table-striped">
	<tr><th>主题</th><th style="width:80px">作者</th><th style="width:80px">回复/查看</th><th style="width:80px">最后发表</th></tr>
	<#if page.content??>
		<#list page.content as comment>
			<tr><td class="comment-title <#if comment.imgCount gt 0>comment-title-img</#if>">${comment.title}</td><td><div>${comment.creator!!}</div><div class="muted">${comment.createAt?string("yyyy-MM-dd")}</div></td><td>${comment.commented}/${comment.viewed}</td><td><div>${comment.lastCommentor!!}</div><div><#if comment.lastCommentAt??>${comment.lastCommentAt?string("yyyy-MM-dd")}</#if></div></td></tr>
		</#list>
	</#if>
</table>
<#include "pagination.tpl">