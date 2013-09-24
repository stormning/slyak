package com.slyak.standard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.standard.model.Location;

public interface LocationDAO extends JpaRepository<Location, Long>{

	List<Location> findByPidAndPinyinStartingWith(long pid, String pinyin);

	List<Location> findByPid(long pid);

}
