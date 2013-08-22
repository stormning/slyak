package com.slyak.cms.widgets.news;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.slyak.core.util.DateUtils;
import com.slyak.core.util.JsonUtils;
import com.slyak.group.model.Group;
import com.slyak.group.service.GroupService;

@Widgets("news")
@SuppressWarnings("unchecked")
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
		VIEW_TEMPLATE_MAP.put("listImagesWaterwall",
				"list-images-waterwall.tpl");
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
			@Setting(key = "imgSizeType", name = "图片显示哪种缩放类型(在管理端进行相应配置)", options = {
					@NameAndValue(name = "第一个缩放类型", value = "0"),
					@NameAndValue(name = "第二个缩放类型", value = "1"),
					@NameAndValue(name = "第三个缩放类型", value = "2"),
					@NameAndValue(name = "第四个缩放类型", value = "3") }),
			@Setting(key = "newsIds", name = "逗号分隔的编号列表", inputType = InputType.TEXTAREA),
			@Setting(key = "dateRegion", name = "时间范围", inputType = InputType.SELECT, options = {
					@NameAndValue(name = "24小时内", value = "1"),
					@NameAndValue(name = "一周内", value = "7"),
					@NameAndValue(name = "一月内", value = "30"),
					@NameAndValue(name = "一季度内", value = "90"),
					@NameAndValue(name = "一年内", value = "365") }),
			@Setting(key = "logic", value = "0", name = "逻辑", options = {
					@NameAndValue(name = "最新", value = "0"),
					@NameAndValue(name = "最多查看", value = "1"),
					@NameAndValue(name = "最多回复", value = "2"),
					@NameAndValue(name = "最多喜欢", value = "3"),
					@NameAndValue(name = "别人正在看", value = "4") }, inputType = InputType.SELECT),
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
					@NameAndValue(name = "图片瀑布流列表(仅显示分类下拥有图片的文章)", value = "listImagesWaterwall"),
					@NameAndValue(name = "图片TAB页(仅显示分类下拥有图片的文章)", value = "tabImages"),
					@NameAndValue(name = "普通TAB页", value = "tabNormal") }),
			@Setting(key = "commentSize", name = "评论数", value = "10", inputType = InputType.INPUT) })
	public Object list(com.slyak.cms.core.model.Widget widget, ModelMap modelMap)
			throws IOException {

		Map<String, String> settings = widget.getSettings();

		String view = settings.get("view");
		List<Comment> comments = null;
		List<String> types = null;

		String newsIdsStr = org.springframework.util.StringUtils
				.trimAllWhitespace(settings.get("newsIds"));
		if (StringUtils.isEmpty(newsIdsStr)) {

			String typesStr = settings.get("types");

			if (!StringUtils.isEmpty(typesStr)) {
				types = JsonUtils.toType(typesStr, List.class);
			}

			int offset = NumberUtils.parseNumber(settings.get("offset"),
					Integer.class);
			int limit = NumberUtils.parseNumber(settings.get("limit"),
					Integer.class);

			Date start = null;
			Date end = null;
			String drs = settings.get("dateRegion");
			if (!StringUtils.isBlank(drs)) {
				end = new Date();
				int ammount = -Integer.parseInt(drs);
				start = DateUtils.addDays(end, ammount);
			}

			int logic = Integer.parseInt(settings.get("logic"));

			boolean tabView = "tabImages".equals(view)
					|| "tabNormal".equals(view);
			boolean onlyImg = "listImages".equals(view)
					|| "tabImages".equals(view);

			if (tabView) {
				Map<String, List<Comment>> commentsMap = new HashMap<String, List<Comment>>();
				for (String type : types) {
					commentsMap.put(
							type,
							getCommentsByLogic(logic, types, onlyImg, start,
									end, offset, limit));
				}
				modelMap.put("commentsMap", commentsMap);
			} else {
				comments = getCommentsByLogic(logic, types, onlyImg, start,
						end, offset, limit);
				modelMap.put("comments", comments);
			}
		} else {
			types = new ArrayList<String>();
			comments = new ArrayList<Comment>();
			String[] strNewsIds = StringUtils.split(newsIdsStr, ',');
			for (String strNewsId : strNewsIds) {
				Long newsId = NumberUtils.parseNumber(strNewsId, Long.class);
				Comment c = commentService.findOne(newsId);
				if (c != null) {
					comments.add(c);
					types.add(c.getOwner());
				}
			}
			modelMap.put("comments", comments);
		}

		initTypeAndDetailMap(types,
				"true".equalsIgnoreCase(settings.get("showType")), modelMap);
		String tpl = VIEW_TEMPLATE_MAP.get(settings.get("view"));
		return tpl == null ? "list-normal.tpl" : tpl;
	}

	private List<Comment> getCommentsByLogic(int logic, List<String> types,
			boolean onlyImg, Date start, Date end, int offset, int limit) {
		List<Comment> comments = null;
		switch (logic) {
		// 最新
		case 0:

			comments = commentService.getLatest(types, onlyImg, start, end,
					offset, limit);
			break;
		// 最多查看
		case 1:
			comments = commentService.getMostViewed(types, onlyImg, start, end,
					offset, limit);
			break;
		// 最多回复
		case 2:
			comments = commentService.getMostCommented(types, onlyImg, start,
					end, offset, limit);
			break;
		// 最多喜欢
		case 3:
			comments = commentService.getMostLiked(types, onlyImg, start, end,
					offset, limit);
			break;
		// 别人正在看
		case 4:
			commentService.randomListViewed(types, onlyImg, limit);
			break;
		default:
			break;
		}
		return comments;
	}

	private void initTypeAndDetailMap(List<String> owners, boolean showType,
			ModelMap map) {
		if (!CollectionUtils.isEmpty(owners)) {
			List<Group> groups = new ArrayList<Group>();
			Set<String> distinct = new HashSet<String>(owners);
			Map<String, TypeAndPage> tpmap = new HashMap<String, TypeAndPage>();
			for (String gidStr : distinct) {
				Long gid = Long.valueOf(gidStr);
				Group group = groupService.findOne(gid);
				groups.add(group);

				TypeAndPage tap = new TypeAndPage();
				tap.setType(group);
				if (showType) {
					List<com.slyak.cms.core.model.Widget> widgets = cmsService
							.findWidgetsByNameAndAttribute("news.pagination",
									"type", String.valueOf(group.getId()));
					if (!CollectionUtils.isEmpty(widgets)) {
						com.slyak.cms.core.model.Widget w = widgets.get(0);
						tap.setPage(cmsService.findPageById(w.getPageId()));
					}
				}
				List<com.slyak.cms.core.model.Widget> detailWidgets = cmsService
						.findWidgetsByNameAndAttribute("news.detail", "type",
								String.valueOf(group.getId()));
				if (!CollectionUtils.isEmpty(detailWidgets)) {
					com.slyak.cms.core.model.Widget dw = detailWidgets.get(0);
					tap.setDetailPage(cmsService.findPageById(dw.getPageId()));
				}
				tpmap.put(String.valueOf(group.getId()), tap);
			}
			map.put("types", tpmap);
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
			initTypeAndDetailMap(Collections.singletonList(owner), false,
					modelMap);
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

	@Widget(show = false, settings = {
			@Setting(key = "type", value = "0", name = "类型", optionsLoader = "findLeafTypes", inputType = InputType.SELECT),
			@Setting(key = "commentSize", name = "评论数", value = "10", inputType = InputType.INPUT) })
	public String detail(com.slyak.cms.core.model.Widget widget, Long newsId,
			ModelMap modelMap) {
		if (newsId != null) {
			Comment comment = commentService.findOne(newsId);
			modelMap.put("newsId", newsId);
			if (comment != null) {
				commentService.view(newsId);
				modelMap.put("comment", comment);
				modelMap.put("children", commentService.getCommentsByReferer(
						newsId, 0, NumberUtils.parseNumber(widget.getSettings()
								.get("commentSize"), Integer.class)));
			}
		}
		return "detail.tpl";
	}
	
	public List<Comment> moreComment(Long referer,int offset,int limit){
		return commentService.getCommentsByReferer(referer, offset, offset);
	}

	//admin
	public Comment addNews(Comment comment, ModelMap modelMap) {
		commentService.save(comment, 200, null);
		modelMap.put("newsType", comment.getOwner());
		return comment;
	}
	
	public Comment addComment(Comment comment) {
		comment.setContent(com.slyak.core.util.StringUtils.cleanHtml(comment.getContent()));
		commentService.save(comment, 200, null);
		return comment;
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

	public static void main(String[] args) {
		File file = new File(
				"E:\\cloudstore\\backup\\smart-cms\\2013-08-19\\upload");
		loop(file);
	}

	public static void loop(File f) {/*
									 * if (f.isDirectory()) { File[] fs =
									 * f.listFiles(); for (File file : fs) {
									 * loop(file); } } else { String baseName =
									 * FilenameUtils.getBaseName(f.getName());
									 * if (baseName.equals("orginal")) { //
									 * f.renameTo(new
									 * File(f.getAbsolutePath().replace("0.jpg",
									 * // "c0.jpg"))); //
									 * System.out.println(f.getAbsolutePath
									 * ().replace("0.jpg", // "c0.jpg")); String
									 * abp = f.getAbsolutePath(); try { new
									 * CommonImage
									 * (f).resizeWithMaxWidth(120).save(
									 * abp.replace("orginal.jpg", "z0.jpg")); ;
									 * } catch (IOException e) {
									 * e.printStackTrace(); } //
									 * ci.resizeWithMaxWidth(maxWidth).; } }
									 */
	}
}
