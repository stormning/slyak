package com.slyak.biz.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.biz.model.Biz;

public interface BizDao extends JpaRepository<Biz, String> {

}
