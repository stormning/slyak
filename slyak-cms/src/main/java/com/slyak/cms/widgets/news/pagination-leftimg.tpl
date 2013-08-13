<#if page.content??>
	<#include "img-url.tpl">
	<ul class="pagination-leftimg clearfix">
		<#list page.content as comment>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <li>
		      <div class="span5">
				<a class="thumbnail" href="">
		          <img src="${ctx}/file/newsImg/<@splitId idstr=comment.id/>/0/0.jpg?ver=${comment.ver}" style="width:100%">
		        </a>
			  </div>
			  <div class="span7">
		      	<h6><a href="">${comment.title}</a></h6>
		      	<p>
		      		${comment.fragment!!}
		      	</p>
		      </div>
		    </li>
	    </#list>
	 </ul>
 </#if>