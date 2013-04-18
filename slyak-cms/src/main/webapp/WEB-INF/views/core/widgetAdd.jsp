<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<div class="tabbable tabs-left">
	<div class="row-fluid">
		<div class="span3">
			<div class="well" style="padding: 8px 0px">
				<ul class="nav nav-list">
					<c:forEach items="${widgetInfoMap}" var="item" varStatus="status">
						<li <c:if test="${status.first}">class="active"</c:if>>
							<a href="#widget-pane-${item.key}" data-toggle="tab">
								<spring:message code="${item.key}"/>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="span9">
			<div class="tab-content" id="${item.key}" data-toggle="tab">
				<c:forEach items="${widgetInfoMap}" var="item" varStatus="status">
					<div class="tab-pane <c:if test="${status.first}">active</c:if>" id="widget-pane-${item.key}">
						<c:forEach items="${widgetInfoMap[item.key]}" var="widgetInfo">
							<c:if test="${widgetInfo.value.show}">
								<div class="media">
								  <div class="pull-left widget-snapshot">
								  	 暂无截图
								  </div>  
								  <div class="media-body">
								    <h4 class="media-heading"><spring:message code="${widgetInfo.value.region}.${widgetInfo.value.name}"/></h4>
								    <%-- <p><spring:message code="${widgetInfo.value.region}.${widgetInfo.value.name}.description"/></p> --%>
								    <p><button class="btn btn-small" widgetName="${widgetInfo.value.region}.${widgetInfo.value.name}">马上添加</button></p>
								  </div>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
