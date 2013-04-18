<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<div class="row">
	<section class="span3 bs-docs-sidebar">
		<ul class="nav nav-list bs-docs-sidenav">
			<li <c:if test="${'logForm' eq subnav}">class="active"</c:if>><a href="${ctx}/account/logForm"><i class="icon-chevron-right"></i>记账</a></li>
			<li <c:if test="${'log' eq subnav}">class="active"</c:if>><a href="${ctx}/account/log"><i class="icon-chevron-right"></i>收支明细</a></li>
			<li <c:if test="${'logReport' eq subnav}">class="active"</c:if>><a href="${ctx}/account/logReport"><i class="icon-chevron-right"></i>统计图表</a></li>
			<li <c:if test="${'logTypes' eq subnav}">class="active"</c:if>><a href="${ctx}/account/logTypes"><i class="icon-chevron-right"></i>分类设置</a></li>
			<li <c:if test="${'logMembers' eq subnav}">class="active"</c:if>><a href="${ctx}/account/logMembers"><i class="icon-chevron-right"></i>成员设置</a></li>
		</ul>
	</section>
	<section class="span9">
		<tiles:insertAttribute name="right" ignore="true"/>
	</section>
</div>