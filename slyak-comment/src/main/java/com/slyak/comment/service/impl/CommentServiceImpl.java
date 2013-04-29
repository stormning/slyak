package com.slyak.comment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.slyak.comment.dao.CommentDao;
import com.slyak.comment.model.Comment;
import com.slyak.comment.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
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
	public void remove(Long commentId) {
		commentDao.delete(commentId);
	}

	@Override
	public Comment findOne(Long commentId) {
		return commentDao.findOne(commentId);
	}


	@Override
	public List<Comment> listComments(int fetchSize, String biz, String owner) {
		Pageable pageable = new PageRequest(0, fetchSize);
		return commentDao.listComments(pageable, biz, owner);
	}


	@Override
	public Page<Comment> getComments(Pageable pageable, String biz, String owner) {
		return commentDao.getComments(pageable, biz, owner);
	}


	@Override
	public List<String> listBizOwners(String biz) {
		return commentDao.listBizOwners(biz);
	}



}
