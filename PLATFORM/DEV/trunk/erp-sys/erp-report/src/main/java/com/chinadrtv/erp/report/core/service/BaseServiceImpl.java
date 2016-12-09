package com.chinadrtv.erp.report.core.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;

public abstract class BaseServiceImpl<T extends BaseEntity<T>, PK extends Serializable> implements BaseService<T, PK>{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public abstract BaseRepository<T, PK> getDao();

	/**
	 * 通过主键获取
	 */
	@Override
	public T get(PK pk) {
		return getDao().findOne(pk);
	}

	/**
	 * 保存
	 */
	@Override
	public void save(T e) {
		getDao().save(e);
	}
	
}
