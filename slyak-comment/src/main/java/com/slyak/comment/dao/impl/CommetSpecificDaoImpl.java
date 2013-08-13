package com.slyak.comment.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.slyak.comment.dao.CommetSpecificDao;

@Repository
public class CommetSpecificDaoImpl implements CommetSpecificDao {

	@PersistenceContext
	private EntityManager em;
	
	
}
