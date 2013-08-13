<#if types?? && types?keys?size gt 0>
	<ul class="nav nav-tabs">
		<#assign tabIndex=0>
		<#list types?values as t>
	    	<li <#if tabIndex==0>class="active"</#if>><a href="#widget-${widget.id}-${t.type.id}" data-toggle="tab">${t.type.name}</a></li>
	    	<#assign tabIndex=tabIndex+1>
	    </#list>
	</ul>
	<div class="tab-content">
		<#assign indexOuter=0>
		<#list types?values as t>
			<#if commentsMap[(t.type.id)?c]!?size gt 0>
				<div class="tab-pane<#if indexOuter==0> active</#if>" id="widget-${widget.id}-${t.type.id}">
					<#assign comments=commentsMap[(t.type.id)?c] >
					<#include "list-images.tpl">				
				</div>
			</#if>
			<#assign indexOuter=indexOuter+1>
		</#list>
	</div>
</#if>
 