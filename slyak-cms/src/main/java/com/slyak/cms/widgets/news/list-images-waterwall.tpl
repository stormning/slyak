<link rel="stylesheet" href="${resource}/list-images-waterwall.css" />
<script charset="utf-8" src="${resource}/list-images-waterwall.js"></script>

<#if comments?? && comments?size gt 0>
	<#include "img-url.tpl">
	<#macro loopRenderComment cline row>
		<#if cline*sizePerLine lte comments?size-1>
			<#assign currentIndex=row-1+cline*sizePerLine>
			<#if currentIndex lt comments?size>
				<#assign comment = comments[row-1+cline*sizePerLine]>
				<#assign offset=0>
				<#assign tmp=''>
				<#assign step=2>
				<div class="item">
					<a class="item-wrapper" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
				      <img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/z0.jpg?ver=${comment.ver}" style="width:100%">
			        </a>
			        <div class="item-meta">
			        	<p class="item-description">${comment.title}</p>
			        	<div class="item-social-meta">
			        		<span>${comment.viewed} 查看</span>
			        		<span>${comment.commented} 评论</span>
			        	</div>
			        </div>
			        <div class="item-credits">
		        		<a class="user" href="javascript:void(0)"><img src="${ctx}/static/images/user-34.jpg"></a>
		        		<div style="margin-left:44px;height:34px;position:relative">
		        			<a>沉城</a><br>发表于 ${comment.createAt?string('yyyy-MM-dd')}
		        		</div>
			        </div>
		        </div>
				<@loopRenderComment cline=cline+1 row=row/>
			</#if>	
		</#if>
	</#macro>
	<ul class="row-fluid list-images-waterwall">
		<#assign index = 0>
		<#assign sizePerLine = widget.settings.sizePerLine?number>
		<#assign spanClass = 'span'+12/sizePerLine>
		<#assign lineCount = (comments?size/sizePerLine)?ceiling>
		
		<#list 1..sizePerLine as rn>
			<li class="${spanClass}">
				<@loopRenderComment cline=0 row=rn />
			</li>
		</#list>
	 </ul>
 </#if>