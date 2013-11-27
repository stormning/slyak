/**
 * Project name : slyak-web
 * File name : SpecificDao.java
 * Package name : com.slyak.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.dao;

import java.util.Date;
import java.util.List;

import com.slyak.model.Report;

public interface SpecificDao {

	List<Report> getLogReportGroupByMemberAndMonth(Long userId, List<Long> logTypeIds, Date from, Date to);

	List<Report> getLogReportGroupByMemberAndYear(Long userId, List<Long> logTypeIds, Date from, Date to);

	List<Report> getLogReportGroupByMemberAndDay(Long userId, List<Long> logTypeIds, Date from, Date to);

	List<Report> getLogReportGroupByTypeAndDay(Long userId, List<Long> logTypeIds, Date from, Date to);

	List<Report> getLogReportGroupByTypeAndMonth(Long userId, List<Long> logTypeIds,
			Date from, Date to);

	List<Report> getLogReportGroupByTypeAndYear(Long userId, List<Long> logTypeIds, Date from, Date to);

}
