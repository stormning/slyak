$(function() {
	$('.list-images-waterwall .item-wrapper img').hover(function() {
		var _this = $(this);
		_this.stop().animate({
			opacity : 0.7
		}, function() {
			_this.animate({
				opacity : 1
			});
		});
	}, function() {
		$(this).stop().animate({
			opacity : 1
		});
	});
	
	var commentCount,offset=0,ctx,commentLimit,moreCommentUrl,commentId;
	
	var buildHtml = function(comment){
		var date = new Date(comment.createAt);
		return "<div class=\"news-comment-item media\" style=\"display:none\">"+
						"<a class=\"pull-left\" href=\"javascript:void(0)\">"+
						"<img class=\"media-object\" src=\""+ctx+"/static/images/user-34.jpg\">"+
					"</a>"+
					"<div class=\"media-body\">"+
						"<h5 class=\"media-heading\">某骚年  <span class=\"news-comment-date\">"+date.format('yyyy-MM-dd HH:mm')+"</span></h5>"+
						"<p>"+comment.content+"</p>"+
					"</div>"+
				"</div>";
	};
	
	var modal = SLYAK.modal({
		id : "ajaxDetail",
		nofooter : true,
		onHidden : function() {
			$(this).removeData('modal');
			$(this).find(".modal-body").html("loading......");
		},
		onShown : function() {
			var dataHolder = $(".data-holder");
			commentCount=parseInt(dataHolder.attr("commentCount"));
			ctx=dataHolder.attr("ctx");
			commentLimit=parseInt(dataHolder.attr("commentLimit"));
			moreCommentUrl=dataHolder.attr("moreCommentUrl");
			commentId = dataHolder.attr("commentId");
			
			$(".news-detail-comments").on("click",function(){
				var commentTextArea = $(".news-comment textarea");
				$('body').scrollToID(commentTextArea);
				commentTextArea.focus();
			});
			$(".news-comment-item form").on("submit",function(e){
				var _this = $(this);
				e.preventDefault();
				var data = {content:_this.find("[name=content]").val(),referer:_this.find("[name=referer]").val()};
				$.post(_this.attr("action"),data,function(res){
					commentCount = commentCount+1;
					$(".news-detail-comment-count").html(commentCount);
					$(".list-images-waterwall #comment-"+commentId+" .item-comment-count").html(commentCount);
					$(buildHtml(res)).prependTo(".news-comment").fadeIn();
				});
			});
			$(".news-comment-more").on("click",function(){
				offset = offset+commentLimit;
				if(offset>commentCount-1){
					offset = commentCount-1;
				}
				$.get(moreCommentUrl+"&offset="+offset,function(res){
					if(res&&res.length>0){
						var _htm = "";
						for(var i=0;i<res.length;i++){
							_htm+=buildHtml(res[i]);
						}
						$(_htm).after(".news-comment-coment:last").fadeIn(function(){
							var commentTextArea = $(".news-comment textarea");
							$('body').scrollToID(commentTextArea);
						});
					}
					if(offset+commentLimit>=commentCount-1){
						$(".news-comment-more").remove();
					}
				});
			});
		}
	});
});