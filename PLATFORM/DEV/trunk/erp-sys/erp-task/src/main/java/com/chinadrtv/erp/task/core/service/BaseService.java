package com.chinadrtv.erp.task.core.service;

import java.io.Serializable;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;

public interface BaseService<T extends BaseEntity, PK extends Serializable> {

	BaseRepository<T, PK> getDao();
	
	public T get(PK pk);
	
	public void save(T e);
	
}
