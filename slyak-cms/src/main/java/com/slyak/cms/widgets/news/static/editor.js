$(function() {
	var editor = KindEditor.create('textarea[name="content"]', {
		items : [ 'bold', 'italic', 'underline', 'strikethrough',
				'removeformat', '|', 'insertorderedlist',
				'insertunorderedlist', 'forecolor', 'hilitecolor', 'fontname',
				'fontsize', '|', 'link', 'unlink', 'emoticons', 'shcode',
				'image', 'flash', 'quote', '|', 'code', 'source', 'about' ],
		cssPath : newsParam.cssPath,
		uploadJson : newsParam.uploadJson,
		filePostName : 'file',
		fileManagerJson : newsParam.fileManagerJson,
		allowFileManager : true
	});
	prettyPrint();
	var _w = 'widget' + newsParam.widgetId;
	var tableId = _w + '-table';
	var _chkeds = [];

	var afterClick = function() {
		if (_chkeds.length > 0) {
			$('#' + _w + '-remove').removeAttr('disabled');
			$('#' + _w + '-top').removeAttr('disabled');
			if (_chkeds.length == 1) {
				$('#' + _w + '-edit').removeAttr('disabled');
			}
		} else {
			$('#' + _w + '-remove').attr('disabled', 'disabled');
			$('#' + _w + '-top').attr('disabled', 'disabled');
			$('#' + _w + '-edit').attr('disabled', 'disabled');
		}
	};

	$("#" + tableId).find(":checkbox:first").click(function() {
		var _chks = $(this).closest("tr").nextAll().find(":checkbox");
		if (_chks && _chks.length > 0) {
			for ( var i = 0; i < _chks.length; i++) {
				var _chk = _chks[i];
				var _newsId = $(_chk).attr("newsId");
				if (_chk.checked) {
					_chkeds = $.grep(_chkeds, function(n) {
						return n != _newsId;
					});
					_chk.checked = false;
				} else {
					_chkeds.push(_newsId);
					_chk.checked = true;
				}
			}
			afterClick();
		}
	});

	$("#" + tableId).find(":checkbox:not(:first)").click(function() {
		var _chk = this;
		var _newsId = $(_chk).attr("newsId");
		if (_chk.checked) {
			_chkeds.push(_newsId);
		} else {
			_chkeds = $.grep(_chkeds, function(n) {
				return n != _newsId;
			});
		}
		if (_chkeds.length == 0) {
			$("#" + tableId).find(":checkbox:first").removeAttr("checked");
		}
		afterClick();
	});
	$('#' + _w + '-removeType:enabled').click(function() {
		var _nt = newsParam.newsType;
		if (_nt) {
			$(this).attr("disabled", "disabled");
			$.ajax(newsParam.removeType, {
				async : false,
				data : {
					newsType : _nt
				}
			});
			location.reload();
		}
	});
	$('#' + _w + '-remove:enabled').click(function() {
		console.log(_chkeds);
		alert(11);
	});
	$('#' + _w + '-edit:enabled').click(function() {
		alert(11);
	});
	$('#' + _w + '-top:enabled').click(function() {
		alert(11);
	});
	
});