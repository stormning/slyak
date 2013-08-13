<link rel="stylesheet" href="${resource}/list-normal.css" />
<#if comments??>
	<ul class="list-normal">
		<#list comments as comment>
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
		</#list>
	</ul>
</#if>		