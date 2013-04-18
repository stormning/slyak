<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="tabbable">
	<!-- Only required for left/right tabs -->
	<ul class="nav nav-tabs">
		<li class="active"><a href="#globalCssTab" data-toggle="tab">全局样式</a></li>
		<li><a href="#globalJspTab" data-toggle="tab">全局布局</a></li>
	</ul>
	<form method="post" action="/global/edit">
		<input type="hidden" name="pageId" value="${param.pageId}">
		<div class="tab-content">
			<div class="alert alert-info"><strong>提示:</strong>按F11进行全屏编辑切换</div>
			<div class="tab-pane active" id="globalCssTab">
				<textarea style="display: none;" name="css" id="golbalCssHolder">${global.css}</textarea> 
				<div id="globalCss"></div>
			</div>
			<div class="tab-pane" id="globalJspTab">
				<textarea style="display: none;" name="jsp" id="golbalJspHolder">${global.jsp}</textarea>
				<div id="globalJsp"></div>
			</div>
		</div>
	</form>
</div>