<#if comment??>
	<link rel="stylesheet" href="${resource}/detail.css" />
	<h3>${comment.title}</h3>
	<div class="post-info">
		<ul class="inline pull-right">
			<!--<li><i class="icon icon-heart"></i>${comment.liked}</li>-->
			<li><i class="icon icon-eye-open"></i>${comment.viewed}</li>
			<li><i class="icon icon-comment"></i>${comment.commented}</li>
		</ul>
		<ul class="inline">
			<li><i class="icon icon-calendar"></i>${comment.createAt?string("yyyy-MM-dd")}</li>
			<li><i class="icon icon-user"></i>${comment.creator?default("admin")}</li>
		</ul>
		<div class="clearfix"></div>
	</div>
	<div>
		${comment.content}
	</div>
</#if>					