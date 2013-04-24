package com.slyak.comment.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.comment.model.Comment;

public interface CommentService {

	void save(Comment comment);
	
	void remove(Long commentId);

	Comment findOne(Long commentId);

	List<Comment> listComments(int fetchSize,String biz, String owner);
	
	Page<Comment> getComments(Pageable pageable,String biz, String owner);

	List<String> listBizOwners(String biz);
	
}
