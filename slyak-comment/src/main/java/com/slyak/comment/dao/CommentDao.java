package com.slyak.comment.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.comment.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

	List<Comment> findByCommentTypeId(Pageable pageable, Long commentTypeId);

	@Query("select c from Comment c where c.commentType.id=?1")
	Page<Comment> pageByCommentTypeId(Pageable pageable, Long commentTypeId);

}
