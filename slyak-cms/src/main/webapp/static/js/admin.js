$(function() {
		$.extend({
			admin:function(args){
				var ctx = args.ctx||'';
				var currentPageId = args.currentPageId;
				var ajaxSort = function(widgetsData){
					$.ajax(ctx+"/widget/sort",{type:'post',contentType:'application/json;charset=UTF-8',data:$.toJSON(widgetsData)});
				};
				$(".column").sortable({
					connectWith : ".column",
					cursor : "move",
					update: function(event, ui) {
						if(ui.sender){
							var data = new Array();
							//to another container , update self and siblings
							var rank = ui.item.index();
							data.push({"id":ui.item.attr("id").replace("panel-",""),"rank":rank,"container":$(this).attr("id")});
							ui.item.siblings(".panel").each(function(){
								data.push({"id":$(this).attr("id").replace("panel-",""),"rank":$(this).index()});
							});
							ajaxSort(data);
						}else{
							if(ui.item.parent().attr("id")==$(this).attr("id")){
								var data = new Array();
								//just change rank , update self and rank changed siblings(do unitl newRank==oldRank) 
								var rank = ui.item.index();
								data.push({"id":ui.item.attr("id").replace("panel-",""),"rank":rank});
								ui.item.siblings(".panel").each(function(){
									if($(this).index()!= rank+1){
										return false; 
									}
									data.push({"id":$(this).attr("id").replace("panel-",""),"rank":++rank});
								});
								ajaxSort(data);
							}else{
								//remove form self,don't need to update
							}
						}
					}
				});
				$(".column").disableSelection();
				
				SLYAK.modal({id:"widgetEdit",header:"widget配置",
					onShown:function(){
						setTimeout(function(){
							var _this = $("#widgetEdit");
							_this.find("select[name=border]").on("change",function(){
								_this.find("#borderClassSelector").toggle();
							});
							$(".system-borders a").on("click",function(){
								$(this).siblings().removeClass("btn-selected");
								$(this).addClass("btn-selected");
							});
						}, 200);
					},
					onHidden:function(){$(this).removeData('modal');},
					onSubmit:function(e){
						var _this = $("#widgetEdit");
						var tpl = _this.find("select[name=border]").val();
						var bc = tpl=='none.tpl'?"":_this.find(".btn-selected").attr("data-widget-setstyle");
						var stts = {};
						_this.find(".settings").each(function(index,el){
							var _key = $(el).children("div:eq(0)").attr("key");
							if($(el).attr("inputType")=='CHECKBOX'){
								var arr = new Array();
								$(el).find("[name=stvalue]:checked").each(function(index,el){
									arr.push($(el).val());
								});
								if(arr.length>0){
									stts[_key]= arr;
								}
							}else{
								stts[_key]=$(el).find("[name=stvalue]").val();
							}
						});
						var data = {id:_this.find("input[name=id]").val(),title:_this.find("input[name=title]").val(),borderTpl:tpl,borderClass:bc,settings:stts};
						$.ajax(_this.find("form").attr("action"),{type:'post',contentType:'application/json;charset=UTF-8',data:$.toJSON(data),complete:function(){
							location.reload();
						}});
					}
				});
				var panelHelper = $("<div id='panelHelper' style='display:none'><a class='icon icon-edit' data-toggle='modal' data-target='#widgetEdit'></a><a class='icon icon-remove' href='javascript:void(0)'></a></div>").appendTo("body");
				$(".panel[id]").hover(function(){panelHelper.appendTo($(this)).show();},function(){panelHelper.hide();});
				
				$("#panelHelper .icon-remove").click(function(){
					$.post(ctx+"/widget/remove", {widgetId:$(this).parents(".panel").attr("id").replace("panel-","")},function(){
						location.reload();
					});
				});
				$("#panelHelper .icon-edit").click(function(){
					$(this).attr("href",ctx+"/widget/edit?widgetId="+$(this).parents(".panel").attr("id").replace("panel-",""));
				});
				
				//widget toolbar
				//admin toolbar
				var toolbar = "<ul id='toolbar'><li><a data-toggle='modal' href='"+ctx+"/page/create"+(currentPageId?"?pageId="+currentPageId:"")+"' data-target='#pageCreate'>添加页面</a></li>";
				if(currentPageId){
					toolbar+= ("<li><a data-toggle='modal' href='"+ctx+"/page/edit?id="+currentPageId+"' data-target='#pageEdit'>编辑本页</a></li>"+
								"<li><a data-toggle='modal' href='"+ctx+"/page/remove?id="+currentPageId+"'>删除本页</a></li>"+
								"<li><a data-toggle='modal' href='"+ctx+"/widget/add?pageId="+currentPageId+"' data-target='#widgetAdd'>添加模块</a></li>"+
								"<li><a data-toggle='modal' href='"+ctx+"/layout/change' data-target='#layoutChange'>修改布局</a></li>"+
								"<li><a data-toggle='modal' href='' data-target='#layoutExport'>导出布局</a></li>" +
								"<li><a data-toggle='modal' href='"+ctx+"/global/edit?pageId="+currentPageId+"' data-target='#globalEdit'>全站配置</a></li>");
				}
				toolbar+="</ul>";
				$(toolbar).appendTo("body").end().animate({"top":"30%"});
				SLYAK.modal({id:"pageCreate",header:"添加页面"});
				if(currentPageId){
					SLYAK.modal({id:"pageEdit",header:"编辑本页"});
					SLYAK.modal({id:"pageRemove",header:"删除本页"});
					SLYAK.modal({id:"widgetAdd",header:"添加模块",nofooter:true,onShown:function(){
						var btns = $("#widgetAdd .btn");
						btns.unbind("click");
						btns.on("click",function(){
							$.post(ctx+"/widget/add", {pageId:currentPageId,widgetName:$(this).attr("widgetName")},function(){
								location.reload();
							});
						});
					}});
					SLYAK.modal({id:"layoutChange",header:"修改布局",nofooter:true,onShown:function(){
						setTimeout(function(){
							$(".layout-demo").hover(
								  function () {
								    $(this).addClass("layout-demo-hover");
								  },
								  function () {
								    $(this).removeClass("layout-demo-hover");
								  }
							).on("click",function(){
								var layout =$(this).attr("layout");
								$.post(ctx+"/layout/change", {newLayout:layout,pageId:currentPageId},function(){
									location.reload();
								});
							});
						},500);
					}});
				}
				ace.require("ace/commands/default_commands").commands.push({
				    name: "Toggle Fullscreen",
				    bindKey: "F11",
				    exec: function(editor) {
				        $(document.body).toggleClass("fullScreen");
				        $(editor.container).toggleClass("fullScreen");
				        editor.resize();
				    }
				});
				SLYAK.modal({id:"globalEdit",header:"全局配置",onShown:function(){
					var globalCss = ace.edit("globalCss");
					globalCss.setTheme("ace/theme/dawn");
					globalCss.getSession().setMode("ace/mode/css");
					globalCss.getSession().setValue($('#golbalCssHolder').val());
					globalCss.getSession().addEventListener("change",function(){
						$('#golbalCssHolder').val(globalCss.getSession().getValue());
					});
					
					var globalJsp = ace.edit("globalJsp");
					globalJsp.setTheme("ace/theme/dawn");
					globalJsp.getSession().setMode("ace/mode/jsp");
					globalJsp.getSession().setValue($('#golbalJspHolder').val());
					globalJsp.getSession().addEventListener("change",function(){
						$('#golbalJspHolder').val(globalJsp.getSession().getValue());
					});
				}
				});
			}
		});
	});