<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
		<script src="${ctx}/fileResource/js/jquery-1.7.2.min.js"></script>
		<script src="${ctx}/fileResource/js/Jcrop/js/jquery.Jcrop.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/fileResource/js/Jcrop/css/jquery.Jcrop.css" type="text/css" />
		<script src="${ctx}/fileResource/js/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/fileResource/js/uploadify/uploadify.css">
		<style>
			.cropArea{
			   font-size : 12px;
			   padding:15px;
			   margin:0px;
			}
			
			#preview-pane {
			   border: 1px #ccc solid;
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
			
			.crop-footer{
				background-color:#f0f0f0;
				padding:10px;
				border-top:1px solid #ccc;
			}
			
			#preview-pane .preview-container {
			  overflow: hidden;
			}
			
			.tmpImageContainer{
				padding-top:10px;
				border-top:1px solid #ccc;
			}
			
			.uploadify-button{
				border:1px solid #ddd;
				background-color:#eee;
				text-align:center
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
				
				$('#uploadTmp').uploadify({
					'formData':{
						'biz': '${biz}',
						'owner': '${owner}'
					},
					'fileObjName':'file',
					'multi': false,
					'fileTypeExts' : '*.gif; *.jpg; *.png',
					'swf':'${ctx}/fileResource/js/uploadify/uploadify.swf',
					'uploader' : '${ctx}/file/uploadTmp',
					'buttonText':'选择图片',
					'width':90,
					'height':26,
					'fileSizeLimit' : '30MB',
					'onUploadSuccess':function(){
						if(location.href.indexOf('uploaded')==-1){
							location.href = location.href+'&uploaded=true';
						} else {
							location.reload();
						}
					}
				});
				
				$('#submitCrop').on('click',function(){
					var selected = jcrop_api.tellSelect();
					var nh = Math.floor(selected.h);
					var nw = Math.floor(nh*${aspectRatio});
					$.post("${ctx}/file/crop",{
						biz : '${biz}',
						owner:'${owner}',
						left:Math.floor(selected.x),
						top:Math.floor(selected.y),
						width:nw,
						height:nh
					},function(res){
						//do after crop
						if(res.success){
							if(document.parent){
								if(document.parent){
									if(document.parent.onCropSuccess){
										document.parent.onCropSuccess();
									} else {
										document.parent.location.reload();
									}
								}
							} else {
								location.reload();
							}
						}
					});
				});
				
			});
		</script>
	</head>
	<body class="cropArea">
		<div>
			<form>
				<div id="queue"></div>
				<input type="file" id="uploadTmp" name="file"/>
			</form>
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
			
			<#if uploaded??>
				<div class="tmpImageContainer">
					<img src="${ctx}/file/view/${biz}/${owner}/tmp" id="tmpImage"/>
				</div>
				<div class="crop-footer">
					<input type="submit" value="确认" id="submitCrop">
					<a href="javascript:void(0)" id="cancelCrop">取消</a>
				</div>
			</#if>
		</div>
	</body>
</html>