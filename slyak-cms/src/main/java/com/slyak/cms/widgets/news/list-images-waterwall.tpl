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
					<a class="item-wrapper" data-target="#ajaxDetail" href="${action}/news/detail?newsId=${comment.id}" data-toggle="modal">
				        <img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/z0.jpg?ver=${comment.ver}" style="width:100%">
			        </a>
			        <div class="item-meta">
			        	<p class="item-description">${comment.title}</p>
			        	<div class="item-social-meta">
			        		<span><em>${comment.viewed}</em> 查看</span>
			        		<span><em class="item-comment-count">${comment.commented}</em> 评论</span>
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