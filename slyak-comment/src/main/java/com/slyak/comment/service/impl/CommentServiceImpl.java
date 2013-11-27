/**
 * Project name : slyak-comment
 * File name : CommentServiceImpl.java
 * Package name : com.slyak.comment.service.impl
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.comment.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.slyak.comment.dao.CommentDao;
import com.slyak.comment.dao.CommetSpecificDao;
import com.slyak.comment.model.Comment;
import com.slyak.comment.service.CommentService;
import com.slyak.comment.util.Constants;
import com.slyak.core.util.JsonUtils;
import com.slyak.core.util.StringUtils;
import com.slyak.core.web.OffsetLimitRequest;
import com.slyak.event.EventPublisher;
import com.slyak.tag.model.Tag;
import com.slyak.tag.service.TagService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private CommetSpecificDao specificDao;

	@Autowired
	private TagService tagService;

	@Autowired
	private EventPublisher eventPublisher;

	private Map<Long, ViewDetail> hotCommentViewed = new ConcurrentHashMap<Long, ViewDetail>();

	@Override
	public void save(Comment comment, int fragmentSize, List<Long> tagIds) {
		boolean hasTagIds = !CollectionUtils.isEmpty(tagIds);
		Comment commentToSave = null;
		if (comment.getId() == null) {
			comment.setCreateAt(new Date());
			long referer = comment.getReferer();
			if (referer > 0) {
				Comment parent = commentDao.findOne(referer);
				comment.setLevel(parent.getLevel() + 1);
				parent.setCommented(parent.getCommented() + 1);
				commentDao.save(parent);
			}
			commentToSave = comment;
			if (hasTagIds) {
				increaceAll(tagIds, comment);
			}
		} else {
			Comment exist = commentDao.findOne(comment.getId());
			if (exist != null) {
				exist.setContent(comment.getContent());
				exist.setTitle(comment.getTitle());
				exist.setModifier(comment.getModifier());
				exist.setModifyAt(new Date());
				exist.setOwner(comment.getOwner());
				exist.setVer(exist.getVer() + 1);

				String tidStr = exist.getTagIds();
				String[] tids = StringUtils.split(tidStr, "|");
				if (hasTagIds) {
					if (tids == null) {
						increaceAll(tagIds, exist);
					} else {
						// TODO
						// reduce not exist
						// increase new
					}
				}
				commentToSave = exist;
			}
		}
		
		if(fragmentSize>0){
		commentToSave.setFragment(StringUtils.cut(commentToSave.getContent(),
				fragmentSize));
		}
		commentDao.save(commentToSave);
		// event
		eventPublisher.publish(Constants.EventTopic.COMMENT_SAVE,
				JsonUtils.toJSON(commentToSave));
	}

	private void increaceAll(List<Long> tagIds, Comment saved) {
		Collections.sort(tagIds);
		StringBuffer tagIdsBuffer = new StringBuffer();
		tagIdsBuffer.append('|');
		for (Long tid : tagIds) {
			Tag t = tagService.findOne(tid);
			t.setUsed(t.getUsed() + 1);
			tagIdsBuffer.append(tid).append('|');
		}
		saved.setTagIds(tagIdsBuffer.toString());
	}

	@Override
	public void remove(Long commentId) {
		commentDao.delete(commentId);
	}

	@Override
	public Comment findOne(Long commentId) {
		Comment comment = commentDao.findOne(commentId);
		resetComment(comment);
		return comment;
	}

	@Override
	public List<Comment> listComments(int offset, int limit, String biz,
			String owner) {
		Pageable pageable = new OffsetLimitRequest(offset, limit);
		List<Comment> comments = null;
		if (owner == null) {
			if (biz == null) {
				comments = commentDao.findAllOrderByCreateAt(pageable);
			} else {
				comments = commentDao.listByBiz(pageable, biz);
			}
		} else {
			// when owner is not null, biz can't be null
			if (biz == null) {
				throw new IllegalArgumentException(
						"when owner is not null, biz can't be null");
			} else {
				comments = commentDao.findByBizAndOwner(pageable, biz, owner);
			}
		}
		resetComments(comments);
		return comments;
	}

	@Override
	public Page<Comment> getComments(Pageable pageable, String biz, String owner) {
		Page<Comment> commentPage = commentDao.getCommentsPage(pageable, biz,
				owner);
		resetComments(commentPage.getContent());
		return commentPage;
	}

	@Override
	public List<String> listBizOwners(String biz) {
		return commentDao.listBizOwners(biz);
	}

	@Override
	public void view(long commentId) {
		Comment comment = commentDao.findOne(commentId);
		if (comment != null) {
			ViewDetail vd = hotCommentViewed.get(commentId);
			long current = new Date().getTime();
			if (vd == null) {
				hotCommentViewed.put(commentId,
						new ViewDetail(comment.getViewed(), current));
			} else {
				vd.setCount(vd.getCount() + 1);
				vd.setLastViewed(current);
			}
		}
	}

	@Override
	@Scheduled(fixedDelay = /* 300000 */10000, initialDelay = 10000)
	public void asyncViewed() {
		for (Long commentId : hotCommentViewed.keySet()) {
			if (commentId != null) {
				ViewDetail vd = hotCommentViewed.get(commentId);
				if (vd != null) {
					Comment comment = commentDao.findOne(commentId);
					if (comment != null) {
						comment.setViewed(vd.getCount());
						commentDao.save(comment);
//						hotCommentViewed.remove(commentId);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param comment
	 */
	private void resetComment(Comment comment) {
		if (comment != null) {
			Long commentId = comment.getId();
			ViewDetail vd = hotCommentViewed.get(commentId);
			if (vd != null) {
				comment.setViewed(vd.getCount());
			}
		}
	}

	private void resetComments(List<Comment> comments) {
		if (!CollectionUtils.isEmpty(comments)) {
			for (Comment comment : comments) {
				resetComment(comment);
			}
		}
	}

	class ViewDetail implements Serializable {
		private static final long serialVersionUID = 1L;
		private long count;
		private long lastViewed;

		public ViewDetail(long count, long lastViewed) {
			this.count = count;
			this.lastViewed = lastViewed;
		}

		public long getCount() {
			return count;
		}

		public void setCount(long count) {
			this.count = count;
		}

		public long getLastViewed() {
			return lastViewed;
		}

		public void setLastViewed(long lastViewed) {
			this.lastViewed = lastViewed;
		}
	}

	@Override
	public List<Comment> getLatest(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		List<Comment> comments = specificDao.getLatest(owners, onlyImg, start,
				end, offset, limit);
		resetComments(comments);
		return comments;
	}

	@Override
	public List<Comment> getMostCommented(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		List<Comment> comments = specificDao.getMostCommented(owners, onlyImg,
				start, end, offset, limit);
		resetComments(comments);
		return comments;
	}

	@Override
	public List<Comment> getMostViewed(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		List<Comment> comments = specificDao.getMostViewed(owners, onlyImg,
				start, end, offset, limit);
		resetComments(comments);
		return comments;
	}

	@Override
	public List<Comment> getMostLiked(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit) {
		List<Comment> comments = specificDao.getMostLiked(owners, onlyImg,
				start, end, offset, limit);
		resetComments(comments);
		return comments;
	}

	@Override
	public List<Comment> getMostHot(Date start, Date end, int offset, int limit) {
		//
		return null;
	}

	@Override
	public List<String> listActiveOwners(String bizKey, Date start, Date end,
			int fetchSize) {
		return null;
	}

	@Override
	public List<Comment> randomListViewed(List<String> owners, boolean onlyImg,
			int fetchSize) {
		if (hotCommentViewed.isEmpty()) {
			return Collections.emptyList();
		} else {
			Set<Long> cids = hotCommentViewed.keySet();
			List<Long> cidList = new ArrayList<Long>(cids);
			int total = cids.size();
			fetchSize = fetchSize > total ? total : fetchSize;
			Set<Integer> indexes = new HashSet<Integer>();
			List<Comment> comments = new ArrayList<Comment>(fetchSize);
			while (indexes.size() < fetchSize) {
				int index = (int) (Math.random() * total);
				if (!indexes.contains(index)) {
					indexes.add(index);
					// 利用缓存
					comments.add(findOne(cidList.get(index)));
				}
			}
			return comments;
		}
	}

	@Override
	public void batchDeleteComments(List<Long> commentIds) {
		commentDao.deleteComments(commentIds);
	}

	@Override
	public void removeCommentsByBizAndOwner(String biz, String owner) {
		commentDao.removeCommentsByBizAndOwner(biz, owner);
	}

	@Override
	public Page<Comment> getComments(Pageable pageable, String biz,
			List<String> owners) {
		Page<Comment> commentPage = commentDao.getCommentsPage(pageable, biz,
				owners);
		resetComments(commentPage.getContent());
		return commentPage;
	}

	@Override
	public Page<Comment> getCommentsWithImg(Pageable pageable, String biz,
			List<String> owners) {
		Page<Comment> commentPage = commentDao.getCommentsPageWithImg(pageable,
				biz, owners);
		resetComments(commentPage.getContent());
		return commentPage;
	}

	@Override
	public Page<Comment> getComments(Pageable pageable, String biz,
			List<String> owners, String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return getComments(pageable, biz, owners);
		} else {
			Page<Comment> commentPage = commentDao.getCommentsPageByKeyword(
					pageable, biz, owners, "%" + keyword + "%");
			resetComments(commentPage.getContent());
			return commentPage;
		}
	}

	@Override
	public void changeOwner(String oldOwner, String newOwner) {
		commentDao.changeOwner(oldOwner, newOwner);
	}

	@Override
	public void changeOwner(Long commentId, String newOwner) {
		commentDao.changeCommentOwner(commentId, newOwner);
	}

	@Override
	public List<Comment> getCommentsByReferer(Long commentId, int offset, int limit) {
		Pageable pagebale = new OffsetLimitRequest(offset, limit);
		return commentDao.findByRefererOrderByIdDesc(pagebale,commentId);
	}

}
