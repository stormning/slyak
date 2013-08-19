package com.slyak.comment.dao;

import java.util.Date;
import java.util.List;

import com.slyak.comment.model.Comment;

public interface CommetSpecificDao {

	List<Comment> getLatest(List<String> owners, boolean onlyImg, Date start,
			Date end, int offset, int limit);

	List<Comment> getMostCommented(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

	List<Comment> getMostViewed(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

	List<Comment> getMostLiked(List<String> owners, boolean onlyImg,
			Date start, Date end, int offset, int limit);

}
