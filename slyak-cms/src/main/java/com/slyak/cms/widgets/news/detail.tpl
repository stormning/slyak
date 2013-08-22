<#if comment??>
	<link rel="stylesheet" href="${resource}/detail.css" />
	
	<div class="news-detail">
		<div class="news-detail-bar">
			<div class="pull-right">
				<div class="btn-group">
					<button type="button" class="btn news-detail-like disabled"><i class="icon icon-heart"></i>&nbsp;喜欢 ${comment.liked}</button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn news-detail-comments"><i class="icon icon-comment"></i>&nbsp;评论 <span class="news-detail-comment-count">${comment.commented}</span></button>
				</div>
			</div>
			<h4 class="pull-left">${comment.title}</h4>
		</div>
		<div class="news-detail-content">
			<#if comment.fragment??>
				${comment.content}
				<#else>
				<p class="text-center">
					${comment.content}
				</p>
			</#if>
		</div>
		<div class="news-detail-footer">
			<a href="http://${(comment.from)!'SLYAK.COM'}">${(comment.from)!'SLYAK.COM'}</a>
		</div>
	</div>
	
	<#if (widget.settings.commentSize)?number gt 0>
		<div class="news-comment">
			<#if children??>
				<#list children as cc>
					<div class="news-comment-item media news-comment-coment">
						<a class="pull-left" href="javascript:void(0)">
							<img class="media-object" src="${ctx}/static/images/user-34.jpg">
						</a>
						<div class="media-body">
							<h5 class="media-heading">某骚年  <span class="news-comment-date">${cc.createAt?string('yyyy-MM-dd HH:mm')}</span></h5>
							<p>${cc.content}</p>
						</div>
					</div>
				</#list>
			</#if>
			<#if comment.commented gt (widget.settings.commentSize)?number>
				<div class="news-comment-more alert alert-info text-center">点此加载更多评论</div>
			</#if>
			<div class="news-comment-item media news-comment-area">
				 <a class="pull-left" href="javascript:void(0)">
					<img class="media-object" src="${ctx}/static/images/user-34.jpg">
				</a>
				<div class="media-body">
					<form action="${action}/news/addNews" method="post">
						<input type="hidden" name="referer" value="${comment.id}"/>
						<h5 class="media-heading">就是你，骚年！</h5>
						<textarea name="content"></textarea>
						<input type="submit" class="btn btn-primary" value="发表评论">
					</form>
				</div>
			</div>
		</div>
		<div class="data-holder" commentId="${comment.id}" ctx="${ctx}" commentCount="${comment.commented}" commentLimit="${(widget.settings.commentSize)?number}" moreCommentUrl="${action}/news/moreComment?referer=${comment.id}&limit=${(widget.settings.commentSize)?number}"></div>
	</#if>
</#if>					