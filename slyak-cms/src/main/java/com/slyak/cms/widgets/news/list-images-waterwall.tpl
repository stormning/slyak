<link rel="stylesheet" href="${resource}/list-images-waterwall.css" />

<#include "img-url.tpl">
<#if comments??>
	<ul class="thumbnails list-imges">
		<#assign index = 0>
		<#assign sizePerLine = widget.settings.sizePerLine?number>
		<#assign spanClass = 'span'+12/sizePerLine>
		<#assign lineCount = (comments?size/sizePerLine)?ceiling>
		<#list comments as comment>
			<#assign currentLine = ((index+1)/sizePerLine)?ceiling>
			
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <li class="${spanClass}" style='<#if currentLine==lineCount>margin-bottom:0px;</#if><#if index%sizePerLine==0> margin-left:0px;</#if>'>
		      <a class="thumbnail" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
		          <img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" style="width:100%">
		      </a>
		      <h6><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h6>
		    </li>
		    <#assign index=index+1>
	    </#list>
	 </ul>
 </#if>