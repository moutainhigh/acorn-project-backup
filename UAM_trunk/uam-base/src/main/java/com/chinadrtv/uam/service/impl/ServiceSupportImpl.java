package com.chinadrtv.uam.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.HibernateDao;
import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.service.ServiceSupport;

@Service
@SuppressWarnings("unchecked")
public abstract class ServiceSupportImpl implements ServiceSupport {

	private HibernateDao hibernateDao;
	
	@Override
	public <E extends BaseEntity> E get(Class<E> entityClass, Serializable id) {
		return getHibernateDao().get(entityClass, id);
	}
	
	@Override
	public <E extends BaseEntity> E load(Class<E> entityClass, Serializable id) {
		return getHibernateDao().load(entityClass, id);
	}

	@Override
	public <E extends BaseEntity> List<E> getAll(Class<E> entityClass) {
		return getHibernateDao().getAll(entityClass);
	}

	@Override
	public <K extends Serializable, E extends BaseEntity> K save(final E e) {
		return (K) getHibernateDao().save(e);
	}

	@Override
	public <E extends BaseEntity> E merge(final E e) {
		return getHibernateDao().merge(e);
	}

	@Override
	public <E extends BaseEntity> void saveOrUpdate(final E e) {
		getHibernateDao().saveOrUpdate(e);
	}

	@Override
	public <E extends BaseEntity> void update(final E e) {
		getHibernateDao().update(e);
	}

	@Override
	public <E extends BaseEntity> void delete(final E e) {
		getHibernateDao().delete(e);
	}

	@Override
	public <K extends Serializable, E extends BaseEntity> void delete(final Class<E> entityClass, final K id) {
        E e = get(entityClass, id);
        if (e != null) delete(e);
	}
	
	@Override
	public <E extends BaseEntity> void evict(final E e) {
		getHibernateDao().evict(e);
	}
	
	protected HibernateDao getHibernateDao() {
		return hibernateDao;
	}
	
	@Resource
	public void setHibernateDao(HibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	
}
