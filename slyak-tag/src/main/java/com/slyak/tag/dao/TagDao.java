package com.slyak.tag.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.tag.model.Tag;

public interface TagDao extends JpaRepository<Tag, Long> {

	@Query("from Tag order by used")
	List<Tag> listTags(Pageable pageable);

}
