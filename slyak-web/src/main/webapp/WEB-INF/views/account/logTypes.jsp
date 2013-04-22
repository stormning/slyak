<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<script>
	$(function($){
		$(".selectAll").on("click",function(e){
			$(this).parentsUntil("table").find(":checkbox").attr({"checked":"checked"}).triggerHandler("click");
		});
		
		$(".tab-content :checkbox").on("click",function(){
			var pt = $(this).parentsUntil("table");
			var checkedSize = pt.find(":checked").size();
			checkedSize>0?pt.find(".del").removeClass("disabled"):pt.find(".del").addClass("disabled");
			checkedSize==1?pt.find(".mod").removeClass("disabled"):pt.find(".mod").addClass("disabled");
		});
		
		var typeName='',typeId='';
		$(".mod").on("click",function(e){
			if($(this).hasClass("disabled")){
				e.stopPropagation();
			}else{
				//set value
				var chktomod = $(".tab-content :checked");
				typeName = 	chktomod.attr("tname");
				typeId  = chktomod.attr("tid");
			}
		});
		
		$(".add").on("click",function(e){
			typeName='';
			typeId='';
		});
		
		$(".del").on("click",function(e){
			if(!$(this).hasClass("disabled")){
				var deletedTypes = new Array();
				var chks = $(this).parentsUntil("table").find(":checked");
				$.each(chks,function(i,n){
					var t = new Object();
					t.id = $(n).attr("tid");
					deletedTypes.push(t);
				});
				$.ajax({
					url : "${ctx}/account/type/delete",
					data : JSON.stringify(deletedTypes),
					type : 'POST',
					contentType : "application/json",
					success : function(resp) {
						location.reload();
					}
				});
			}
		});
		
		function init(modal){
			var mf = $(modal).find("form");
			$(modal).find(".btn-primary").click(function(){
				mf.submit();
			});
			var parentId = $(".widget-title .nav-tabs .active").attr("rltid");
			mf.find("input[name='parent.id']").val(parentId);
			mf.find("input[name='id']").val(typeId);
			mf.find("input[name='name']").val(typeName);
		}
		SLYAK.modal({"id":"logTypesModal","header":"Add a logType",onShown:function(){init(this);}});
	});
</script>

<div class="widget-box">	
	<div class="widget-title">
		<ul class="nav nav-tabs">
			<c:forEach items="${rootLogTypes}" var="rootLogType"
				varStatus="status">
				<li <c:if test="${status.first}">class="active"</c:if> rltid="${rootLogType.id}"><a
					href="#tab${status.index}" data-toggle="tab">${rootLogType.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="widget-content tab-content nopadding">
		<c:forEach items="${rootLogTypes}" var="rootLogType" varStatus="status">
			<div class="tab-pane <c:if test="${status.first}">active</c:if>"
				id="tab${status.index}">
				<table class="table">
					<tr class="nomargin">
						<td colspan="2" style="border-top: 0px">
							<input type="button" class="btn btn-small selectAll" value="全选">
							<input type="button" class="btn btn-small add" data-toggle="modal" href="${ctx}/account/nameForm/logTypes" data-target="#logTypesModal" value="新增">
							<input type="button" class="btn btn-small disabled mod" data-toggle="modal" href="${ctx}/account/nameForm/logTypes" data-target="#logTypesModal" value="修改">
							<input type="button" class="btn btn-small disabled del" value="删除">
						</td>
					</tr>
					<c:forEach items="${rootLogType.children}" var="clt">
						<tr class="nomargin">
							<td style="width: 31px;text-align: center;"><input type="checkbox" id="lt_${clt.id}" autocomplete="off" tid="${clt.id}" tname="${clt.name}"></td>
							<td><label for="lt_${clt.id}">${clt.name}</label></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:forEach>
	</div>
</div>