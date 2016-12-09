package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.user.dao.AgentRoleDao;
import com.chinadrtv.erp.user.model.AgentRole;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AgentRoleDaoImpl extends GenericDaoHibernate<AgentRole, String>
  implements AgentRoleDao
{
  public AgentRoleDaoImpl()
  {
    super(AgentRole.class);
  }

  public List<AgentRole> findByGrpId(String grpId) {
    String hql = "from AgentRole u where u.grpId =;grpId";
    Parameter ps = new ParameterString("grpId", grpId);
    return findList(hql, new Parameter[] { ps });
  }
}