<div class="row-fluid">
	<div class="column span12" id="row0">
		<#if containerMap['row0']?exists>
			<#list containerMap['row0'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
</div>