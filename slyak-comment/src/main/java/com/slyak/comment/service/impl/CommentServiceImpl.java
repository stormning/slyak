package com.slyak.comment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.slyak.comment.dao.CommentDao;
import com.slyak.comment.dao.CommentTypeDao;
import com.slyak.comment.model.Comment;
import com.slyak.comment.model.CommentType;
import com.slyak.comment.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private CommentTypeDao commentTypeDao;

	@Override
	public void save(Comment comment) {
		if (comment.getId() == null) {
			comment.setCreateAt(new Date());
			commentDao.save(comment);
		} else {
			Comment exist = commentDao.findOne(comment.getId());
			if(exist!=null){
				exist.setContent(comment.getContent());
				exist.setTitle(comment.getTitle());
				exist.setModifier(comment.getModifier());
				exist.setModifyAt(new Date());
				commentDao.save(exist);
			}
		}
	}

	@Override
	public List<Comment> listByCommentTypeId(int fetchSize,Long commentTypeId) {
		PageRequest request = new PageRequest(0, fetchSize);
		return commentDao.findByCommentTypeId(request,commentTypeId);
	}

	@Override
	public Page<Comment> pageByCommentTypeId(Pageable pageable,Long commentTypeId) {
		return commentDao.pageByCommentTypeId(pageable, commentTypeId);
	}

	@Override
	public void remove(Long commentId) {
		commentDao.delete(commentId);
	}

	@Override
	public Comment findOne(Long commentId) {
		return commentDao.findOne(commentId);
	}

	@Override
	public void saveCommentType(CommentType commentType) {
		CommentType exist = findCommentType(commentType.getOwner(), commentType.getType());
		if(exist==null){
			commentTypeDao.save(commentType);
		}else{
			exist.setDecription(commentType.getDecription());
			commentTypeDao.save(exist);
		}
	}

	@Override
	public List<CommentType> listCommentTypes(String owner) {
		return commentTypeDao.findByOwner(owner);
	}

	@Override
	public CommentType findCommentType(String owner, String type) {
		return commentTypeDao.findByOwnerAndType(owner,type);
	}

	@Override
	public void removeCommentType(CommentType commentType) {
		commentTypeDao.delete(commentType);
	}

	@Override
	public CommentType findCommentType(Long commentTypeId) {
		return commentTypeDao.findOne(commentTypeId);
	}
}
