package com.chinadrtv.erp.user.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.user.model.AgentRole;
import java.util.List;

public abstract interface AgentRoleDao extends GenericDao<AgentRole, String>
{
  public abstract List<AgentRole> findByGrpId(String paramString);
}