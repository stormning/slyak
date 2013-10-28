package com.slyak.user.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.slyak.user.dao.UserExtDao;
import com.slyak.user.model.User;
import com.slyak.user.util.Status;
import com.slyak.user.util.UserQuery;

@Repository
public class UserExtDaoImpl implements UserExtDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Page<User> pageUsers(Pageable pageable, UserQuery userQuery) {
		StringBuffer countSql = new StringBuffer(
				"select count(u) from User u where 1=1");
		StringBuffer listSql = new StringBuffer("select u from User u where 1=1");

		StringBuffer suffixSql = new StringBuffer();

		String keyword = userQuery.getKeyword();
		boolean hasKeyword = StringUtils.hasText(keyword);
		if (hasKeyword) {
			suffixSql
					.append(" and (u.email like :keyword or u.nickName like :keyword)");
		}
		String role = userQuery.getRole();
		boolean hasRole = StringUtils.hasText(role);
		if (hasRole) {
			suffixSql.append(" and u.role like :role");
		}

		Status status = userQuery.getStatus();
		boolean hasStatus = status != null;
		if (hasStatus) {
			suffixSql.append(" and u.status=:status");
		}

		Query countQuery = em.createQuery(
				countSql.append(suffixSql).toString(), Long.class);
		Query listQuery = em.createQuery(listSql.append(suffixSql).toString(),
				User.class);
		if (hasKeyword) {
			countQuery.setParameter("keyword", "%" + keyword + "%");
			listQuery.setParameter("keyword", "%" + keyword + "%");
		}
		if (hasRole) {
			countQuery.setParameter("role", "," + role + ",");
			listQuery.setParameter("role", "," + role + ",");
		}
		if (hasStatus) {
			countQuery.setParameter("status", status);
			listQuery.setParameter("status", status);
		}
		int total = ((Number) countQuery.getSingleResult()).intValue();
		List<User> content = null;
		if (total > 0) {
			int offset = pageable.getPageNumber()*pageable.getPageSize();
			listQuery.setFirstResult(offset);
			listQuery.setMaxResults(pageable.getPageSize());
			content = listQuery.getResultList();
		} else {
			content = Collections.emptyList();
		}
		return new PageImpl<User>(content, pageable, total);
	}

}
