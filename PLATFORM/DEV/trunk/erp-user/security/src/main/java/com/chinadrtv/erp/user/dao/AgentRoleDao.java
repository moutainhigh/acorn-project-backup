package com.chinadrtv.erp.user.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.user.model.AgentRole;

import java.util.List;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
public interface AgentRoleDao extends GenericDao<AgentRole,String>{
    List<AgentRole> findByGrpId(String grpId);
}
