package com.slyak.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slyak.biz.dao.BizDao;
import com.slyak.biz.model.Biz;
import com.slyak.biz.service.BizService;

@Service
public class BizServiceImpl implements BizService {
	
	@Autowired
	private BizDao bizDao;

	@Override
	public Biz findOne(String biz) {
		return bizDao.findOne(biz);
	}

	@Override
	public void save(Biz biz) {
		bizDao.save(biz);
	}
	
}
