package com.chinadrtv.erp.report.core.service;

import java.io.Serializable;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;

public interface BaseService<T extends BaseEntity<T>, PK extends Serializable> {

	BaseRepository<T, PK> getDao();
	
	/**
	 * 通过主键获取
	 * @param pk
	 * @return
	 */
	public T get(PK pk);
	
	/**
	 * 保存
	 * @param e
	 */
	public void save(T e);
	
}
