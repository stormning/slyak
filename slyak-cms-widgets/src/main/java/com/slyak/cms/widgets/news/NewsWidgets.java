package com.slyak.cms.widgets.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;

import com.slyak.biz.model.Biz;
import com.slyak.biz.service.BizService;
import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.enums.InputType;
import com.slyak.comment.model.Comment;
import com.slyak.comment.service.CommentService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

@Widgets("news")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class NewsWidgets {
	
	private static final String BIZ="news";

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BizService bizService;
	
	private static final StringTemplateLoader loader = new StringTemplateLoader();
	
	private static final Configuration configuration = new Configuration();
	
	static{
		configuration.setTemplateLoader(loader);
	}

	@Widget(settings = {
			@Setting(key = "fetchSize", value = "10"),
			@Setting(key = "template",value = "list1.tpl",options={"list1.tpl","list2.tpl"}),
			@Setting(key = "diy",value ="",inputType = InputType.TEXTAREA ,desCode="news.list.diy")
			},onAdd="addType",onEdit="updateType",onRemove="removeType")
	public Object list(com.slyak.cms.core.model.Widget widget,ModelMap modelMap) throws IOException {
		Map<String,String> settings = widget.getSettings();
		String type = widget.getId().toString();
		modelMap.put("comments", commentService.listComments(NumberUtils.parseNumber(settings.get("fetchSize"), Integer.class), BIZ, settings.get("type")));
		String diy = settings.get("diy");
		if(StringUtils.isNotBlank(diy)){
			Object tempsource = loader.findTemplateSource(type);
			if(tempsource == null){
				loader.putTemplate(type, diy);
			}
			return configuration.getTemplate(diy);
		} else {
			return settings.get("template");
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
		commentService.save(comment);
		modelMap.put("newsType", comment.getOwner());
	}
	
	public void addType(com.slyak.cms.core.model.Widget widget) throws JsonGenerationException, JsonMappingException, IOException{
		Biz biz = bizService.findOne(BIZ);
		ObjectMapper om = new ObjectMapper();
		List<NewsType> nts = null;
		if(biz==null){
			nts = new ArrayList<NewsType>();
			biz = new Biz();
			biz.setBiz(BIZ);
		}else{
			nts = om.readValue(biz.getData(), List.class);
		}
		NewsType nt = new NewsType();
		nt.setName(StringUtils.isNotBlank(widget.getTitle())?widget.getTitle():widget.getId().toString());
		nt.setId(widget.getId());
		nts.add(nt);
		biz.setData(om.writeValueAsString(nts));
		bizService.save(biz);
	}
	
	public void updateType(com.slyak.cms.core.model.Widget widget) throws JsonParseException, JsonMappingException, IOException{
		Biz biz = bizService.findOne(BIZ);
		ObjectMapper om = new ObjectMapper();
		List<Map> nts = om.readValue(biz.getData(), List.class);
		for (Map nt : nts) {
			if(((Number)nt.get("id")).longValue() == widget.getId().longValue()){
				nt.put("name", StringUtils.isNotBlank(widget.getTitle())?widget.getTitle():widget.getId().toString());
			}
		}
		biz.setData(om.writeValueAsString(nts));
		bizService.save(biz);
	}
	
	public void removeType(com.slyak.cms.core.model.Widget widget){
		//TODO think it over
	}
	
	@Widget(settings = {@Setting(key = "pageSize", value = "10")})
	public String manager(Integer page,Integer newsType,com.slyak.cms.core.model.Widget widget,ModelMap modelMap) throws JsonParseException, JsonMappingException, IOException {
		Map<String,String> settings = widget.getSettings();
		Biz biz = bizService.findOne(BIZ);
		if(biz!=null){
			ObjectMapper om = new ObjectMapper();
			List<Map> newsTypes = om.readValue(biz.getData(), List.class);
			modelMap.put("newsTypes",newsTypes);
			if(page==null){
				page =1;
			}
			if(newsType==null){
				newsType = (Integer)newsTypes.get(0).get("id");
			}
			modelMap.put("newsType", newsType);
			PageRequest pageRequest = new PageRequest(page-1, NumberUtils.parseNumber(settings.get("pageSize"), Integer.class));
			modelMap.put("page", commentService.getComments(pageRequest, BIZ, newsType.toString()));
		}
		return "manager.tpl";
	}
	
	public void removeNews(Long newsId){
		commentService.remove(newsId);
	}
	
	public class NewsType{
		private String name;
		private Long id;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
	}
}
