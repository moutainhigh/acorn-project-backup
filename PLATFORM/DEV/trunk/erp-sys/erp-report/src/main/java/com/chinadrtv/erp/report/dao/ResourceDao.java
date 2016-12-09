package com.chinadrtv.erp.report.dao;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.report.entity.Resource;

@Repository
public interface ResourceDao extends BaseRepository<Resource, Long> {

//	List<Resources> findByRolesId(Long id);

}
