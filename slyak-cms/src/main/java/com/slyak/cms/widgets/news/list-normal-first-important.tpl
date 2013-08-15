<link rel="stylesheet" href="${resource}/list-normal-first-important.css" />
<#if comments??>
	<#assign index=0>
	<#list comments as comment>
		<ul class="post_wrapper">
			<#if index==0>
				<li class="first">
					<h4><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h4>
					<p>
						<#if comment.imgCount gt 0>
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