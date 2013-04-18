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
		
		var memberName='',memberId='';
		$(".mod").on("click",function(e){
			if($(this).hasClass("disabled")){
				e.stopPropagation();
			}else{
				//set value
				var chktomod = $(".tab-content :checked");
				memberName = 	chktomod.attr("mname");
				memberId  = chktomod.attr("mid");
			}
		});
		
		$(".add").on("click",function(e){
			memberName='';
			memberId='';
		});
		
		$(".del").on("click",function(e){
			if(!$(this).hasClass("disabled")){
				var deletedMembers = new Array();
				var chks = $(this).parentsUntil("table").find(":checked");
				$.each(chks,function(i,n){
					var t = new Object();
					t.id = $(n).attr("mid");
					deletedMembers.push(t);
				});
				$.ajax({
					url : "${ctx}/account/member/delete",
					data : JSON.stringify(deletedMembers),
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
			mf.find("input[name='id']").val(memberId);
			mf.find("input[name='name']").val(memberName);
		}
		SLYAK.modal({"id":"logMembersModal","header":"Add a logMember",onShown:function(){init(this);}});
	});
</script>

<div class="widget-box">
	<div class="widget-title">
		<ul class="nav nav-tabs">
			<li class="active"><a>所有成员</a></li>
		</ul>
	</div>
	<div class="widget-content tab-content nopadding">
		<div class="tab-pane active">
			<table class="table">
				<tr class="nomargin">
					<td colspan="2" style="border-top: 0px">
							<input type="button" class="btn btn-small selectAll" value="全选">
							<input type="button" class="btn btn-small add" data-toggle="modal" href="${ctx}/account/nameForm/logMembers" data-target="#logMembersModal" value="新增">
							<input type="button" class="btn btn-small disabled mod" data-toggle="modal" href="${ctx}/account/nameForm/logMembers" data-target="#logMembersModal" value="修改">
							<input type="button" class="btn btn-small disabled del" value="删除">
						</td>	
				</tr>
				<c:choose>
					<c:when test="${empty logMembers}">
						<tr class="nomargin">
							<td colspan="2">您还没有添加过成员,请点击新增按钮添加</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${logMembers}" var="lm">
							<tr class="nomargin">
								<td style="width: 31px; text-align: center;"><input
									type="checkbox" id="lm_${lm.id}" mname="${lm.name}" mid="${lm.id}" autocomplete="off"></td>
								<td><label for="lm_${lm.id}">${lm.name}</label></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
</div>