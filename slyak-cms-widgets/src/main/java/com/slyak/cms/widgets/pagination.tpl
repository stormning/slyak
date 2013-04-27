<#if page??>
	<script>
		$(function(){
			$(".pagination li[class!=active] a").click(function(e){
				e.preventDefault();
				var _page = $(e.target).attr("page");
				var _href = location.href;
				var reg=new RegExp("(.+\?.*page=)(\\d{1,})(.*)","gmi");
				if(_href.match(reg)){
					_href = _href.replace(reg,"$1"+_page+"$3");
				}else if(_href.indexOf("?")==-1){
					_href+="?page="+_page;
				}else{
					_href+="&page="+_page;
				}
				location.href=_href;
			});	
		});
	</script>
	<#assign totalPages = page.totalPages>
	<#if totalPages gt 1 >
		<#assign currentPage=page.number+1 size=page.size maxButton=7 half=maxButton/2>
		
		<#assign startButton=currentPage-half>
		<#if startButton<1>
			<#assign startButton=1>
		</#if>
		
		<#assign endButton=totalPages>
		<#if currentPage-half gt totalPages>
			<#assign endButton=currentPage-half>
		</#if>
		
		<div class="pagination pagination-centered">
			<ul>
				<#list startButton..endButton as i>
					<#if startButton gt 1&&i==startButton-1>
						<li><a href="#" page="${currentPage-1}">Prev</a></li>
					</#if>
					<li <#if i==currentPage>class="active" </#if>>
						<a <#if i!=currentPage>href="#" page="${i}"</#if>>${i}</a>
					</li>
					<#if endButton lt totalPages&&i==endButton-1>
						<li><a href="#" page="${currentPage+1}">Next</a></li>
					</#if>
				</#list>
			</ul>
		</div>	
	</#if>
</#if>