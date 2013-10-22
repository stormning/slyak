/*
 * Project:  any
 * Module:   slyak-core
 * File:     JpaAuthorizingRealm.java
 * Modifier: zhouning
 * Modified: 2012-12-12 下午1:13:44 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.core.web;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-12
 * @param <T>
 */
public class JpaAuthorizingRealm<T> extends AuthorizingRealm implements InitializingBean{
	
	public enum SaltStyle {NO_SALT, CRYPT, COLUMN, EXTERNAL};
	
	private CriteriaBuilder criteriaBuilder ;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<T> entityClass;
	
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	private String userPKField = "id";
	
	private String usernameField;
	
	private String passwordField;
	
	private String rolesField;
	
	protected SaltStyle saltStyle = SaltStyle.NO_SALT;
	
	private String saltField = "salt";
	
	public void setSaltStyle(SaltStyle saltStyle) {
		this.saltStyle = saltStyle;
	}

	public void setUsernameField(String usernameField) {
		this.usernameField = usernameField;
	}

	public void setPasswordField(String passwordField) {
		this.passwordField = passwordField;
	}
	
	public void setRolesField(String rolesField) {
		this.rolesField = rolesField;
	}

	public void setSaltField(String saltField) {
		this.saltField = saltField;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        Long userId = (Long) getAvailablePrincipal(principals);
        
        Object user = getEntity(Collections.singletonMap(userPKField, userId));
        
        if(user == null){
        	return null;
        } else {
        	Set<String> rolesSet = null;
        	if(StringUtils.isNotBlank(rolesField)){
        		String rolesStr = (String) getEntityFieldValue(user,rolesField);
        		if(StringUtils.isNotBlank(rolesField)){
        			rolesSet = new HashSet<String>();
        			String[] roles =StringUtils.split(rolesStr,",");
        			for (String r : roles) {
        				rolesSet.add(r);
					}
        		}
        	}
        	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(rolesSet);
        	return info;
        }
	}
	
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        
        Object user = getEntity(Collections.singletonMap(usernameField, username));
        
        if(user == null){
        	throw new UnknownAccountException();
        }
        
        SimpleAuthenticationInfo info = null;
            String password = null;
            String salt = null;
            switch (saltStyle) {
            case NO_SALT:
                password = (String) getEntityFieldValue(user,passwordField);
                break;
            case CRYPT:
                // TODO: separate password and hash from getPasswordForUser[0]
                throw new ConfigurationException("Not implemented yet");
            case COLUMN:
                password = (String) getEntityFieldValue(user,passwordField);
                salt = (String) getEntityFieldValue(user,saltField);
                break;
            case EXTERNAL:
                password = (String) getEntityFieldValue(user,passwordField);
                salt = (String) getEntityFieldValue(user,saltField);
            }

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            info = new SimpleAuthenticationInfo(getEntityFieldValue(user,userPKField), password.toCharArray(), getName());
            
            if (salt != null) {
                info.setCredentialsSalt(ByteSource.Util.bytes(salt));
            }
            
            return info;
	}
	
	private Object getEntityFieldValue(Object entity,String fieldName){
		PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(entityClass, fieldName);
		try {
			return propertyDescriptor.getReadMethod().invoke(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getEntity(Map<String,?> propValueMap){
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root entityRoot = criteriaQuery.from(entityClass);
        Predicate[] predicates = new Predicate[propValueMap.size()];
        int i = 0;
        for (Iterator<String> propNameIt = propValueMap.keySet().iterator(); propNameIt.hasNext();i++) {
       	 String propName = propNameIt.next();
       	 predicates[i] = criteriaBuilder.equal(entityRoot.get(propName), propValueMap.get(propName));
        }
        criteriaQuery.select(entityRoot);
        criteriaQuery.where(predicates);
        Query query = entityManager.createQuery(criteriaQuery);
        query.setHint(QueryHints.CACHEABLE, true);
        List results = query.getResultList();
        if(CollectionUtils.isEmpty(results)){
        	return null;
        }else{
        	return results.get(0);
        }
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}
	
}
