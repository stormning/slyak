package com.slyak.comment.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.comment.model.Comment;

public interface CommentService {

	void save(Comment comment,int fragmentSize,List<Long> tagIds);

	void remove(Long commentId);

	Comment findOne(Long commentId);

	List<Comment> listComments(int offset, int limit, String biz, String owner);

	Page<Comment> getComments(Pageable pageable, String biz, String owner);

	List<String> listBizOwners(String biz);

	void view(long commentId);

	List<Comment> getMostCommented(Date start, Date end, int fetchSize);
	
	List<Comment> getMostViewed(Date start, Date end, int fetchSize);
	
	List<Comment> getMostLiked(Date start, Date end, int fetchSize);
	
	List<Comment> getMostHot(Date start, Date end, int fetchSize);

	void asyncViewed();

	List<String> listActiveOwners(String bizKey, Date start, Date end,
			int fetchSize);
	
	List<Comment> randomListViewed(int fetchSize);

	void batchDeleteComments(List<Long> commentIds);

	void removeCommentsByBizAndOwner(String biz, String owner);

	Page<Comment> getComments(Pageable pageable, String biz,
			List<String> owners);

	Page<Comment> getComments(Pageable pageable, String biz,
			List<String> owners, String keyword);

	void changeOwner(String oldOwner, String newOwner);

	void changeOwner(Long commentId, String newOwner);

	Page<Comment> getCommentsWithImg(Pageable pageable, String biz,
			List<String> types);
}
