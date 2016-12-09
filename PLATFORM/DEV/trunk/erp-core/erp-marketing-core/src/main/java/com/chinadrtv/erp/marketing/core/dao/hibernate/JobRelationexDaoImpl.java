package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.JobRelationexDao;
import com.chinadrtv.erp.marketing.core.dto.AgentJobs;
import com.chinadrtv.erp.marketing.core.dto.JobsRelationex;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.agent.JobsRelation;

/**
 * 
 * <dl>
 * <dt><b>Title:查询取数规则</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:查询取数规则</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class JobRelationexDaoImpl extends GenericDaoHibernate<JobsRelation, java.lang.String> implements
		Serializable, JobRelationexDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplateAgent;
	
	public JobRelationexDaoImpl() {
		super(JobsRelation.class);
	}

	/**
	 * 查询取数规则
	 */
	public List<JobsRelationex> queryJobRelationex(String userId,String userType, String jobId) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("select distinct x.jobid, x.top, x.days ");
		sql.append("  from acoapp_security.tsr_iagent_group c,");
		sql.append("       acoapp_security.tsr_group_job_relation g,");
		sql.append("       iagent.pd_sys_jobs_relationex x");
		sql.append("     where g.group_id = c.group_id");
		sql.append("       and c.account_id = ? ");
		sql.append("       and g.job_id = x.jobid");
		sql.append("       and x.usrtype = ? ");
		
		Object[] params = null;
		if(!StringUtil.isNullOrBank(jobId)){
			sql.append("   and x.jobid=?");
			params = new Object[] { userId,userType,jobId};
		}else{
			params = new Object[] { userId,userType};
		}
		
		
		
		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<JobsRelationex>() {

			public JobsRelationex mapRow(ResultSet resultSet, int arg1) throws SQLException {
				JobsRelationex jobsRelationex = new JobsRelationex();
				jobsRelationex.setJobId(resultSet.getString("jobid"));
				jobsRelationex.setTop(resultSet.getObject("top")!=null?resultSet.getInt("top"):null);
				jobsRelationex.setDays(resultSet.getObject("days")!=null?resultSet.getInt("days"):null);
				return jobsRelationex;
			}
			
		});
	}

	/**
	 * 查询已取数量
	 */
	public Integer queryAssignHist(String userId, String jobId,Integer days) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("select count(1)");
		sql.append("  from iagent.ob_assign_hist t");
		sql.append(" where t.agent = ?");
		sql.append("   and t.jobid = ?");
		sql.append("   and t.assigntime > to_date(to_char(sysdate + 1, 'YYYY-MM-DD'), 'YYYY-MM-DD') - ?");
		
		Object[] params = new Object[] { userId,jobId,days};
		
		
		
		List<Integer> result = jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<Integer>() {

			public Integer mapRow(ResultSet resultSet, int arg1) throws SQLException {
				Integer count = resultSet.getInt(1);
				return count;
			}
			
		});
		if(!result.isEmpty()) return result.get(0);
		return null;
	}
	
	/**
	 * 
	* @Description: 当前数量
	* @param userId
	* @param jobId
	* @param validDays
	* @return
	* @return Integer
	* @throws
	 */
	public Integer queryCurrentAssignHist(String userId, String jobId) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("select count(1)");
		sql.append("  from iagent.ob_contact a");
		sql.append(" where a.assignagent = ?");
		sql.append("   and a.pd_jobid = ?");
		sql.append("   and ((sysdate >= startdate or startdate is null) and");
		sql.append("       (sysdate <= a.assigntime + 7 or sysdate <= enddate))");
		sql.append("   and (a.status <= 0 or a.status is null)");
		
		Object[] params = new Object[] { userId,jobId};
		
		
		
		List<Integer> result = jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<Integer>() {

			public Integer mapRow(ResultSet resultSet, int arg1) throws SQLException {
				Integer count = resultSet.getInt(1);
				return count;
			}
			
		});
		if(!result.isEmpty()) return result.get(0);
		return null;
	}

	/* (非 Javadoc)
	* <p>Title: queryUnprocessed</p>
	* <p>Description: </p>
	* @param userId
	* @param jobId
	* @return
	* @see com.chinadrtv.erp.marketing.core.dao.JobRelationexDao#queryUnprocessed(java.lang.String, java.lang.String)
	*/ 
	@Override
	public List<AssignMessage> queryUnprocessed(String userId, String jobId) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("select distinct a.contactid");
		sql.append("  from iagent.ob_assign_hist a");
		sql.append("  left join iagent.ob_contact_hist b");
		sql.append("    on b.pd_customerid = a.pd_customerid");
		sql.append("  left join iagent.ob_contact c");
		sql.append("    on c.pd_customerid = a.pd_customerid");
		sql.append(" where a.agent = ?");
		//sql.append("   and a.jobid = ?");
		sql.append("   and b.assignagent is null");
		sql.append("   and c.status = -1");
		sql.append("   and a.assigntime > sysdate - 7");
		
		//Object[] params =   new Object[] { userId,jobId};
		Object[] params =   new Object[] { userId};
		
		
		
		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<AssignMessage>() {

			public AssignMessage mapRow(ResultSet resultSet, int arg1) throws SQLException {
				AssignMessage assignMessage = new AssignMessage();
				assignMessage.setContactId(resultSet.getString("contactid"));
				return assignMessage;
			}
			
		});
	}
	
	/**
	 * 查询job列表
	 */
	public List<AgentJobs> queryAllJob() {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("SELECT DISTINCT d.jobid ");
		sql.append("FROM iagent.pd_sys_agent_group a,");
		sql.append("     iagent.pd_sys_accounts b,");
		sql.append("     iagent.pd_sys_group_jobs d ");
		sql.append("WHERE a.accountid = b.account");
		sql.append("      AND a.groupid = d.groupid");
		sql.append("  ORDER BY d.jobid");
		
		
		return jdbcTemplateAgent.query(sql.toString(), new RowMapper<AgentJobs>() {

			public AgentJobs mapRow(ResultSet resultSet, int arg1) throws SQLException {
				AgentJobs job = new AgentJobs();
				job.setJobId(resultSet.getString("jobid"));
				return job;
			}
			
		});
	}

	/**
	 * 查询job列表
	 */
	public List<AgentJobs> queryJob(String agentId) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("SELECT DISTINCT d.jobid ");
		sql.append("FROM iagent.pd_sys_agent_group a,");
		sql.append("     iagent.pd_sys_accounts b,");
		sql.append("	 iagent.usr c,");
		sql.append("     iagent.pd_sys_group_jobs d ");
		sql.append("WHERE a.accountid = b.account");
		sql.append("      AND a.accountid = c.usrid");
		sql.append("      AND a.groupid = d.groupid");
		sql.append("      AND c.usrid = ?");
		sql.append("  ORDER BY d.jobid");
		
		Object[] params = new Object[] { agentId};
		
		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<AgentJobs>() {

			public AgentJobs mapRow(ResultSet resultSet, int arg1) throws SQLException {
				AgentJobs job = new AgentJobs();
				job.setJobId(resultSet.getString("jobid"));
				return job;
			}
			
		});
	}
	
	
	/**
	 * 查询job下的组列表
	 */
	public List<Grp> queryJobGroup(String dept,String jobId) {
		StringBuilder sql = new StringBuilder("");
		
		sql.append("SELECT DISTINCT d.grpid, d.grpname ");
		sql.append("FROM iagent.pd_sys_agent_group a,");
		sql.append("     iagent.pd_sys_accounts b,");
		sql.append("     iagent.usr c,");
		sql.append("     iagent.grp d, ");
		sql.append("     iagent.pd_sys_group_jobs e ");
		sql.append("WHERE a.accountid = b.account ");
		sql.append("      AND a.accountid = c.usrid ");
		sql.append("      AND c.defgrp = d.grpid ");
		sql.append("      and A.GROUPID=e.groupid ");
		sql.append("      AND d.dept = ? ");
		sql.append("      and e.jobid= ? ");
		sql.append(" order by d.grpid");
		
		Object[] params = new Object[] { dept,jobId};
		
		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<Grp>() {

			public Grp mapRow(ResultSet resultSet, int arg1) throws SQLException {
				Grp grp = new Grp();
				grp.setGrpid(resultSet.getString("grpid"));
				grp.setGrpname(resultSet.getString("grpname"));
				return grp;
			}
			
		});
	}
	
	
//	/**
//	 * 查询job列表
//	 */
//	public List<AgentJobs> queryJob(String deptNum) {
//		StringBuilder sql = new StringBuilder("");
//		
//		sql.append("select distinct j.jobid ");
//		sql.append("  from iagent.grp g,iagent.pd_sys_group_jobs j ");
//		sql.append("  where g.dept=? ");
//		sql.append("       and g.grpid=j.groupid ");
//		sql.append("  order by j.jobid");
//		
//		Object[] params = new Object[] { deptNum};
//		
//		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<AgentJobs>() {
//
//			public AgentJobs mapRow(ResultSet resultSet, int arg1) throws SQLException {
//				AgentJobs job = new AgentJobs();
//				job.setJobId(resultSet.getString("jobid"));
//				return job;
//			}
//			
//		});
//	}
//	
//	
//	/**
//	 * 查询job下的组列表
//	 */
//	public List<Grp> queryJobGroup(String jobId) {
//		StringBuilder sql = new StringBuilder("");
//		
//		sql.append("select g.grpid,g.grpname ");
//		sql.append("  from iagent.grp g,iagent.pd_sys_group_jobs j ");
//		sql.append("  where j.jobid=? ");
//		sql.append("       and g.grpid=j.groupid ");
//		sql.append("  order by g.grpid");
//		
//		Object[] params = new Object[] { jobId};
//		
//		return jdbcTemplateAgent.query(sql.toString(), params, new RowMapper<Grp>() {
//
//			public Grp mapRow(ResultSet resultSet, int arg1) throws SQLException {
//				Grp grp = new Grp();
//				grp.setGrpid(resultSet.getString("grpid"));
//				grp.setGrpname(resultSet.getString("grpname"));
//				return grp;
//			}
//			
//		});
//	}
}
