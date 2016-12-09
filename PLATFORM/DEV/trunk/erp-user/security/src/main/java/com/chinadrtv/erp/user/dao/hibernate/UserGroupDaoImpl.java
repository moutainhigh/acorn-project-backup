/**
 * 
 */
package com.chinadrtv.erp.user.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.user.dao.UserGroupDao;
import com.chinadrtv.erp.user.model.UserGroup;

/**
 *  
 * @author haoleitao
 * @date 2013-3-19 上午10:16:12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository("userGroupDao")
public class  UserGroupDaoImpl extends GenericDaoHibernate<UserGroup,String> implements UserGroupDao {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserGroupDaoImpl.class);
	/**
	 * @param persistentClass
	 */
	public UserGroupDaoImpl() {
		super(UserGroup.class);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#getAll()
	 */
	public List<UserGroup> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#getAllDistinct()
	 */
	public List<UserGroup> getAllDistinct() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public UserGroup get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#save(java.lang.Object)
	 */
	public UserGroup save(UserGroup object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findByNamedQuery(java.lang.String, java.util.Map)
	 */
	public List<UserGroup> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(UserGroup object) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findPageCount(java.lang.String, java.util.Map)
	 */
	public int findPageCount(String hql, Map<String, Parameter> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findPageCount(java.lang.String, java.util.Map, java.util.Map)
	 */
	public int findPageCount(String hql, Map<String, Parameter> parameters,
			Map<String, Criteria> criterias) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findPageList(java.lang.String, java.util.Map)
	 */
	public List<UserGroup> findPageList(String hql,
			Map<String, Parameter> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findPageList(java.lang.String, java.util.Map, java.util.Map)
	 */
	public List<UserGroup> findPageList(String hql,
			Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findList(java.lang.String, java.util.Map)
	 */
	public List<UserGroup> findList(String hql,
			Map<String, Parameter> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findList(java.lang.String, java.util.Map, java.util.Map)
	 */
	public List<UserGroup> findList(String hql,
			Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#find(java.lang.String, java.util.Map)
	 */
	public UserGroup find(String hql, Map<String, Parameter> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#find(java.lang.String, java.util.Map, java.util.Map)
	 */
	public UserGroup find(String hql, Map<String, Parameter> parameters,
			Map<String, Criteria> criterias) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#execUpdate(java.lang.String, java.util.Map)
	 */
	public int execUpdate(String hql, Map<String, Parameter> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#execUpdate(java.lang.String, java.util.Map, java.util.Map)
	 */
	public int execUpdate(String hql, Map<String, Parameter> parameters,
			Map<String, Criteria> criterias) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#find(java.lang.String, com.chinadrtv.erp.core.dao.query.Parameter[])
	 */
	public UserGroup find(String hql, Parameter... parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#findList(java.lang.String, com.chinadrtv.erp.core.dao.query.Parameter[])
	 */
	public List<UserGroup> findList(String hql, Parameter... parametersArray) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.GenericDao#execUpdate(java.lang.String, com.chinadrtv.erp.core.dao.query.Parameter[])
	 */
	public int execUpdate(String hql, Parameter... parametersArray) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.user.dao.UserGroupDao#getUserGroupsByUserId(java.lang.String)
	 */
	public List<UserGroup> getUserGroupsByUserId(String userId) {
		// TODO Auto-generated method stub
		List<UserGroup> list = null;
		try{
	        String hql = "from UserGroup ug where ug.usrId =:usrId ";
	        Query q = getSession().createQuery(hql);
	        q.setString("usrId", userId);
	        list = q.list();
		}catch(Exception e){
            logger.error("",e);
		}

		return list;
	}

}
