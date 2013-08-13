<#macro buildbb pageToBuild>
	<#if bbindex==0>		<#assign str="<li>${pageToBuild.name}</li>">
		<#else>
		<#assign str="<li><a href='${ctx}/${pageToBuild.alias}'>${pageToBuild.name}</a><span class='divider'>/</span></li>">
	</#if>
	<#assign finalStr=str+finalStr>
	<#assign bbindex=bbindex+1>
	<#if pageToBuild.parent??>
		<@buildbb pageToBuild=pageToBuild.parent/>
	</#if>
</#macro>

<#assign bbindex=0/>
<#assign finalStr=''/>
<@buildbb pageToBuild=currentPage />
<ul class="breadcrumb">
	${finalStr}
</ul>