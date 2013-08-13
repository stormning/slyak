<link rel="stylesheet" href="${resource}/flexslider/flexslider.css" type="text/css">
<script src="${resource}/flexslider/jquery.flexslider.js"></script>

<script>
	$(function() {
	  $('.flexslider').flexslider({
	    animation: "slide",
	    animationLoop: false
	     <#if itemWidth??>,itemWidth: ${itemWidth}</#if>
	  });
	});
</script>

<div class="flexslider">
  <ul class="slides">
  	  <#list items as item>
  	  	  <li>
  	  	  	${item}
  	  	  </li>
  	  </#list>
  </ul>
</div>