package com.chinadrtv.erp.report.dao;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.report.entity.Role;

@Repository
public interface RoleDao extends BaseRepository<Role, Long> {

//	List<Roles> findByUsersId(Long id);

}
