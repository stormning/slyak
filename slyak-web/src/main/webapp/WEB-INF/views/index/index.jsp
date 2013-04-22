<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dashboard.css">

<div class="row">
	<div class="span8">

		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-file"></i>
				</span>
				<h5>Recent Posts</h5>
			</div>
			<div class="widget-content nopadding">
				<ul class="recent-posts">
					<li>
						<div class="user-thumb">
							<img width="40" height="40" src="img/demo/av2.jpg" alt="User">
						</div>
						<div class="article-post">
							<span class="user-info"> By: neytiri on 2 Aug 2012, 09:27
								AM, IP: 186.56.45.7 </span>
							<p>
								<a href="#">Vivamus sed auctor nibh congue, ligula vitae
									tempus pharetra...</a>
							</p>
							<a class="btn btn-primary btn-mini" href="#">Edit</a> <a
								class="btn btn-success btn-mini" href="#">Publish</a> <a
								class="btn btn-danger btn-mini" href="#">Delete</a>
						</div>
					</li>
					<li>
						<div class="user-thumb">
							<img width="40" height="40" src="img/demo/av3.jpg" alt="User">
						</div>
						<div class="article-post">
							<span class="user-info"> By: john on on 24 Jun 2012, 04:12
								PM, IP: 192.168.24.3 </span>
							<p>
								<a href="#">Vivamus sed auctor nibh congue, ligula vitae
									tempus pharetra...</a>
							</p>
							<a class="btn btn-primary btn-mini" href="#">Edit</a> <a
								class="btn btn-success btn-mini" href="#">Publish</a> <a
								class="btn btn-danger btn-mini" href="#">Delete</a>
						</div>
					</li>
					<li>
						<div class="user-thumb">
							<img width="40" height="40" src="img/demo/av1.jpg" alt="User">
						</div>
						<div class="article-post">
							<span class="user-info"> By: michelle on 22 Jun 2012,
								02:44 PM, IP: 172.10.56.3 </span>
							<p>
								<a href="#">Vivamus sed auctor nibh congue, ligula vitae
									tempus pharetra...</a>
							</p>
							<a class="btn btn-primary btn-mini" href="#">Edit</a> <a
								class="btn btn-success btn-mini" href="#">Publish</a> <a
								class="btn btn-danger btn-mini" href="#">Delete</a>
						</div>
					</li>
					<li class="viewall"><a href="#" class="tip-top"
						data-original-title="View all posts"> + View all + </a></li>
				</ul>
			</div>
		</div>


		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"><i class="icon-repeat"></i></span>
				<h5>Recent Activity</h5>
			</div>
			<div class="widget-content nopadding">
				<ul class="activity-list">
					<li><a href="#"> <i class="icon-user"></i> <strong>Admin</strong>
							added <strong>1 user</strong> <span>2 hours ago</span>
					</a></li>
					<li><a href="#"> <i class="icon-file"></i> <strong>Caroline
								Trin</strong> write a <strong>blog post</strong> <span>Yesterday</span>
					</a></li>
					<li><a href="#"> <i class="icon-envelope"></i> <strong>John
								Doe</strong> sent a <strong>message</strong> <span>2 days ago</span>
					</a></li>
					<li><a href="#"> <i class="icon-picture"></i> <strong>Matt
								Armon</strong> updated <strong>profile photo</strong> <span>2 days
								ago</span>
					</a></li>
					<li><a href="#"> <i class="icon-user"></i> <strong>Admin</strong>
							bans <strong>3 users</strong> <span>week ago</span>
					</a></li>
				</ul>
			</div>
		</div>

	</div>
	
	<div class="span4">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"><i class="icon-user"></i></span>
				<h5>Most active users</h5>
			</div>
			<div class="widget-content nopadding">
				<ul>
					<li class="user-thumb">
						<img width="40" height="40" src="img/demo/av2.jpg" alt="User">
					</li>
					<li class="user-thumb">
						<img width="40" height="40" src="img/demo/av2.jpg" alt="User">
					</li>
					<li class="user-thumb">
						<img width="40" height="40" src="img/demo/av2.jpg" alt="User">
					</li>
					<li class="user-thumb">
						<img width="40" height="40" src="img/demo/av2.jpg" alt="User">
					</li>
				</ul>
			</div>
		</div>	
	</div>
</div>