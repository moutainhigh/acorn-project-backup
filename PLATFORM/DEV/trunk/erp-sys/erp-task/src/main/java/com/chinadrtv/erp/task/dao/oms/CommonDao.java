package com.chinadrtv.erp.task.dao.oms;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;

@Repository
public interface CommonDao extends BaseRepository<BaseEntity, Serializable> {

}
