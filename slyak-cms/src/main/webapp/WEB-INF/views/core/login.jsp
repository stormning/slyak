<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" scope="request"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Login | Bitsy Admin</title>
		<meta name="description" content="">
		<meta name="author" content="cuongv">
		<meta name="robots" content="index, follow">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
				
		<!-- CSS styles -->
		<link rel="stylesheet" type="text/css" href="/static/css/cssreset-min.css">
		<link rel="stylesheet" type="text/css" href="/static/thirdparty/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="/static/thirdparty/bootstrap/css/bootstrap-responsive.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/login.css">
		
		<!-- JS Libs -->
		<script type="text/javascript" src="/static/js/html5.js"></script>
		<script src="/static/thirdparty/jquery/jquery-1.9.1.min.js"></script>
		<script src="/static/thirdparty/bootstrap/js/bootstrap.min.js"></script>
		
	</head>
	<body class="single-page">
		<div class="container">
			<div class="container">
				<div class="content">
					<div class="row-fluid">
						<div id="login-container">
							<div id="login-header">						
								<h3>登录</h3>						
							</div> <!-- /login-header -->
			
							<div id="login-content" class="clearfix">
								<form action="${ctx}/login" method="post">
									<fieldset>
										<div class="control-group">
											<label class="control-label" for="username">用户名</label>
											<div class="controls">
												<input type="text" class="" id="username" name="username">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="password">密码</label>
											<div class="controls">
												<input type="password" class="" id="password" name="password">
											</div>
										</div>
									</fieldset>
											
									<div id="remember-me" class="pull-left">
										<input type="checkbox" name="rememberme" id="remember">
										<label id="remember-label" for="remember">记住我</label>
									</div>
											
									<div class="pull-right">
										<button type="submit" class="btn btn-warning btn-large">
											登录
										</button>
									</div>
								</form>							
							</div> <!-- /login-content -->	
							<div id="login-extra">
								<br/>
								<p>没有帐号? <a href="javascript:;">注册</a></p>
								<p>忘记密码? <a href="forgot_password.html">找回它</a></p>
							</div> <!-- /login-extra -->		
						</div>
					</div>
				</div>
			</div> <!-- /container -->
		</div>
	</body>
	</html>		