<#if currentPage.parent??>
	<ul class="breadcrumb">
	  <li><a href="${ctx}/${currentPage.parent.alias}">${currentPage.parent.name}</a> <span class="divider">/</span></li>
	  <li class="active">${currentPage.name}</li>
	</ul>
</#if>