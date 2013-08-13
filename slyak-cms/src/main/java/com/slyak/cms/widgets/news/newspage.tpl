<#if page??>
	<#if page.content??>
		<link rel="stylesheet" href="${resource}/newspage.css" />
		<#list page.content as comment>
			<ul class="post_wrapper">
				<li><span class="muted pull-right">${comment.createAt?string("MM-dd")}</span><a href="${view}/news/detail?newsId=${comment.id}">${comment.title}</a></li>
			</ul>
		</#list>
		<#include "pagination.tpl">
	</#if>
</#if>