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
});