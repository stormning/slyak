<link rel="stylesheet" href="${resource}/flexslider/flexslider.css" type="text/css">
<script src="${resource}/flexslider/jquery.flexslider.js"></script>

<script>
	$(function() {
	  $('.flexslider').flexslider({
	    animation: "slide",
	    animationLoop: false
	     <#if itemWidth??>,itemWidth: ${itemWidth}</#if>
	    <#if thumbnails??>,controlNav: "thumbnails"</#if>
	  });
	});
</script>

<div class="flexslider">
  <ul class="slides">
  	  <#list images as img>
  	  	  <li>
  	  	  	<img src="<#if img?starts_with("http")>${img}<#else>${ctx}${img}</#if>"/>
  	  	  </li>
  	  </#list>
  </ul>
</div>