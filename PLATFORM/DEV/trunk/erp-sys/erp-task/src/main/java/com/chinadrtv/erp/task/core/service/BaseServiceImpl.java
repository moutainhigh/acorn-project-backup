package com.chinadrtv.erp.task.core.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;

public abstract class BaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements BaseService<T, PK>{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public abstract BaseRepository<T, PK> getDao();

	@Override
	public T get(PK pk) {
		return getDao().findOne(pk);
	}

	@Override
	public void save(T e) {
		getDao().save(e);
	}
	
}
