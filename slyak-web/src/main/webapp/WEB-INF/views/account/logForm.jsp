<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">

<script type="text/javascript">
    $(document).ready(function() {
 	    $('.form_datetime').datetimepicker({
 	        language:  'zh-CN',
 	        weekStart: 1,
 	        todayBtn:  1,
 			autoclose: 1,
 			todayHighlight: 1,
 			startView: 2,
 			forceParse: 0
 	    });
    });
</script>

<div class="widget-box">	
	<div class="widget-title">
		<ul class="nav nav-tabs">
			<c:forEach items="${rootLogTypes}" var="rootLogType"
				varStatus="status">
				<li <c:if test="${(empty activeRootTypeId&&status.first) || (not empty activeRootTypeId&&activeRootTypeId==rootLogType.id)}">class="active"</c:if>><a
					href="#tab${status.index}" data-toggle="tab">${rootLogType.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="widget-content tab-content nopadding">
		<c:forEach items="${rootLogTypes}" var="rootLogType" varStatus="status">
			<div class="tab-pane <c:if test="${(empty activeRootTypeId&&status.first) || (not empty activeRootTypeId&&activeRootTypeId==rootLogType.id)}">active</c:if>" id="tab${status.index}">
				<form class="form-horizontal logform" action="${ctx}/account/addLog" method="post">
					<div class="control-group" style="border-top: 0px">
					    <label class="control-label" for="inputEmail">描述</label>
					    <div class="controls">
					    	<input type="text" class="description" name="title">
					    </div>
				  	</div>
				  	<div class="control-group">
					    <label class="control-label" for="inputEmail">数额</label>
					    <div class="controls">
						    <div class="input-prepend input-append">
							  <span class="add-on">¥</span>
							  <input class="span2" id="appendedPrependedInput" type="text" name="units">
							  <span class="add-on">.00</span>
							</div>
					    </div>
				  	</div>
				  	<div class="control-group">
		                <label class="control-label">时间</label>
		                <div class="controls">
		                	<div class="input-append date form_datetime" data-date="1979-09-16T05:25:07Z" data-date-format="yyyy/MM/dd" data-link-field="hiddenTime${status.index}">
			                    <input class="dateInput" type="text" value="" readonly="readonly">
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
							</div>
		                </div>
						<input type="hidden" id="hiddenTime${status.index}" name="happenTime" value="">
		            </div>
				  	
					<div class="control-group">
					    <label class="control-label" for="inputEmail">分类</label>
					    <div class="controls">
					    	<select class="span2"  name="accountLogType.id">
				    			<option value="${rootLogType.id}">未指定</option>
					    		<c:forEach items="${rootLogType.children}" var="clt">
					    			<option value="${clt.id}" <c:if test="${param.typeId == clt.id }">selected="selected"</c:if>>${clt.name}</option>
					    		</c:forEach>
					    	</select>
					    	<a class="btn btn-link" href="${ctx}/account/logTypes">设置</a>
					    </div>
				  	</div>
					<div class="control-group">
					    <label class="control-label" for="inputEmail">成员</label>
					    <div class="controls">
					    	<select class="span2" name="accountLogMember.id">
					    		<option value="">未指定</option>
					    		<c:forEach items="${logMembers}" var="lm">
					    			<option value="${lm.id}">${lm.name}</option>
					    		</c:forEach>
					    	</select>
					    	<a class="btn btn-link" href="${ctx}/account/logMembers">设置</a>
					    </div>
				  	</div>
				  	<div class="form-actions">
						<button class="btn btn-primary" type="submit">提交</button>
					</div>
				</form>
			</div>
		</c:forEach>
	</div>
</div>