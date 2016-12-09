package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dao.UserGroupDao;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class AgentUserDaoImpl extends GenericDaoHibernate<AgentUser, String>
  implements AgentUserDao, UserDetailsService
{

  @Autowired
  private UserGroupDao userGroupDao;

  public AgentUserDaoImpl()
  {
    super(AgentUser.class);
  }

  public List<AgentRole> findByGrpId(String grpId)
  {
    String hql = "from AgentRole u where  u.grpId =:grpId";
    ParameterString ps = new ParameterString("grpId", grpId);
    Query q = getSession().createQuery(hql);
    q.setString(ps.getName(), (String)ps.getValue());
    return q.list();
  }

  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    String hql = "from AgentUser u where u.userId =:userId";
    Parameter ps = new ParameterString("userId", s);
    AgentUser user = (AgentUser)find(hql, new Parameter[] { ps });

    List agentRoles = findByGrpId(user.getDefGrp());
    user.setAgentRoles(new HashSet(agentRoles));
    return user;
  }
}