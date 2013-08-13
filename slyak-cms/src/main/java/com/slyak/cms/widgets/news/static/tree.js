$(function() {
	var selectTypeId = 0;
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

	var rgs = newsParam.rootGroups;
	if (rgs) {
		for ( var i = 0; i < rgs.length; i++) {
			var rg = rgs[i];
			zNodes.push({
				id : rg.id,
				name : rg.name,
				pid : rg.pid,
				isParent : !rg.leaf
			});
		}
	}
	
	var pgs = newsParam.pathGroups;
	if (pgs) {
		for ( var i = 1; i < pgs.length; i++) {
			var pg = pgs[i];
			zNodes.push({
				id : pg.id,
				name : pg.name,
				pid : pg.pid,
				isParent : !pg.leaf
			});
		}
	}
	
	t = $.fn.zTree.init(t, setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	if (selectTypeId) {
		zTree.selectNode(zTree.getNodeByParam("id", selectTypeId));
	}

	SLYAK.modal({
		id : "saveType",
		header : "新增分类",
		onShown : function() {
		},
		onHidden : function() {
			$(this).removeData('modal');
		},
		onSubmit : function(e) {
			var _this = $("#saveType");
			e.preventDefault();
			var data = {
				name : _this.find("input[name=name]").val(),
				pid : selectTypeId
			};
			$.ajax(_this.find("form").attr("action"), {
				type : 'post',
				dataType : 'json',
				data : data,
				complete : function() {
					location.reload();
				}
			});
		}
	});

	SLYAK.modal({
		id : "editType",
		header : "修改分类",
		onShow : function() {
		},
		onShown : function() {
		},
		onHidden : function() {
			$(this).removeData('modal');
		},
		onSubmit : function(e) {
			var _this = $("#saveType");
			e.preventDefault();
			var data = {};
			$.ajax(_this.find("form").attr("action"), {
				type : 'post',
				contentType : 'application/json',
				data : $.toJSON(data),
				complete : function() {
					location.reload();
				}
			});
		}
	});
});