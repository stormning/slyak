<link rel="stylesheet" href="${resource}/more-leftimgtext.css" />
<#if comments??>
	<ul>
		<#list comments as comment>
			<li class="span3">
              <a href="#" class="thumbnail">
                <img data-src="holder.js/260x120" alt="260x120" style="width:100%" src="${ctx}/">
              </a>
            </li>
		</#list>
	</ul>
	<#include "pagination.tpl">
</#if>