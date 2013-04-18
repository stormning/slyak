package com.slyak.cms.widgets.news;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.model.Settings;
import com.slyak.comment.model.Comment;
import com.slyak.comment.model.CommentType;
import com.slyak.comment.service.CommentService;

@Widgets("news")
public class NewsWidgets {

	@Autowired
	private CommentService commentService;

	@Widget(settings = {
			@Setting(key = "type", value = ""),
			@Setting(key = "style", value = ""),
			@Setting(key = "fetchSize", value = "10"),
			@Setting(key = "template",value = "list.tpl")
			},onEdit="addType",onRemove="removeType")
	public String list(Settings settings,ModelMap modelMap) {
		CommentType commentType = commentService.findCommentType("news", settings.get("type"));
		if(commentType!=null){
			modelMap.put("comments", commentService.listByCommentTypeId(NumberUtils
					.parseNumber(settings.get("fetchSize"), Integer.class),commentType.getId()));
		}
		modelMap.put("style", settings.get("style"));
		
		String template = settings.get("template");
		if(StringUtils.isBlank(template)){
			return "list.tpl";
		}else{
			return template;
		}
	}
	
	@Widget(show=false)
	public String detail(Long newsId,ModelMap modelMap) {
		if (newsId != null) {
			modelMap.put("comment", commentService.findOne(newsId));
		}
		return "detail.tpl";
	}
	
	public void addNews(Comment comment,ModelMap modelMap){
		Long commentTypeId = comment.getCommentType().getId();
		CommentType ct = commentService.findCommentType(commentTypeId);
		comment.setCommentType(ct);
		commentService.save(comment);
		modelMap.put("newsType", ct.getType());
	}
	
	public void addType(com.slyak.cms.core.model.Widget widget){
		Map<String, String> settings = widget.getSettings();
		if(!CollectionUtils.isEmpty(settings)){
			String type = settings.get("type");
			CommentType commentType = new CommentType();
			commentType.setOwner("news");
			commentType.setType(type);
			commentType.setDecription(widget.getTitle());
			commentService.saveCommentType(commentType);
		}
	}
	
	public void removeType(Settings settings){
		String type = settings.get("type");
		CommentType commentType = commentService.findCommentType("news", type);
		commentService.removeCommentType(commentType);
	}
	
	@Widget(settings = {@Setting(key = "pageSize", value = "10")})
	public String manager(Integer page,String newsType,Settings settings,ModelMap modelMap) {
		List<CommentType> newsTypes = commentService.listCommentTypes("news");
		if(!CollectionUtils.isEmpty(newsTypes)){
			modelMap.put("newsTypes", newsTypes);
			if(page==null){
				page =1;
			}
			CommentType nt;
			if(newsType==null){
				nt = newsTypes.get(0);
			}else{
				nt = commentService.findCommentType("news",newsType);;
			}
			modelMap.put("newsType", nt);
			PageRequest pageRequest = new PageRequest(page-1, NumberUtils.parseNumber(settings.get("pageSize"), Integer.class));
			modelMap.put("page", commentService.pageByCommentTypeId(pageRequest, nt.getId()));
		}
		return "manager.tpl";
	}
	
	public void removeNews(Long newsId){
		commentService.remove(newsId);
	}
}
