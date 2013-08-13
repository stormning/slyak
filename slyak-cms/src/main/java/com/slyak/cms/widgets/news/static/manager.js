$(function() {
	var selectTypeId = newsParam.newsType ? newsParam.newsType : 0;
	var setting = {
		async : {
			enable : true,
			url : newsParam.loadChildTypeUrl,
			autoParam : [ "id=pid", "name=n", "level=lv" ],
			dataFilter : function(treeId, parentNode, responseData){
				var responseNodes = new Array();
				if(responseData){
					for(var i =0; i < responseData.length; i++) {
						responseNodes.push({
							id : responseData[i].id,
							pid : responseData[i].pid,
							name : responseData[i].name,
							isParent : !responseData[i].leaf
						});
					}
				}
				return responseNodes;
			}
		},
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : 0
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				selectTypeId = treeNode.id;
				location.href = newsParam.reloadUrl+selectTypeId;
				if (treeNode.pid == 0) {
					// disable editTypeBtn removeTypeBtn

				} else {

				}
			}
		}
	};

	var t = $("#tree");
	var zNodes = new Array();
	zNodes.push({
		id : 0,
		pid : -1,
		name : "分类列表",
		isParent : true
	});

	var gs = newsParam.allGroups;
	if (gs) {
		for ( var i = 0; i < gs.length; i++) {
			var g = gs[i];
			zNodes.push({
				id : g.id,
				name : g.name,
				pid : g.pid,
				isParent : !g.leaf
			});
		}
	}
	
	t = $.fn.zTree.init(t, setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.expandAll(true);
	zTree.selectNode(zTree.getNodeByParam("id", selectTypeId));

	if(selectTypeId>0){
		$("#editTypeBtn,#removeTypeBtn").removeAttr("disabled");
	}
	
	SLYAK.modal({
		id : "saveType",
		header : "新增分类",
		onShown:function(){
			setTimeout(function(){
				$("#saveType .form-inline").hover(function(){typeHelper.appendTo($(this)).show();},function(){typeHelper.hide();});
			},200);
		},
		onHidden : function() {
			$(this).removeData('modal');
		}
	});

	var typeHelper = $("<div id='typeHelper'><button class='btn btn-small'>删除</button><button class='btn btn-small'>添加</button></div>").appendTo("body");
	
	SLYAK.modal({
		id : "editType",
		header : "修改分类",
		onShow : function() {
			return !$("#editTypeBtn").is(":disabled");
		},
		onShown : function(){
			setTimeout(function(){
				$(".resizeList .form-inline").hover(function(){typeHelper.appendTo($(this)).show();},function(){typeHelper.hide();});
			},200);
		},
		onHidden : function() {
			$(this).removeData('modal');
		}
	});
	
	$("#removeTypeBtn").click(function(e){
		e.preventDefault();
		if(selectTypeId>0){
			if(!$(this).is(":disabled")){
				$.ajax($(this).attr("href"), {
					type : 'post',
					dataType : 'json',
					complete : function(res) {
						location.reload();
					}
				});
			}
		}
	});

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
			if (_chkeds.length == 1) {
				$('#' + _w + '-edit').removeAttr('disabled');
			}else{
				$('#' + _w + '-edit').attr('disabled', 'disabled');
			}
		} else {
			$('#' + _w + '-remove').attr('disabled', 'disabled');
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
	$('#' + _w + '-remove').click(function() {
		if(!$(this).is(":disabled")){
			var newsIdsStr = "?";
			for(var i=0;i<_chkeds.length;i++){
				newsIdsStr+="newsIds="+_chkeds[i]+"&";
			}
			$.post($(this).attr("rel")+newsIdsStr,function(){
				location.reload();
			});
		}
	});
	$('#' + _w + '-add').click(function() {
		$("#newsManagerForm").get(0).reset();
		editor.html("");
		var titleEl = $("#newsManagerForm input[name=title]");
		 $('body').scrollToID(titleEl);
		 titleEl.focus();
	});
	
	$('#' + _w + '-edit').click(function() {
		var _f = $("#newsManagerForm");
		$.post($(this).attr("rel")+"?id="+_chkeds[0],function(comment){
			_f.find("[name=owner]").val(comment.owner);
			_f.find("[name=title]").val(comment.title);
			_f.find("[name=id]").val(comment.id);
			editor.html(comment.content);
		});
	});
	
	$('#' + _w + '-table select').change(function() {
		var _select = $(this);
		var _newsId = _select.closest("tr").attr("newsId");
		newsParam.changeTypeUrl;
		$.post(newsParam.changeTypeUrl,{id:_newsId,owner:_select.val()});
	});
});