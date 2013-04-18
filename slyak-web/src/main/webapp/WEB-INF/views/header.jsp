<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a href="#" class="brand">SLYAK</a>
      <div class="nav-collapse collapse">
        <ul class="nav">
          <li <c:if test="${'index' eq nav}">class="active"</c:if>><a href="${ctx}/">Home</a></li>
          <shiro:user>
          	  <li <c:if test="${'account' eq nav}">class="active"</c:if>><a href="${ctx}/account/log">My Account</a></li>
          </shiro:user>
          <li <c:if test="${'other' eq nav}">class="active"</c:if>><a href="${ctx}/other">Look Around</a></li>
          <li <c:if test="${'forum' eq nav}">class="active"</c:if>><a href="${ctx}/forum">Forum</a></li>
          <li <c:if test="${'faq' eq nav}">class="active"</c:if>><a href="${ctx}/faq">FAQ</a></li>
        </ul>
        <shiro:guest>
        	<ul class="nav pull-right">
			    <li><button class="btn btn-link" type="button" data-toggle="modal" href="${ctx}/regist" data-target="#registModal">Regist</button></li>
			    <li><button class="btn btn-link" type="button" data-toggle="modal" href="${ctx}/login" data-target="#loginModal">Login</button></li>
		    </ul>
	    </shiro:guest>
	    <shiro:user>
	    	<ul class="nav pull-right">
	    		<li class="dropdown">						
					<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="icon-cog"></i>
						Settings
						<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:;">Account Settings</a></li>
						<li><a href="javascript:;">Privacy Settings</a></li>
						<li class="divider"></li>
						<li><a href="javascript:;">Help</a></li>
					</ul>						
				</li>
				<li class="dropdown">						
					<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="icon-user"></i> 
						<shiro:principal/>
						<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:;">My Profile</a></li>
						<li><a href="javascript:;">My Groups</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/public/logout">Logout</a></li>
					</ul>						
				</li>
	    		<%-- <li>
					<a href="#"><shiro:principal/></a>
				</li>
				<li class="divider-vertical"></li>
				<li>
					<a href="${ctx}/public/logout">Logout</a>
				</li> --%>
	    	</ul>
	    </shiro:user>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>
<tiles:insertAttribute name="subheader" ignore="true" />