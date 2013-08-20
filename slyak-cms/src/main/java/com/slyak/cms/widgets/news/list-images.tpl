<link rel="stylesheet" href="${resource}/list-images.css" />

<#if comments?? && comments?size gt 0>
	<#include "img-url.tpl">
	<#macro loopRenderComment cline row>
		<#if cline*sizePerLine lte comments?size-1>
			<#assign comment = comments[row-1+cline*sizePerLine]>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
			<li class="${spanClass}">
				<a class="thumbnail" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
			      <img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" style="width:100%">
		        </a>
		        <h6><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h6>
			</li>	
			<@loopRenderComment cline=cline+1 row=row/>
		</#if>
	</#macro>
	<ul class="row-fluid list-images">
		<#assign index = 0>
		<#assign sizePerLine = widget.settings.sizePerLine?number>
		<#assign spanClass = 'span'+12/sizePerLine>
		<#assign lineCount = (comments?size/sizePerLine)?ceiling>
		
		<#list 1..sizePerLine as rn>
			<@loopRenderComment cline=0 row=rn />
		</#list>
	 </ul>
 </#if>