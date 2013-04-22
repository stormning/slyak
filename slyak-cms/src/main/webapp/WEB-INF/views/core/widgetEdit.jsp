<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<form action="/widget/edit" method="post" class="form-horizontal">
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
			</select>
			<a href="">定义新模板</a>
		</div>			
	</div>
	<div id="borderClassSelector" class="control-group <c:if test="${'none.tpl' eq widget.borderTpl}">hide</c:if>">
		<label for="widgetBorderClasses" class="control-label">边框样式</label>
		<div class="controls">
			<div class="system-borders">
               <a href="javascript:void(0)" data-widget-setstyle="purple" class="purple-btn <c:if test="${'purple' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="navyblue" class="navyblue-btn <c:if test="${'navyblue' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="green" class="green-btn <c:if test="${'green' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="yellow" class="yellow-btn <c:if test="${'yellow' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="orange" class="orange-btn <c:if test="${'orange' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="pink" class="pink-btn <c:if test="${'pink' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="red" class="red-btn <c:if test="${'red' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="darkgrey" class="darkgrey-btn <c:if test="${empty widget.borderClass||'darkgrey' eq widget.borderClass}">btn-selected</c:if>"></a>
               <a href="javascript:void(0)" data-widget-setstyle="black" class="black-btn <c:if test="${'black' eq widget.borderClass}">btn-selected</c:if>"></a>
           </div>
		</div>
	</div>
		
	<c:if test="${not empty settings}">
	<div class="control-group">
		<label for="settings" class="control-label">参数配置</label>
		<div class="controls">
			<c:forEach items="${settings}" var="st">
				<div class="row-fluid settings">
					<div class="span3" key="${st.key}">
						${st.key}
					</div>
					<div class="span9">
						<c:choose>
							<c:when test="${'INPUT' eq st.inputType}">
								<input type="text" name="stvalue" value="${st.value}"/>
							</c:when>
							<c:when test="${'TEXTAREA' eq st.inputType}">
								<textarea name="stvalue">${mergedSettings[st.key]}</textarea>
							</c:when>
							<c:when test="${'SELECT' eq st.inputType}">
								<select name="stvalue">
									<c:forEach items="${st.options}" var="op">
										<option value="${op}" <c:if test="${op eq mergedSettings[st.key]}">selected="selected"</c:if>>${op}</option>
									</c:forEach>
								</select>
							</c:when>
							<c:when test="${'RADIO' eq st.inputType}">
								<c:forEach items="${st.options}" var="op">
									${op}:<input type="radio" name="stvalue" value="${op}" <c:if test="${op eq mergedSettings[st.key]}">checked="checked"</c:if>/>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${st.options}" var="op">
									${op}:<input type="checkbox" name="stvalue" value="${op}" <c:if test="${op eq mergedSettings[st.key]}">checked="checked"</c:if>/>
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