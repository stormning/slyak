<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<c:if test="${not empty currentPage.customCss}">
	<style>
		${currentPage.customCss}
	</style>
</c:if>

<div id="wrap" class="container">
	<div class="row">
		<!-- navbar start -->
		<c:set var="navPage" value="${currentPage.parent==null?currentPage:currentPage.parent}" scope="request"/>
		<div class="navbar">
			<div class="navbar-inner">
				<div class="container">
					<a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">Menu</a>
					<a href="/" class="brand">SLYAK</a>
					<div class="nav-collapse">
						<ul class="nav">
							<c:forEach items="${pages}" var="p" varStatus="status">
								<c:choose>
									<c:when test="${not empty p.children}">
										<li class="dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown">${p.name}<b class="caret"></b></a>
											<ul class="dropdown-menu">
												<c:forEach items="${p.children}" var="cp">
													<li><a href="/${cp.alias}">${cp.name}</a></li>
												</c:forEach>
											</ul>
										</li>
									</c:when>
									<c:otherwise>
										<li <c:if test="${p.id eq navPage.id}">class="active"</c:if>>
											<a href="/${status.first?'':p.alias}">${p.name}</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- navbar end -->
	</div>

	<div id="content" class="row">
		<tiles:insertAttribute name="content" />
	</div>
	<div id="footer"></div>
</div>