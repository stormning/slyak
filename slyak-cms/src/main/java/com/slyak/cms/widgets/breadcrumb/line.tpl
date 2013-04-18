<#if currentPage.parent??>
	<ul class="breadcrumb">
	  <li><a href="/${currentPage.parent.alias}">${currentPage.parent.name}</a> <span class="divider">/</span></li>
	  <li class="active">${currentPage.name}</li>
	</ul>
</#if>