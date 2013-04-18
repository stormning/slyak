<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<script>
$(function(){
	$('#calendar').popover({selector:"#test",placement:"bottom"});
});
</script>
<div class="widget-box">
	<div class="widget-title">
		<div class="buttons">
			<div class="btn-group">
			  <a class="btn btn-small" href="#"><i class="icon-user"></i> 所有成员</a>
			  <a class="btn btn-small dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li><a href="#">豆妈</a></li>
			    <li><a href="#">豆豆</a></li>
			  </ul>
			</div>
			<div class="btn-group">
			  <!-- <button class="btn btn-small"><i class="icon-chevron-left"></i></button> -->
			  <button class="btn btn-small" id="calendar">......-<fmt:formatDate value="<%=new Date()%>" pattern="yyyy/MM"/></button>
			  <!-- <button class="btn btn-small"><i class="icon-chevron-right"></i></button> -->
			</div>
		</div>
		<ul class="nav nav-tabs">
			<!-- root -->
			<c:forEach items="${rootTypes}" var="rootType">
				<li
					<c:if test="${rootType.id == checkedRt.id}">class="active"</c:if>>
					<a href="${ctx}/account/log/${rootType.id}">${rootType.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="widget-content tab-content">
		<ul class="nav nav-pills">
			<li <c:if test="${empty checkedCt}">class="active"</c:if>><a
				href="${ctx}/account/log">所有分类</a></li>
			<c:forEach items="${checkedRt.children}" var="secondLevelType">
				<li
					<c:if test="${not empty checkedCt && secondLevelType.id == checkedCt.id}">class="active"</c:if>>
					<a href="${ctx}/account/log/${secondLevelType.id}">${secondLevelType.name}</a>
				</li>
			</c:forEach>
		</ul>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>描述</th>
						<th>成员</th>
						<th>分类</th>
						<th>收入/支出</th>
						<th>时间</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${logPage.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录 , <a href="${ctx}/account/logForm?typeId=${checkedCt.id}">记一笔</a> ?
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${logPage.content}" var="al">
								<tr>
									<td class="taskDesc">${al.title}</td>
									<td><c:choose><c:when test="${empty al.accountLogMember}">未指定</c:when><c:otherwise>${al.accountLogMember.name}</c:otherwise></c:choose></td>
									<td class="taskStatus"><c:choose><c:when test="${empty al.accountLogType}">未指定</c:when><c:otherwise>${al.accountLogType.name}</c:otherwise></c:choose></td>
									<td class="taskStatus">${al.units}</td>
									<td class="taskOptions"><fmt:formatDate value="${al.happenTime}" pattern="yyyy-MM-dd"/></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<c:if test="${logPage.totalPages>1}">
				<c:set var="page" value="${logPage}" scope="request"/>
				<%@include file="../pagination.jsp"%>
			</c:if>
		</div>
	</div>
</div>