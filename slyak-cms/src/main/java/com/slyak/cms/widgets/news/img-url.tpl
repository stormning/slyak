<#macro splitId idstr><#if idstr?length-1 gte offset+step><#assign tmp = tmp+'/'+idstr?substring(offset,step)><#assign offset=offset+step><@splitId idstr=idstr/><#else><#assign tmp = tmp+'/'+idstr?substring(offset)>${tmp}</#if></#macro>