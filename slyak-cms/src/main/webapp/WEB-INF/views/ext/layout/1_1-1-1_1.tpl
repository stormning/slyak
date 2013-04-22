<div class="row-fluid">
	<div class="column span12" id="row0">
		<#if containerMap['row0']?exists>
			<#list containerMap['row0'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
</div>
<div class="row-fluid">
	<div class="column span4" id="row1">
		<#if containerMap['row1']?exists>
			<#list containerMap['row1'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
	<div class="column span4" id="row2">
		<#if containerMap['row2']?exists>
			<#list containerMap['row2'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
	<div class="column span4" id="row3">
		<#if containerMap['row3']?exists>
			<#list containerMap['row3'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
</div>
<div class="row-fluid">
	<div class="column span12" id="row4">
		<#if containerMap['row4']?exists>
			<#list containerMap['row4'] as w>
				${w.content}
			</#list>
		</#if>
	</div>
</div>