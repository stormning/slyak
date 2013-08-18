<link rel="stylesheet" href="${resource}/list-normal-first-important.css" />
<#if comments??>
	<#assign index=0>
	<#list comments as comment>
		<ul class="post_wrapper">
			<#if index==0>
				<li class="first">
					<h4><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h4>
					<p class="text-left">
						<#if comment.imgCount gt 0>
							<#assign offset=0>
							<#assign tmp=''>
							<#assign step=2>
							<#include "img-url.tpl">
							<div class="row-fluid">
								<div class="span4">
									<div class="img-holder">
										<a class="thumbnail" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
											<img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/0.jpg?ver=${comment.ver}" style="width:100%">
										</a>
									</div>
								</div>
								<div class="text-left">
									${comment.fragment}
								</div>
							</div>
							<#else>
							${comment.fragment}
						</#if>
					</p>
				</li>
				<#else>
				<li>
					<#if (widget.settings.showType)=='true' && types??>
						<#if types[comment.owner]??>
							<#assign t = types[comment.owner]>
							<a href="<#if (t.page)??>${ctx}/${t.page.alias}</#if>" target="_blank">[${t.type.name}]</a>&nbsp;
						</#if>
					</#if>	
					<a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
						${comment.title}
					</a>
				</li>
			</#if>
		</ul>
		<#assign index=index+1>
	</#list>
</#if>		