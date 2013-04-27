package com.slyak.biz.service;

import com.slyak.biz.model.Biz;

public interface BizService {

	Biz findOne(String biz);
	
	void save(Biz biz);

}
