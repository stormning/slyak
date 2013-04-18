<div class="carousel slide" id="home-carousel">
	<!-- Carousel items -->
	<div class="carousel-inner">
		 <#assign index=0>
		 <#list images as img>
		 	<div class="item<#if index==0> active</#if>">
		 		<img alt="" src="<#if img?starts_with("http")>${img}<#else>${ctx}${img}</#if>">
		 	</div>
		 	<#assign index=index+1>
	  	  </#list>
	</div>
	<#if images??>
		<a data-slide="prev" href="#home-carousel" class="carousel-control left">‹</a>
		<a data-slide="next" href="#home-carousel" class="carousel-control right">›</a>
	</#if>
</div>