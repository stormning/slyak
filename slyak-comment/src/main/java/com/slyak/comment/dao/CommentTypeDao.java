package com.slyak.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.comment.model.CommentType;

public interface CommentTypeDao extends JpaRepository<CommentType, Long> {

	List<CommentType> findByOwner(String owner);

	CommentType findByOwnerAndType(String owner, String type);

}
