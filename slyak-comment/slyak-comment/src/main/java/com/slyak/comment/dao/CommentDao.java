package com.slyak.comment.dao;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.comment.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

	@Query("from Comment where biz=?1 and owner=?2")
	List<Comment> listComments(Pageable pageable,String biz,String owner);

	@Query("from Comment where biz=?1 and owner=?2")
	Page<Comment> getComments(Pageable pageable,String biz,String owner);

	@Query("select owner from Comment where biz=?1")
	List<String> listBizOwners(String biz);

}
