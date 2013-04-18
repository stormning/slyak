<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<style>
	#container{
		min-height: 840px;
	}
</style>

<script type="text/javascript">
$(function () {
    var chart;
    var _series_original = ${series};
    var _categories = ${categories};
    var _series = new Array();
    var _single = false;
    <c:choose>
    	<c:when test="${empty averages}">
    		_single = true;
    	</c:when>
    	<c:otherwise>
    	 var _averages = ${averages};
    	    if(_averages!=null&&_averages.length<=12){
    	   		_series.push({
    	   			type: 'spline',
    	   			name: 'Average',
    	   			data: _averages,
    	   			marker: {
    	               	lineWidth: 2,
    	               	lineColor: Highcharts.getOptions().colors[3],
    	               	fillColor: 'white'
    	               }
    	   		});
    	    }
    	</c:otherwise>
    </c:choose>
   
    if(_series_original!=null){
    	var _colum_type = (_series_original[0].data.length>12||_single)?'spline':'column';
    	for(var i=0;i<_series_original.length;i++){
	    	_series.push({
	            type: _colum_type,
	            name: _series_original[i].name == '_slyak_default_'?'未指定':_series_original[i].name,
	            data: _series_original[i].data,
	            marker: {
                    enabled: false
                }
	        });
    	}
    }
    
/*     var _pie = ${pie};
    if(_pie!=null){
    	var _pie_data = new Array();
    	for(var i=0;i<_pie.length;i++){
    		_pie_data.push({
	            name: _pie[i].name,
	            y: _pie[i].y
	        });
    	}
    	_series.push({
    		type: 'pie',
            name: 'Total consumption',
            data: _pie_data,
            center: [100, 80],
            size: 100,
            showInLegend: false,
            dataLabels: {
                enabled: false
            }
        });
    } */
    
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container'
            },
            title: {
                text: '<fmt:formatDate value="<%=new Date() %>" pattern="yyyy年M月"/>${checked.name}统计图'
            },
            xAxis: {
            	//time region
                categories: _categories
            },
            yAxis: {
            	min:0
            },
            tooltip: {
                formatter: function() {
                    var s;
                    if (this.point.name) { // the pie chart
                        s = ''+
                            this.point.name +': '+ this.y +' fruits';
                    } else {
                        s = ''+
                            this.x  +': '+ this.y;
                    }
                    return s;
                }
            },
            /* labels: {
                items: [{
                    html: 'Total fruit consumption',
                    style: {
                        left: '40px',
                        top: '8px',
                        color: 'black'
                    }
                }]
            }, */
            series: _series
        });
    });
    
});
</script>

<script src="${ctx}/static/thirdparty/highcharts/highcharts.js"></script>
<script src="${ctx}/static/thirdparty/highcharts/modules/exporting.js"></script>

<div class="widget-box">
	<div class="widget-title">
		<ul class="nav nav-tabs">
			<!-- root -->
			<c:forEach items="${rootTypes}" var="rootType">
				<li
					<c:if test="${rootType.id == checked.id}">class="active"</c:if>>
					<a href="${ctx}/account/logReport/${rootType.id}">${rootType.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="widget-content tab-content">
		<div class="tab-pane active" id="container">
			&nbsp;
		</div>
		<div>
			<table>
				<c:forEach items="${reports}" var="report">
					<tr><td></td></tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>