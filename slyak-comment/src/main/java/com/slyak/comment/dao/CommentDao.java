package com.slyak.comment.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.slyak.comment.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

	List<Comment> findByBizAndOwner(Pageable pageable, String biz, String owner);

	@Query("from Comment where biz=?1")
	List<Comment> listByBiz(Pageable pageable, String biz);

	@Query("select distinct(owner) from Comment where biz=?1")
	List<String> listBizOwners(String biz);

	@Query("select c from Comment c where c.biz=?1 and c.owner=?2 order by createAt desc")
	Page<Comment> getCommentsPage(Pageable pageable, String biz, String owner);

	@Query("select c from Comment c where c.biz=?1 and c.owner in ?2 order by createAt desc")
	Page<Comment> getCommentsPage(Pageable pageable, String biz,
			List<String> owners);

	@Query("select c from Comment c where c.biz=?1 and c.owner in ?2 and c.imgCount>0 order by createAt desc")
	Page<Comment> getCommentsPageWithImg(Pageable pageable, String biz,
			List<String> owners);

	@Query("select c from Comment c where c.biz=?1 and c.owner in ?2 and (c.title like ?3 or c.content like ?3) order by createAt desc")
	Page<Comment> getCommentsPageByKeyword(Pageable pageable, String biz,
			List<String> owners, String keyword);

	@Query("from Comment order by createAt desc")
	List<Comment> findAllOrderByCreateAt(Pageable pageable);

	@Query("delete from Comment c where c.id in ?1")
	@Modifying
	void deleteComments(List<Long> commentIds);

	@Query("delete from Comment where biz=?1 and owner=?2")
	@Modifying
	void removeCommentsByBizAndOwner(String biz, String owner);

	@Query("update Comment set owner=?2 where owner=?1")
	@Modifying
	void changeOwner(String oldOwner, String newOwner);

	@Query("update Comment set owner=?2 where id=?1")
	@Modifying
	void changeCommentOwner(Long commentId, String newOwner);

	List<Comment> findByRefererOrderByIdDesc(Pageable pagebale, Long commentId);
}
