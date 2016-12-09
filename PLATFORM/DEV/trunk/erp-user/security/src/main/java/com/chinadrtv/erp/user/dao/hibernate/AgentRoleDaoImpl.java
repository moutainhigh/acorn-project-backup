package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.user.dao.AgentRoleDao;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
@Repository
public class AgentRoleDaoImpl extends GenericDaoHibernate<AgentRole,String> implements AgentRoleDao {
    public AgentRoleDaoImpl() {
        super(AgentRole.class);
    }

    public List<AgentRole> findByGrpId(String grpId) {
        String hql = "from AgentRole u where u.grpId =;grpId";
        Parameter ps = new ParameterString("grpId",grpId);
        return findList(hql,ps);
    }
}
