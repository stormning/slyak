<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${currentPage.title}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="${currentPage.description}">
<meta name="keywords" content="${currentPage.keywords}">
<!-- html5 -->
<script type="text/javascript" src="/static/js/html5.js"></script>
<link rel="stylesheet" type="text/css" href="/static/css/cssreset-min.css">
<link rel="stylesheet" type="text/css" href="/static/thirdparty/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/static/thirdparty/bootstrap/theme/black-green/theme.css">
<link rel="stylesheet" type="text/css" href="/static/css/global.css">

<script src="/static/thirdparty/jquery/jquery-1.9.1.min.js"></script>
<script src="/static/thirdparty/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/js/base.js"></script>

<!-- admin area start-->
<link rel="stylesheet" type="text/css" href="/static/css/admin.css">
<script src="/static/thirdparty/jquery/jquery-ui.min.js"></script>
<script src="/static/thirdparty/ace/ace.js"></script>
<script src="/static/thirdparty/jquery/jquery.json.js"></script>
<script src="/static/js/admin.js"></script>

<script>
	$(function(){
		$.admin({ctx:'',currentPageId:'${currentPage.id}'});
	});
</script>

<!-- admin area end -->

<!-- baidu -->
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fddcde6a957b55e6295d9fbf4324b388c' type='text/javascript'%3E%3C/script%3E"));
$(function(){
	$("a:first").css({'display':'none'});
});
</script>


</head>
<body>
	<tiles:insertAttribute name="content" />
</body>
</html>