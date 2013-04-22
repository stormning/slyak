package com.slyak.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.slyak.dao.SpecificDao;
import com.slyak.model.Report;

@Repository
@SuppressWarnings("unchecked")
public class SpecificDaoImpl implements SpecificDao {

	@PersistenceContext
	private EntityManager em;
	
	private void appendTimeRegion(StringBuffer sb,boolean hasFrom,boolean hasTo){
		if (hasFrom) {
			sb.append(" and al.happen_time>=:from");
		}
		if (hasTo) {
			sb.append(" and al.happen_time<=:to");
		}
	}
	
	
	@Override
	public List<Report> getLogReportGroupByMemberAndMonth(Long userId, List<Long> logTypeIds,
			Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(logTypeIds),hasFrom=false,hasTo=false;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_member_id as ownerId,MONTH(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		sb.append(" group by MONTH(al.happen_time)");
		
		if(hasIds) {
			sb.append(" ,al.log_member_id");
		}
		
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", logTypeIds);
		}
		if (hasFrom) {
			query.setParameter("from", from,TemporalType.DATE);
		}
		if (hasTo) {
			query.setParameter("to", to,TemporalType.DATE);
		}
		return wrap(query.getResultList());
	}
	
	private List<Report> wrap(List<Object[]> results){
		if(CollectionUtils.isEmpty(results)){
			return Collections.EMPTY_LIST;
		}else{
			List<Report> reports = new ArrayList<Report>();
			for (Object[] objects : results) {
				Report report = new Report();
				report.setUnits((BigDecimal)objects[0]);
				report.setOwnerId(objects[1]==null?null:((Number)objects[1]).longValue());
				report.setTime(((Number)objects[2]).intValue());
				reports.add(report);
			}
			return reports;
		}
	}

	@Override
	public List<Report> getLogReportGroupByMemberAndYear(Long userId, List<Long> logTypeIds,
			Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(logTypeIds),hasFrom=from!=null,hasTo=to!=null;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_member_id as ownerId,YEAR(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		sb.append(" group by YEAR(al.happen_time)");
		
		if(hasIds) {
			sb.append(" ,al.log_member_id");
		}
		
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", logTypeIds);
		}
		if (hasFrom) {
			query.setParameter("from", from);
		}
		if (hasTo) {
			query.setParameter("to", to);
		}
		return wrap(query.getResultList());
	}

	@Override
	public List<Report> getLogReportGroupByMemberAndDay(Long userId, List<Long> logTypeIds,
			Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(logTypeIds),hasFrom=from!=null,hasTo=to!=null;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_member_id as ownerId,DAY(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		
		sb.append(" group by DAY(al.happen_time),al.log_member_id");
		
		Query query = em.createNativeQuery(sb.toString());
		
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", logTypeIds);
		}
		if (hasFrom) {
			query.setParameter("from", from);
		}
		if (hasTo) {
			query.setParameter("to", to);
		}
		return wrap(query.getResultList());
	}

	@Override
	public List<Report> getLogReportGroupByTypeAndDay(Long userId, List<Long> ids,
			Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(ids),hasFrom=from!=null,hasTo=to!=null;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_type_id as ownerId,DAY(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		sb.append(" group by DAY(al.happen_time) ,al.log_type_id");
		
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", ids);
		}
		if (hasFrom) {
			query.setParameter("from", from);
		}
		if (hasTo) {
			query.setParameter("to", to);
		}
		return wrap(query.getResultList());
	}

	@Override
	public List<Report> getLogReportGroupByTypeAndMonth(Long userId,
			List<Long> ids, Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(ids),hasFrom=from!=null,hasTo=to!=null;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_type_id as ownerId,MONTH(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		sb.append(" group by MONTH(al.happen_time) ,al.log_type_id");
		
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", ids);
		}
		if (hasFrom) {
			query.setParameter("from", from);
		}
		if (hasTo) {
			query.setParameter("to", to);
		}
		return wrap(query.getResultList());
	}

	@Override
	public List<Report> getLogReportGroupByTypeAndYear(Long userId, List<Long> ids,
			Date from, Date to) {
		boolean hasIds = !CollectionUtils.isEmpty(ids),hasFrom=from!=null,hasTo=to!=null;
		StringBuffer sb = new StringBuffer(
				"SELECT SUM(al.units) as units,al.log_type_id as ownerId,YEAR(al.happen_time) as time FROM t_account_log al where al.user_id=:userId");
		appendTimeRegion(sb,hasFrom,hasTo);
		if(hasIds) {
			sb.append(" and al.log_type_id in (:ids)");
		}
		sb.append(" group by  YEAR(al.happen_time) ,al.log_type_id");
		
		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", userId);
		if(hasIds) {
			query.setParameter("ids", ids);
		}
		if (hasFrom) {
			query.setParameter("from", from);
		}
		if (hasTo) {
			query.setParameter("to", to);
		}
		return wrap(query.getResultList());
	}

}
