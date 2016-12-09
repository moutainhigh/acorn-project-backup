package com.chinadrtv.erp.user.dao.hibernate;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dao.UserGroupDao;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.Constants;

/**
 * User: liuhaidong
 * Date: 12-11-19
 */

@Repository("userDao")
public class AgentUserDaoImpl extends GenericDaoHibernate<AgentUser,String> implements AgentUserDao, UserDetailsService {
	@Autowired
	private UserGroupDao userGroupDao;
	
    public AgentUserDaoImpl() {
        super(AgentUser.class);
    }

    public List<AgentRole> findByGrpId(String grpId) {
        //find by static permission names, because no pk in PGLINK
        String hql = "from AgentRole u where  u.grpId =:grpId and u.right='-1'";
        ParameterString ps = new ParameterString("grpId",grpId);
        Query q = getSession().createQuery(hql);
        q.setString(ps.getName(),ps.getValue());
        return q.list();
    }

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String hql = "from AgentUser u where u.userId =:userId and u.valid='-1'";
        Parameter ps = new ParameterString("userId",s);
        AgentUser user = find(hql,ps);
        //鑾峰彇oms绯荤粺鐢ㄦ埛缁�
        // UserGroup userGroup = userGroupDao.getUserGroupsByUserId(s).get(0);
        // List<AgentRole> agentRoles = findByGrpId(userGroup.getGroupId());
        List<AgentRole> agentRoles = findByGrpId(user.getDefGrp());
        user.setAgentRoles(new HashSet(agentRoles));
        return user;
    }
    
    public String queryAgentUserType(String userId) {
    	//"select * from iagent.pd_sys_accounts a where a.account='27430';";
    	StringBuilder sql= new StringBuilder();
    	sql.append("select a.usrtype from ")
    	   .append(Constants.IAGENT_SCHEMA).append(".pd_sys_accounts a ")
    	   .append("where a.account=:userId");
    	SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("userId", userId);
		String userType = (String)sqlQuery.uniqueResult();
		return userType;
    }
    
/*       select u.usrid, u.name, ul.levelid, ul.levelid2, ul.levelid3
     from iagent.usr u
     left join iagent.usrlevel ul
       on u.usrid = ul.usrid
    where u.usrid = '12650'
          and ul.valid='-1'*/
    public Map<String, Object> queryUserLevel(String userId) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select ")
    	   .append("u.usrid userId, ")
    	   .append("u.name userName, ")
    	   .append("ul.levelid levelId, ")
    	   .append("ul.levelid2 levelId2, ")
    	   .append("ul.levelid3 levelId3 ")
    	   .append("from ")
    	   .append(SchemaConstants.IAGENT_SCHEMA).append(".usr u ")
    	   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".usrlevel ul on u.usrid = ul.usrid ")
    	   .append("where u.usrid=:userId ")
    	   .append("and ul.valid='-1'");
    	Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
    	sqlQuery.setString("userId", userId);
    	sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		Map<String, Object> mapList  = (Map<String, Object>)sqlQuery.uniqueResult();
		return mapList;
    }
    
    public List<AgentUserInfo4TeleDist> queryUserLevelInBatch(List<String> userIds) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select ")
    	   .append("ul.usrid userId, ")
    	   .append("ul.levelid levelId, ")
    	   .append("ul.levelid2 levelId2, ")
    	   .append("ul.levelid3 levelId3 ")
    	   .append("from ")
    	   .append(SchemaConstants.IAGENT_SCHEMA).append(".usrlevel ul ")
    	   .append("where ul.usrid in(:userId) ")
    	   .append("and ul.valid='-1'");
    	Query sqlQuery = this.getSessionFactory().getCurrentSession()
    			.createSQLQuery(sql.toString())
    			.addScalar("userId", StringType.INSTANCE)
    			.addScalar("levelId", StringType.INSTANCE)
    			.addScalar("levelId2", StringType.INSTANCE)
    			.addScalar("levelId3", StringType.INSTANCE)
    			.setResultTransformer(Transformers.aliasToBean(AgentUserInfo4TeleDist.class));
    	sqlQuery.setParameterList("userId", userIds);
		@SuppressWarnings("unchecked")
		List<AgentUserInfo4TeleDist> users = sqlQuery.list();
		return users;
    }
    
    /* (非 Javadoc)
	* <p>Title: queryAgentUserByGroup</p>
	* <p>Description: </p>
	* @param groupId
	* @return
	* @see com.chinadrtv.erp.user.dao.AgentUserDao#queryAgentUserByGroup(java.lang.String)
	*/ 
	@Override
	public List<AgentUser> queryAgentUserByGroup(String groupId) {
		String hql = "from AgentUser u where u.defGrp =:groupId and u.valid='-1'";
        Parameter ps = new ParameterString("groupId",groupId);
        List<AgentUser> list = this.findList(hql, ps);
        return list;
	}
	
	private static final String REMOVE_USER_LEVEL_SQL = 
			"UPDATE IAGENT.USRLEVEL SET LEVELID = '', LEVELID2 = '', LEVELID3 = '', MDDT = SYSDATE";
	
	@Override
	public void removeAllUserLevel() {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(REMOVE_USER_LEVEL_SQL).executeUpdate();
			}
		});
	}

	@Override
	public Integer saveUserLevels(final List<UserLevel> userLevels) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				int count = 0;
				for (UserLevel ul : userLevels) {
					count++;
					session.saveOrUpdate(session.merge(ul));
					
					if (count % 50 == 0) {
						session.flush();
						session.clear();
					}
				}
				
				return count;
			}
		});
	}
	
}
