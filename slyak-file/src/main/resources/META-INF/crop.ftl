<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
		<script src="${ctx}/fileResource/js/jquery-1.7.2.min.js"></script>
		<script src="${ctx}/fileResource/js/Jcrop/js/jquery.Jcrop.js"></script>
		<link rel="stylesheet" href="${ctx}/fileResource/js/Jcrop/css/jquery.Jcrop.css" type="text/css" />
		<style>
			.cropArea{
			   font-size : 12px;
			   padding:15px;
			}
			
			#preview-pane {
			   border: 1px rgba(0,0,0,.4) solid;
			   background-color: white;
			   margin-bottom:15px;
			}
			
			.crop-header #preview-pane,.crop-header p{
				float:left;
				margin-right:15px;
			}
			
			.crop-header p{
				margin:0px;
			}
			
			#preview-pane .preview-container {
			  overflow: hidden;
			}
			
			.tmpImageContainer{
				padding-top:10px;
				border-top:1px solid #ccc;
			}
		</style>
		<script>
			$(function($){
				var jcrop_api,
		        boundx,
		        boundy,
				$preview = $('#preview-pane'),
		        $pcnt = $('#preview-pane .preview-container'),
		        $pimg = $('#preview-pane .preview-container img'),
		
		        xsize = $pcnt.width(),
		        ysize = $pcnt.height();
			
				function updatePreview(c){
			      if (parseInt(c.w) > 0){
			        var rx = xsize / c.w;
			        var ry = ysize / c.h;
			        $pimg.css({
			          width: Math.round(rx * boundx) + 'px',
			          height: Math.round(ry * boundy) + 'px',
			          marginLeft: '-' + Math.round(rx * c.x) + 'px',
			          marginTop: '-' + Math.round(ry * c.y) + 'px'
			        });
			      }
			    };
			
				$("#tmpImage").Jcrop({
            		aspectRatio: ${aspectRatio},
            		minSize: [20,20],
            		boxWidth: 400,
            		boxHeight: 400,
            		onChange: updatePreview,
					onSelect: updatePreview
				},function(){
					jcrop_api = this;
					var width = $("#tmpImage").width();
					var height = $("#tmpImage").height();
					var w,h;
					if(width/height>${aspectRatio}){
						h = height;
						w = h*${aspectRatio};
					}else{
						w = width;
						h = w/${aspectRatio};
					}
					var x = (width-w)/2;
					var y = (height-h)/2;
					
					var bounds = this.getBounds();
				    boundx = bounds[0];
				    boundy = bounds[1];
				    
				    this.setSelect([x,y,w,h]);
				});
				
				$("#uploadTmp").on("change",function(){
					$("#uploadTmpForm").submit();
				});
				
			});
		</script>
	</head>
	<body class="cropArea">
		<form action="${ctx}/file/uploadTmp" method="POST" id="uploadTmpForm" enctype="multipart/form-data">
			<input type="hidden" name="biz" value="${biz}"/>
			<input type="hidden" name="owner" value="${owner}"/>
			<input type="file" id="uploadTmp" name="file"/>
		</form>
		<div>
			<div class="crop-header">
				<div id="preview-pane">
				    <div class="preview-container" style="width:90px;height:${90/aspectRatio}px">
				      <#if uploaded??>
					  	<img src="${ctx}/file/view/${biz}/${owner}/tmp" class="jcrop-preview" alt="Preview" />
					  <#else>
				      	<img src="${ctx}/file/view/${biz}/${owner}/${firtFileName}" style="width:90px;height:${90/aspectRatio}px" class="jcrop-preview" alt="Preview" />
					  </#if>
				    </div>
				</div>
				<p>请截取需要上传的图片区域</p>
				<div style="clear:both"></div>
			</div>
			
			<#if uploaded??&&!croped??>
				<div class="tmpImageContainer">
					<img src="${ctx}/file/view/${biz}/${owner}/tmp" id="tmpImage"/>
				</div>
			</#if>
		</div>
	</body>
</html>