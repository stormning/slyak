package com.slyak.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.model.Forum;

public interface ForumDao extends JpaRepository<Forum, Long> {

}
