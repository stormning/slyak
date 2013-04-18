package com.slyak.comment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.comment.model.Comment;
import com.slyak.comment.model.CommentType;

public interface CommentService {

	void saveCommentType(CommentType commentType);
	
	List<CommentType> listCommentTypes(String owner);
	
	CommentType findCommentType(String owner,String type);
	
	void save(Comment comment);
	
	void remove(Long commentId);

	List<Comment> listByCommentTypeId(int fetchSize,Long commentTypeId);
	
	Page<Comment> pageByCommentTypeId(Pageable pageable,Long commentTypeId);
	
	Comment findOne(Long commentId);

	void removeCommentType(CommentType commentType);

	CommentType findCommentType(Long commentTypeId);
	
}
