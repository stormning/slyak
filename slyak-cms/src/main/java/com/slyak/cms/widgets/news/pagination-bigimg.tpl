<link rel="stylesheet" href="${resource}/list-bigimg.css" />
<#if page.content??>
	<#include "img-url.tpl">
	<div class="pagination-leftimg">
		<#list page.content as comment>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <div class="panel">
		      <#if comment.imgCount gt 0>
				  <a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
			          <img src="${ctx}/file/newsImg/<@splitId idstr=comment.id/>/0/0.jpg?ver=${comment.ver}" style="width:100%">
			      </a>
		      </#if>
			  <div>
		      	<h3><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h3>
		      	<div class="muted">
		      		创建于 ${comment.createAt?string("yyyy-MM-dd")} 浏览数 ${comment.viewed} 评论数 ${comment.commented}
		      	</div>
		      	<div style="position:relative;">
		      		${comment.fragment!!}
		      		<a style="position:absolute;right:0;bottom:0" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">[查看详情]</a>
		      	</div>
		      </div>
		    </div>
	    </#list>
	    <#include "pagination.tpl">
	 </div>
 </#if>