package com.slyak.cms.widgets.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.slyak.cms.core.annotation.NameAndValue;
import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.enums.InputType;
import com.slyak.cms.core.service.CmsService;
import com.slyak.cms.core.support.Option;
import com.slyak.comment.model.Comment;
import com.slyak.comment.service.CommentService;
import com.slyak.config.model.ConfigPK;
import com.slyak.config.service.ConfigService;
import com.slyak.core.io.image.ImgConfig;
import com.slyak.core.util.JsonUtils;
import com.slyak.core.web.OffsetLimitRequest;
import com.slyak.group.model.Group;
import com.slyak.group.service.GroupService;

@Widgets("news")
public class NewsWidgets {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private CmsService cmsService;

	private static final Map<String, String> VIEW_TEMPLATE_MAP = new HashMap<String, String>();

	static {
		VIEW_TEMPLATE_MAP.put("listNormal", "list-normal.tpl");
		VIEW_TEMPLATE_MAP.put("listNormalFirstImportant",
				"list-normal-first-important.tpl");
		VIEW_TEMPLATE_MAP.put("listImages", "list-images.tpl");
		VIEW_TEMPLATE_MAP.put("tabNormal", "tab-normal.tpl");
		VIEW_TEMPLATE_MAP.put("tabImages", "tab-images.tpl");

		VIEW_TEMPLATE_MAP.put("paginationNormal", "pagination-normal.tpl");
		VIEW_TEMPLATE_MAP.put("paginationTable", "pagination-table.tpl");
		VIEW_TEMPLATE_MAP.put("paginationBigimg", "pagination-bigimg.tpl");
		VIEW_TEMPLATE_MAP.put("paginationLeftimg", "pagination-leftimg.tpl");
		VIEW_TEMPLATE_MAP.put("paginationImgs", "pagination-imgs.tpl");
	}

	@Widget(settings = {
			@Setting(key = "offset", value = "0", name = "偏移量"),
			@Setting(key = "limit", value = "10", name = "显示个数"),
			@Setting(key = "showType", value = "true", name = "是否显示类型"),
			@Setting(key = "imgSizeType", name = "图片缩放类型(在管理端进行相应配置)", options = {
					@NameAndValue(name = "第一个缩放类型", value = "0"),
					@NameAndValue(name = "第二个缩放类型", value = "1"),
					@NameAndValue(name = "第三个缩放类型", value = "2"),
					@NameAndValue(name = "第四个缩放类型", value = "3") }),
			@Setting(key = "logic", value = "0", name = "逻辑", options = {
					@NameAndValue(name = "最新", value = "0"),
					@NameAndValue(name = "最多查看", value = "1"),
					@NameAndValue(name = "最多回复", value = "2"),
					@NameAndValue(name = "最多喜欢", value = "3"),
					@NameAndValue(name = "别人正在看", value = "4") }),
			@Setting(key = "types", value = "[]", name = "类型", optionsLoader = "findLeafTypes", inputType = InputType.CHECKBOX),
			@Setting(key = "sizePerLine", value = "4", name = "每行显示几个", options = {
					@NameAndValue(value = "1", name = "每行1个"),
					@NameAndValue(value = "2", name = "每行2个"),
					@NameAndValue(value = "3", name = "每行3个"),
					@NameAndValue(value = "4", name = "每行4个"),
					@NameAndValue(value = "6", name = "每行6个"),
					@NameAndValue(value = "12", name = "每行12个") }),
			@Setting(key = "view", value = "listNormal", name = "视图展现形式", options = {
					@NameAndValue(name = "默认标题列表", value = "listNormal"),
					@NameAndValue(name = "标题列表(第一个突出显示)", value = "listNormalFirstImportant"),
					@NameAndValue(name = "图片列表(仅显示分类下拥有图片的文章)", value = "listImages"),
					@NameAndValue(name = "可切换的tab页标题列表", value = "tabNormal"),
					@NameAndValue(name = "可切换的tab页图片列表(仅显示分类下拥有图片的文章)", value = "tabImages") }) })
	public Object list(com.slyak.cms.core.model.Widget widget, ModelMap modelMap)
			throws IOException {
		Map<String, String> settings = widget.getSettings();
		String typesStr = settings.get("types");
		List<String> types = null;
		if (!StringUtils.isEmpty(typesStr)) {
			types = JsonUtils.toType(typesStr, List.class);
		}
		if (CollectionUtils.isEmpty(types)) {
			types = Collections.singletonList("0");
		}
		String view = settings.get("view");

		List<Comment> comments = new ArrayList<Comment>();

		Pageable pageable = new OffsetLimitRequest(NumberUtils.parseNumber(
				settings.get("offset"), Integer.class),
				NumberUtils.parseNumber(settings.get("limit"), Integer.class));
		if ("listImages".equals(view)) {
			comments.addAll(commentService.getCommentsWithImg(pageable,
					Constants.CPK_NEWS.getBiz(), types).getContent());
		} else if ("tabImages".equals(view)) {
			Map<String, List<Comment>> commentMap = new HashMap<String, List<Comment>>();
			for (String type : types) {
				List<Comment> cs = commentService.getCommentsWithImg(pageable,
						Constants.CPK_NEWS.getBiz(),
						Collections.singletonList(type)).getContent();
				commentMap.put(type, cs);
				comments.addAll(cs);
			}
			modelMap.put("commentsMap", commentMap);
		} else {
			comments.addAll(commentService.getComments(pageable,
					Constants.CPK_NEWS.getBiz(), types).getContent());
		}
		modelMap.put("comments", comments);
		initTypeAndDetailMap(comments,
				"true".equalsIgnoreCase(settings.get("showType")), modelMap);
		String tpl = VIEW_TEMPLATE_MAP.get(settings.get("view"));
		return tpl == null ? "list-normal.tpl" : tpl;
	}

	private void initTypeAndDetailMap(List<Comment> comments, boolean showType,
			ModelMap map) {
		if (!CollectionUtils.isEmpty(comments)) {
			Set<Long> groupIds = new HashSet<Long>();
			for (Comment c : comments) {
				groupIds.add(Long.valueOf(c.getOwner()));
			}
			if (!CollectionUtils.isEmpty(groupIds)) {
				List<Group> groups = groupService
						.findByIdIn(new ArrayList<Long>(groupIds));
				Map<String, TypeAndPage> tpmap = new HashMap<String, TypeAndPage>();
				for (Group group : groups) {
					TypeAndPage tap = new TypeAndPage();
					tap.setType(group);
					if (showType) {
						List<com.slyak.cms.core.model.Widget> widgets = cmsService
								.findWidgetsByNameAndAttribute(
										"news.pagination", "type",
										String.valueOf(group.getId()));
						if (!CollectionUtils.isEmpty(widgets)) {
							tap.setPage(widgets.get(0).getPage());
						}
					}
					List<com.slyak.cms.core.model.Widget> detailWidgets = cmsService
							.findWidgetsByNameAndAttribute("news.detail",
									"type", String.valueOf(group.getId()));
					if (!CollectionUtils.isEmpty(detailWidgets)) {
						tap.setDetailPage(detailWidgets.get(0).getPage());
					}
					tpmap.put(String.valueOf(group.getId()), tap);
				}
				map.put("types", tpmap);
			}
		}

	}

	@Widget(settings = {
			@Setting(key = "type", value = "0", name = "类型", optionsLoader = "findLeafTypes", inputType = InputType.SELECT),
			@Setting(key = "pageSize", value = "20", name = "每页显示多少个"),
			@Setting(key = "view", value = "paginationNormal", name = "视图展现形式", options = {
					@NameAndValue(name = "默认分页列表", value = "paginationNormal"),
					@NameAndValue(name = "TABLE分页列表", value = "paginationTable"),
					@NameAndValue(name = "大图加概述", value = "paginationBigimg"),
					@NameAndValue(name = "左小图加概述", value = "paginationLeftimg"),
					@NameAndValue(name = "图片列表", value = "paginationImgs") }) })
	public String pagination(com.slyak.cms.core.model.Widget widget,
			Integer page, ModelMap modelMap) {
		Map<String, String> settings = widget.getSettings();
		String owner = settings.get("type");
		if (StringUtils.isNotBlank(owner)) {
			if (page == null) {
				page = 1;
			}
			PageRequest pageRequest = new PageRequest(page - 1,
					NumberUtils.parseNumber(settings.get("pageSize"),
							Integer.class));
			Page<Comment> cp = commentService.getComments(pageRequest,
					Constants.CPK_NEWS.getBiz(), owner);
			modelMap.put("page", cp);
			initTypeAndDetailMap(cp.getContent(), false, modelMap);
		}
		String tpl = VIEW_TEMPLATE_MAP.get(settings.get("view"));
		return tpl == null ? "pagination-normal.tpl" : tpl;
	}

	public Option[] findLeafTypes() {
		List<Group> groups = groupService.findAllLeaves(
				Constants.CPK_NEWS.getBiz(), Constants.CPK_NEWS.getOwner());
		if (CollectionUtils.isEmpty(groups)) {
			return null;
		} else {
			List<Option> opts = new ArrayList<Option>();
			for (Group group : groups) {
				Option opt = new Option();
				opt.setName(group.getName());
				opt.setValue(String.valueOf(group.getId()));
				opts.add(opt);
			}
			return opts.toArray(new Option[opts.size()]);
		}
	}

	@Widget(show = false, settings = { @Setting(key = "type", value = "0", name = "类型", optionsLoader = "findLeafTypes", inputType = InputType.SELECT) })
	public String detail(Long newsId, ModelMap modelMap) {
		if (newsId != null) {
			Comment comment = commentService.findOne(newsId);
			modelMap.put("newsId", newsId);
			if (comment != null) {
				commentService.view(newsId);
				modelMap.put("comment", commentService.findOne(newsId));
			}
		}
		return "detail.tpl";
	}

	public void addNews(Comment comment, ModelMap modelMap) {
		commentService.save(comment, 200, null);
		modelMap.put("newsType", comment.getOwner());
	}

	@Transactional
	public Group saveType(NewsType newsType, ModelMap modelMap) {
		Group group = newsType.getGroup();
		ImgConfig imgConfig = newsType.getImgConfig();
		boolean create = group.getId() == null;
		group.setBiz(Constants.CPK_NEWS.getBiz());
		group.setOwner(Constants.CPK_NEWS.getOwner());
		groupService.save(group);
		ConfigPK imgCpk = new ConfigPK(Constants.BIZ_NEWSTYPE,
				String.valueOf(group.getId()));
		configService.save(imgCpk, imgConfig);

		if (create && group.getPid() != null
				&& groupService.findOne(group.getPid()) != null) {
			commentService.changeOwner(String.valueOf(group.getPid()),
					String.valueOf(group.getId()));
		}

		return group;
	}

	public List<Group> loadChildren(Long pid) {
		return groupService.getChildren(pid);
	}

	public Comment getNews(Long id) {
		return commentService.findOne(id);
	}

	public String saveTypeView(Long id, ModelMap modelMap) {
		if (id != null) {
			Group group = groupService.findOne(id);
			modelMap.put("id", group.getId());
			modelMap.put("pid", group.getPid());
			modelMap.put("name", group.getName());
			ConfigPK imgCpk = new ConfigPK(Constants.BIZ_NEWSTYPE,
					String.valueOf(group.getId()));
			ImgConfig imgConfig = configService.findData(imgCpk,
					ImgConfig.class);
			modelMap.put("imgConfig", imgConfig);
		}
		return "saveType.tpl";
	}

	@Transactional
	public void removeType(Long id) {
		Group g = groupService.findOne(id);
		if (g != null) {
			groupService.delete(id);
			commentService.changeOwner(String.valueOf(id),
					String.valueOf(g.getPid()));
		}
	}

	public void changeType(Long id, String owner) {
		commentService.changeOwner(id, owner);
	}

	public void deleteNews(Long[] newsIds) {
		commentService.batchDeleteComments(Arrays.asList(newsIds));
	}

	@Widget(settings = { @Setting(key = "pageSize", value = "10", name = "每页显示个数") })
	public String manager(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "0") Long newsType,
			@RequestParam(defaultValue = "") String keyword,
			com.slyak.cms.core.model.Widget widget, ModelMap modelMap) {
		List<Group> allGroups = groupService.findAll();
		List<String> searchTypes = new ArrayList<String>();
		if (CollectionUtils.isEmpty(allGroups)) {
			newsType = 0L;
		} else {
			modelMap.put("allGroups", JsonUtils.toJSON(allGroups));
			List<Group> leafGroups = null;
			Group currentGroup = groupService.findOne(newsType);
			if (currentGroup == null) {
				newsType = 0L;
				leafGroups = groupService.findAllLeaves(
						Constants.CPK_NEWS.getBiz(),
						Constants.CPK_NEWS.getOwner());
			} else {
				if (currentGroup.isLeaf()) {
					leafGroups = Collections.singletonList(currentGroup);
				} else {
					leafGroups = groupService
							.findChildLeavesByPath(currentGroup.getPath());
				}
			}
			if (!CollectionUtils.isEmpty(leafGroups)) {
				modelMap.put("leafGroups", leafGroups);
				searchTypes = new ArrayList<String>();
				for (Group lg : leafGroups) {
					searchTypes.add(String.valueOf(lg.getId()));
				}
			}
		}
		if (newsType == 0) {
			searchTypes.add("0");
		}
		Map<String, String> settings = widget.getSettings();
		PageRequest pageRequest = new PageRequest(
				page - 1,
				NumberUtils.parseNumber(settings.get("pageSize"), Integer.class));
		modelMap.put(
				"page",
				commentService.getComments(pageRequest,
						Constants.CPK_NEWS.getBiz(), searchTypes, keyword));
		modelMap.put("newsType", newsType);
		modelMap.put("keyword", keyword);
		return "manager.tpl";
	}
}
