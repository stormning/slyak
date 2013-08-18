<%@page import="org.springframework.util.StringUtils"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.slyak.core.util.JsonUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<form action="${ctx}/widget/edit" method="post" class="form-horizontal">
	<div class="control-group">
		<label for="widgetId" class="control-label">编号</label>
		<div class="controls">
			<input type="text" id="widgetId" class="input-xlarge" name="id" value="${widget.id}" disabled="disabled">
		</div>
	</div>
	<div class="control-group">
		<label for="widgetTitle" class="control-label">标题</label>
		<div class="controls">
			<input type="text" id="widgetTitle" class="input-xlarge" name="title" value="${widget.title}">
		</div>
	</div>
	
	<div class="control-group">
		<label for="widgetborderClassClass" class="control-label">边框模板</label>
		<div class="controls">
			<select name="border">
				<option value="none.tpl" <c:if test="${'none.tpl' eq widget.borderTpl}">selected="selected"</c:if>>无边框</option>
				<option value="t-c-f.tpl" <c:if test="${'t-c-f.tpl' eq widget.borderTpl}">selected="selected"</c:if>>系统边框</option>
				<option value="c-f.tpl" <c:if test="${'c-f.tpl' eq widget.borderTpl}">selected="selected"</c:if>>系统边框(无标题)</option>
			</select>
			<a href="">定义新模板</a>
		</div>			
	</div>
	<div id="borderClassSelector" class="control-group <c:if test="${'none.tpl' eq widget.borderTpl}">hide</c:if>">
		<label for="widgetBorderClasses" class="control-label">边框样式</label>
		<div class="controls">
			<div class="system-borders">
			   <a href="javascript:void(0)" data-widget-setstyle="panel-primary" class="panel-primary <c:if test="${'panel-primary' eq widget.borderClass}">btn-selected</c:if>"><span class="panel-heading"></span></a>	
               <a href="javascript:void(0)" data-widget-setstyle="panel-success" class="panel-success <c:if test="${'panel-success' eq widget.borderClass}">btn-selected</c:if>"><span class="panel-heading"></span></a>
               <a href="javascript:void(0)" data-widget-setstyle="panel-warning" class="panel-warning <c:if test="${'panel-warning' eq widget.borderClass}">btn-selected</c:if>"><span class="panel-heading"></span></a>
               <a href="javascript:void(0)" data-widget-setstyle="panel-danger" class="panel-danger <c:if test="${'panel-danger' eq widget.borderClass}">btn-selected</c:if>"><span class="panel-heading"></span></a>
               <a href="javascript:void(0)" data-widget-setstyle="panel-info" class="panel-info  <c:if test="${'panel-info ' eq widget.borderClass}">btn-selected</c:if>"><span class="panel-heading"></span></a>
           </div>
		</div>
	</div>
		
	<c:if test="${not empty settings}">
	<div class="control-group">
		<label for="settings" class="control-label">参数配置</label>
		<div class="controls">
			<c:forEach items="${settings}" var="st">
				<div class="row-fluid settings" inputType="${st.inputType}">
					<div class="span3" key="${st.key}">
						${st.key}
					</div>
					<div class="span9">
						<c:choose>
							<c:when test="${'INPUT' eq st.inputType}">
								<input type="text" name="stvalue" value="${mergedSettings[st.key]}"/>
							</c:when>
							<c:when test="${'TEXTAREA' eq st.inputType}">
								<textarea name="stvalue">${mergedSettings[st.key]}</textarea>
							</c:when>
							<c:when test="${'SELECT' eq st.inputType}">
								<select name="stvalue">
									<option value="">未设置</option>
									<c:forEach items="${st.options}" var="op">
										<option value="${op.value}" <c:if test="${op.value eq mergedSettings[st.key]}">selected="selected"</c:if>>${op.name}</option>
									</c:forEach>
								</select>
							</c:when>
							<c:when test="${'RADIO' eq st.inputType}">
								<c:forEach items="${st.options}" var="op">
									${op.name}:<input type="radio" name="stvalue" value="${op.value}" <c:if test="${op.value eq mergedSettings[st.key]}">checked="checked"</c:if>/>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:set var="chkvalues" value="${mergedSettings[st.key]}" scope="request"/>
								<%
									List<String> chks = new ArrayList<String>();
									String chkvalues = (String)request.getAttribute("chkvalues");
									if(StringUtils.hasText(chkvalues)){
										chks = JsonUtils.toType(chkvalues, List.class);
									}
								%>
								<c:forEach items="${st.options}" var="op">
									<c:set var="opvalue" value="${op.value}" scope="request"/>
									<%
										String opvalue = (String)request.getAttribute("opvalue");
									%>
									${op.name}:<input type="checkbox" name="stvalue" value="${op.value}" <%if(chks.contains(opvalue)){ %>checked="checked"<%}%>/>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</div>			
			</c:forEach>
		</div>
	</div>
	</c:if>
</form>