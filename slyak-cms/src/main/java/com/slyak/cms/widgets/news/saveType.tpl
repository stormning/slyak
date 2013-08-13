<form action="${action}/news/saveType" method="post" class="form-horizontal">
	<input type="hidden" name="group.id" value="${id!!}">
	<input type="hidden" name="group.pid" value="${pid?default('0')}">
	<div class="control-group">
		<label class="control-label">名称</label>
		<div class="controls">
			<input type="text" class="input-xlarge" name="group.name" value="${name!!}">
		</div>
	</div>
	<div class="control-group">	
		<label class="control-label">图片上传时缩放</label>
		<div class="controls">
			宽：<input type="text" class="input span1" name="imgConfig.maxWidth" value="${(imgConfig.maxWidth)!800}"> 高：<input type="text" class="input span1" name="imgConfig.maxHeight" value="${(imgConfig.maxHeight)!600}">
		</div>
	</div>
	<div class="control-group resizeList">	
		<label class="control-label">缩略图尺寸列表</label>
		<#if (imgConfig.imgSizes)?? && (imgConfig.imgSizes?size gt 0)>
			<#assign index=0>
			<#assign total=imgConfig.imgSizes?size>
			<#list imgConfig.imgSizes as sz>
				<div class="controls form-inline">
					宽：<input type="text" class="input span1" name="imgConfig.imgSizes[${index}].width" value="${sz.width!!}"> 高：<input type="text" class="input span1" name="imgConfig.imgSizes[${index}].height" value="${sz.height!!}">
				</div>
				<#assign index=index+1>
			</#list>
			<#else>
			<div class="controls form-inline">
				宽：<input type="text" class="input span1" name="imgConfig.imgSizes[0].width" value=""> 高：<input type="text" class="input span1" name="imgConfig.imgSizes[0].height" value="">
			</div>
		</#if>
	</div>
</form>	