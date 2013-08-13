package com.slyak.cms.widgets.news;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.slyak.comment.dao.CommentDao;
import com.slyak.comment.model.Comment;
import com.slyak.comment.util.Constants;
import com.slyak.config.model.ConfigPK;
import com.slyak.config.service.ConfigService;
import com.slyak.core.io.StaticResourceMappingManager;
import com.slyak.core.io.image.CommonImage;
import com.slyak.core.io.image.ImgConfig;
import com.slyak.core.io.image.ImgSize;
import com.slyak.core.util.JsonUtils;
import com.slyak.core.util.StringUtils;
import com.slyak.event.EventComsumer;

@Component
public class CommentSaveComsumer implements EventComsumer {

	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private StaticResourceMappingManager resourceMappingManager;
	
	@Value("#{config['base.url']}")
	private String baseUrl;
	
	@Autowired
	private ConfigService configService;
	

	@Override
	public void resolve(String json) throws Exception {
		Comment comment = JsonUtils.toType(json, Comment.class);
		String content = comment.getContent();
		Set<String> imgSrcs = StringUtils.findImgSrcs(content);
		int index = 0;
		if(!org.springframework.util.CollectionUtils.isEmpty(imgSrcs)){
			for (String src : imgSrcs) {
				String fullPathSrc = new String(src);
				//from editor
				if(org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "//")){
					fullPathSrc = "http:"+src;
				}
				else if(org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "/")){
					fullPathSrc =  baseUrl+src;
				}else if (!org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "http")){
					fullPathSrc = "http://"+src;
				}
				
				CommonImage ci = null;
				try{
					ci = new CommonImage(new URI(fullPathSrc));
				} catch (Exception e){
					//ignore
				}
				if(ci!=null){
					String realPath = resourceMappingManager.getRealPathByBizAndOwner(com.slyak.cms.widgets.news.Constants.BIZ_NEWSIMG, String.valueOf(comment.getId()));
					ImgConfig imgConfig = configService.findData(new ConfigPK(com.slyak.cms.widgets.news.Constants.BIZ_NEWSTYPE, comment.getOwner()), ImgConfig.class);
					String directory = realPath+File.separator+index;
					FileUtils.forceMkdir(new File(directory));
					
					if(imgConfig == null){
						ci.save(directory+File.separator+"orginal.jpg");
					} else{
						ci.resizeWithContainer(imgConfig.getMaxWidth(), imgConfig.getMaxHeight()).save(directory+File.separator+"orginal.jpg");
						List<ImgSize> imgSizes = imgConfig.getImgSizes();
						if(!CollectionUtils.isEmpty(imgSizes)){
							int sizeIndex=0;
							for (ImgSize imgSize : imgSizes) {
								if(imgSize.getWidth()!=null&&imgSize.getHeight()!=null){
									ci.cropWithContainer(imgSize.getWidth(), imgSize.getHeight()).save(directory+File.separator+sizeIndex+".jpg");
									sizeIndex++;
								}
							}
						}
					}
					String httpPath = resourceMappingManager.getHttpPathByBizAndOwner(com.slyak.cms.widgets.news.Constants.BIZ_NEWSIMG, String.valueOf(comment.getId()));
					String orginalhttpPath = httpPath+"/"+index+"/orginal.jpg?ver="+comment.getVer();
					content = org.apache.commons.lang.StringUtils.replace(content, src, orginalhttpPath);
				}
				index++;
			}
			comment.setContent(content);
			comment.setImgCount(index);
			commentDao.save(comment);
		}
	}

	@Override
	public boolean supports(String topic) {
		return topic.equals(Constants.EventTopic.COMMENT_SAVE);
	}
	
	public static void main(String[] args) {
//		<script(\s[^>]*?)?>[\s\S]*?</script>
//		System.out.println(JavaScriptUtils.javaScriptEscape("<div>hello</div>"));;
		String sourceString = "<div >.good{}</div>";
		sourceString = sourceString.replaceAll("<style[^>]*?>[\\s\\S]*?</style>|<script[^>]*?>[\\s\\S]*?</script>|<.+?>", "");
//		sourceString = sourceString.replaceAll("<style[^>]*?>[\\s\\S]*?</style>","");
//		sourceString = sourceString.replaceAll("<script[^>]*?>[\\s\\S]*?</script>", "");
		System.out.println(sourceString);
//		System.out.println("<style>.good{}</style>".replaceAll("<.+?>", ""));
//		new CommonImage(new URI("http://any123.com/assets/pdt/img/ad-competition.jpg"))
	}
}
