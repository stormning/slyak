<ul>
	<#if comments??>
		<#list comments as comment>
			<li><a href='${view}/news/detail?newsId=${comment.id}'>${comment.title}</a></li>
		</#list>
	</#if>
</ul>