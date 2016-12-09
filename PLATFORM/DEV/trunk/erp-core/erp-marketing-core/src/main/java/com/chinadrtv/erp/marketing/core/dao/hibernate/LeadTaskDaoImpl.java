/*
 * @(#)LeadTaskDaoImpl.java 1.0 2013-5-10上午11:36:39
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.UserBpm;

/**
 * 
 * @author cuiming
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:36:39
 * 
 */
@Repository
public class LeadTaskDaoImpl extends
		GenericDaoHibernate<LeadTask, java.lang.Long> implements Serializable,
		LeadTaskDao {
	private static final long serialVersionUID = 1L;

	public LeadTaskDaoImpl() {
		super(LeadTask.class);
	}

	public void saveLeadTask(LeadTask leadTask) {
		getHibernateTemplate().evict(leadTask);
		Long id = (Long)getHibernateTemplate().save(leadTask);
		leadTask.setId(id);
	}
	
	public void evict(LeadTask leadTask) {
		getHibernateTemplate().evict(leadTask);
	}

	public void updateLeadTask(LeadTask leadTask) {
		getHibernateTemplate().saveOrUpdate(leadTask);
	}

	/**
	 * 根据 leadI查询 leadTask
	 */
	public List<LeadTask> getLeadTaskByHql(Long leadId) {
		String hql = "from LeadTask m where m.leadId=:leadId";
		Parameter leadIds = new ParameterLong("leadId", leadId);
		List<LeadTask> m = (List<LeadTask>) findList(hql, leadIds);
		return m;
	}

	/**
	 * TODO 1. potential contact 2. order by 3. date range
	 */
	public List<Object> query(CampaignTaskDto mTaskDto, DataGridModel dataModel) {
		String sql = "select * from (select A.*, rownum rn from ( select ca.id caID, ca.name caName,ltp.name ltpName,con.contactid conid,"
				+ "con.name conName, pc.id pcid, pc.name pcName, m.create_date ltCreateDate," +
				" le.end_date endDate, m.status ltStatus, m.userID userID, m.BPM_COMMENT remark, " +
				" m.START_DATE appointDate, m.id tid, m.BPM_INSTANCE_ID instid, m.IS_POTENTIAL isPotential," +
				"m.PD_CUSTOMER_ID pdCustomerId, le.id leadId," +
				"CASE WHEN m.status IN (0,1) AND TO_NUMBER(m.start_date-SYSDATE) * 24 <= 2 AND TO_NUMBER(m.Start_Date-sysdate) * 24 > 0 THEN 0" +
				"WHEN m.status IN (0,1) AND TO_NUMBER(le.end_date-SYSDATE) <= 1 AND TO_NUMBER(le.end_date-SYSDATE) > 0 THEN 1" +
				"ELSE 2 END exigency, m.SOURCE_TYPE sourceType, ltp.id ltpId, m.SOURCE_TYPE2 sourceType2, m.SOURCE_TYPE3 sourceType3"
				+ " from "+Constants.MARKETING_SCHEMA+".lead_task m "
				+ "left join "+Constants.IAGENT_SCHEMA+".contact con on m.contact_id=con.contactid "
				+ "left join "+Constants.MARKETING_SCHEMA+".potential_contact pc on m.contact_id=pc.id "
				+ "left join "+Constants.MARKETING_SCHEMA+".lead le on m.lead_id=le.id "
				+ "left join "+Constants.MARKETING_SCHEMA+".campaign ca on le.campaign_id=ca.id "
				+ "left join "+Constants.MARKETING_SCHEMA+".lead_type ltp on ca.lead_type_id = ltp.id "
				+ "where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		genHibernateSql(sb, mTaskDto);
		if (StringUtils.isEmpty(dataModel.getSortAlias())) {
			sb.append(" order by exigency asc, ltCreateDate desc");
		} else if (StringUtils.equals(dataModel.getSortAlias(), "endDate")) { 
			// TODO 任务结束时间有排序规则，需要单独处理
		} else {
			sb.append(" order by ");
			sb.append(dataModel.sorting());
		}
		sb.append(" ) A where rownum<=:rows ) where rn >:page");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString());

		assignValue(sqlQuery, mTaskDto);
		sqlQuery.setInteger("page", dataModel.getStartRow());
		sqlQuery.setInteger("rows",
				dataModel.getStartRow() + dataModel.getRows());

		List<Object> objList = sqlQuery.list();
		return objList;
	}

	public Integer queryCount(CampaignTaskDto mTaskDto) {
		StringBuilder sql = new StringBuilder(
				"select count(m.id)"
						+ " from "+Constants.MARKETING_SCHEMA+".lead_task m "
						+ "left join "+Constants.IAGENT_SCHEMA+".contact con on m.contact_id=con.contactid "
						+ "left join "+Constants.MARKETING_SCHEMA+".potential_contact pc on m.contact_id=pc.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead le on m.lead_id=le.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".campaign ca on le.campaign_id=ca.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead_type ltp on ca.lead_type_id = ltp.id "
						+ "where 1=1 ");
		StringBuilder sb = new StringBuilder(sql);
		genHibernateSql(sb, mTaskDto);
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString());
		assignValue(sqlQuery, mTaskDto);
		List<Object> objList = sqlQuery.list();
		int count = ((BigDecimal) objList.get(0)).intValue();
		return count;
	}
	
	public List<Object> queryAgentAllocatedCount( ) {
		StringBuilder sql = new StringBuilder(
				"select le.owner, count(m.id)"
						+ " from "+Constants.MARKETING_SCHEMA+".lead_task m "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead le on m.lead_id=le.id "
						+"where le.owner is not null and m.SOURCE_TYPE = '3' "
						+ "group by le.owner");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		List<Object> objList = sqlQuery.list();
		return objList;
	}
	
	public List<Object> queryAgentUndialCount( ) {
		StringBuilder sql = new StringBuilder(
				"select le.owner, count(m.id)"
						+ " from "+Constants.MARKETING_SCHEMA+".lead_task m "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead le on m.lead_id=le.id "
						+ "AND m.status = '0' and le.owner is not null and m.SOURCE_TYPE = '3' "
						+ "group by le.owner");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		List<Object> objList = sqlQuery.list();
		return objList;
	}
	
	/**
select t.contact_id,t.source_type,  t.userid, t.create_date, le.end_date
  from acoapp_marketing.lead_task t left join acoapp_marketing.lead le on t.lead_id=le.id 
 where t.source_type in ('1', '3')
   and t.contact_id = '10021920'
 group by t.contact_id, t.source_type, t.userid, t.create_date, le.end_date
 order by t.create_date desc
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryContactTaskHistory(String contactId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.contact_id,t.source_type,  t.userid, t.create_date, le.end_date ")
		   .append("from ").append(Constants.MARKETING_SCHEMA).append(".lead_task t ")
		   .append("left join ").append(Constants.MARKETING_SCHEMA).append(".lead le ").append(" on t.lead_id=le.id ")
		   .append("where t.source_type in ('1', '3') ")
		   .append("and t.contact_id = :contactId ")
		   .append("group by t.contact_id, t.source_type, t.userid, t.create_date, le.end_date ")
		   .append(" order by t.create_date desc");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("contactId", contactId);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> mapList  = sqlQuery.list();
		return mapList;
	}
	
	public Integer queryUnStartedCampaignTaskCount(CampaignTaskDto mTaskDto) {
		StringBuilder sql = new StringBuilder(
				"select count(m.id)"
						+ " from "+Constants.MARKETING_SCHEMA+".lead_task m "
						+ "left join "+Constants.IAGENT_SCHEMA+".contact con on m.contact_id=con.contactid "
						+ "left join "+Constants.MARKETING_SCHEMA+".potential_contact pc on m.contact_id=pc.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead le on m.lead_id=le.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".campaign ca on le.campaign_id=ca.id "
						+ "left join "+Constants.MARKETING_SCHEMA+".lead_type ltp on ca.lead_type_id = ltp.id "
						+ "where 1=1 ");
		StringBuilder sb = new StringBuilder(sql);
		genHibernateSql(sb, mTaskDto);
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString());
		assignValue(sqlQuery, mTaskDto);
		List<Object> objList = sqlQuery.list();
		int count = ((BigDecimal) objList.get(0)).intValue();
		return count;
	}

	public int updateInstStatus(String instID, String status, String remark, String taskID) {
		StringBuilder sql = new StringBuilder();
		if(StringUtils.isBlank(status) && StringUtils.isBlank(remark)) {
			return 0;
		}
		sql.append("update "+Constants.MARKETING_SCHEMA+".lead_task lt set lt.update_date=sysdate, ");
		if(StringUtils.isNotBlank(status)) {
		   sql.append("lt.status=:status ");
		}
		if(StringUtils.isNotBlank(status) && StringUtils.isNotBlank(remark)) {
			sql.append(", ");
		}
		if(StringUtils.isNotBlank(remark)) {
			sql.append("lt.bpm_comment=:remark ");
		}
		sql.append(" where lt.bpm_instance_id=:instID");
		if (StringUtils.isNotBlank(taskID)) {
			sql.append(" and lt.bpm_task_id=:taskID");
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		if(StringUtils.isNotBlank(remark)) {
			sqlQuery.setString("remark", remark);
		}
		if(StringUtils.isNotBlank(status)) {
			sqlQuery.setString("status", status);
		}
		sqlQuery.setString("instID", instID);
		if (StringUtils.isNotBlank(taskID)) {
			sqlQuery.setString("taskID", taskID);
		}
		int number = sqlQuery.executeUpdate();
		return number;
	}
	
	public int updateInstIsDone(String instID, long isDone) {
		StringBuilder sql = new StringBuilder();
		sql.append("update "+Constants.MARKETING_SCHEMA+".lead_task lt set ")
		   .append(" lt.IS_DONE=:isDone ")
		   .append(" where lt.bpm_instance_id=:instID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("isDone", isDone);
		sqlQuery.setString("instID", instID);
		int number = sqlQuery.executeUpdate();
		return number;
	}
	
	public int updateInstAppointDate(String instID, Date appointDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("update "+Constants.MARKETING_SCHEMA+".lead_task lt set ")
		   .append(" lt.start_date=:appointDate ")
		   .append(" where lt.bpm_instance_id=:instID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setTime("appointDate", appointDate);
		sqlQuery.setString("instID", instID);
		int number = sqlQuery.executeUpdate();
		return number;
	}
	
	public String queryBPMProcessID(String camID) {
		String pID = null;
		StringBuilder sql = new StringBuilder(
				"select ltype.bpm_def_id from "+Constants.MARKETING_SCHEMA+".campaign cam " +
						"left join "+Constants.MARKETING_SCHEMA+".lead_type ltype on cam.lead_type_id=ltype.id " +
								"where cam.id= :camID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("camID", camID);
		List<Object> objList = sqlQuery.list();
		pID = (String) objList.get(0);
		return pID;
	}

	public Date queryEndDate(String leadID) {
		Date endDate = null;
		StringBuilder sql = new StringBuilder(
				"select cam.end_date from "+Constants.MARKETING_SCHEMA+".lead le " +
						"left join "+Constants.MARKETING_SCHEMA+".campaign cam on le.campaign_id=cam.id"
						+ " where le.id= :leadID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("leadID", leadID);
		List<Object> objList = sqlQuery.list();
		endDate = (Date) objList.get(0);
		return endDate;
	}

	public int updateTaskOwnerByLead(long leadID, String newOwner) {
		StringBuilder sql = new StringBuilder(
				"update "+Constants.MARKETING_SCHEMA+".lead_task lt set lt.userid = :userid "
						+ " where lt.lead_id= :leadID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("leadID", leadID);
		sqlQuery.setString("userid", newOwner);
		int updatedNum = sqlQuery.executeUpdate();
		return updatedNum;
	}
	
	public int updateTaskStatusByLead(long leadID, String status) {
		StringBuilder sql = new StringBuilder(
				"update "+Constants.MARKETING_SCHEMA+".lead_task lt set lt.status=:status "
						+ " where lt.lead_id= :leadID and lt.status in('0','1')");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("leadID", leadID);
		sqlQuery.setString("status", status);
		int updatedNum = sqlQuery.executeUpdate();
		return updatedNum;
	}

	public List<Object> queryTasksByLead(long leadID) {
		StringBuilder sql = new StringBuilder(
				"select lt.bpm_task_id from "+Constants.MARKETING_SCHEMA+".lead_task lt "
						+ " where lt.lead_id= :leadID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("leadID", leadID);
		List<Object> objs = sqlQuery.list();
		return objs;
	}
	
	public List<Object> queryInstsByLead(long leadID) {
		StringBuilder sql = new StringBuilder(
				"select lt.bpm_instance_id from "+Constants.MARKETING_SCHEMA+".lead_task lt "
						+ " where lt.lead_id= :leadID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("leadID", leadID);
		List<Object> objs = sqlQuery.list();
		return objs;
	}
	
	public List<Object> queryAliveInstsByLead(long leadID) {
		StringBuilder sql = new StringBuilder(
				"select lt.bpm_instance_id from "+Constants.MARKETING_SCHEMA+".lead_task lt "
						+ " where lt.lead_id= :leadID and lt.status in('0','1')");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("leadID", leadID);
		List<Object> objs = sqlQuery.list();
		return objs;
	}
	
	public Object queryInstsIsDone(String instID) {
		StringBuilder sql = new StringBuilder(
				"select lt.is_done from "+Constants.MARKETING_SCHEMA+".lead_task lt "
						+ " where lt.bpm_instance_id=:instID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("instID", instID);
		Object obj = sqlQuery.uniqueResult();
		return obj;
	}
	
	public LeadTask queryInst(String instID) {
		StringBuilder sql = new StringBuilder(
				"select lt.* from "+Constants.MARKETING_SCHEMA+".lead_task lt "
						+ " where lt.bpm_instance_id=:instID");
		
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("instID", instID);
		LeadTask leadTask = (LeadTask)sqlQuery.addEntity(LeadTask.class).uniqueResult();
		return leadTask;
	}
	
	@SuppressWarnings("unchecked")
	public List<LeadTask> queryInvalidPushInst() {
		StringBuilder sql = new StringBuilder();
		sql.append("select lt.bpm_instance_id bpmInstanceId, lt.userid userid from "+Constants.MARKETING_SCHEMA+".lead_task lt ")
		   .append("left join "+Constants.MARKETING_SCHEMA+".campaign_receiver cr ")
		   .append("on lt.bpm_instance_id=cr.bpm_inst_id ")
		   .append("where lt.source_type=3 and lt.status in('0','1') ")
		   .append("and cr.bpm_inst_id is null ")
		   .append("and lt.contact_id in ")
		   .append("(select lt1.contact_id ")
		   .append("from ").append(Constants.MARKETING_SCHEMA).append(".lead_task lt1 ")
		   .append("where lt1.contact_id = lt.contact_id ")
		   .append("and lt1.bpm_instance_id != lt.bpm_instance_id ")
		   .append("and lt1.create_date > (lt.create_date - interval '1' hour) ")
		   .append("and lt1.create_date < (lt.create_date + interval '1' hour) ")
		   .append(")");
		
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString())
				.addScalar("bpmInstanceId", StringType.INSTANCE)
				.addScalar("userid", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(LeadTask.class));
		List<LeadTask> leadTasks = sqlQuery.list();
		return leadTasks;
	}
	
	public int updatePotential2Normal(String contactId, String potentialContactId) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".lead_task lt ")
           .append(" set lt.contact_id =:contactId, lt.is_potential = 0 ")
           .append(" where lt.contact_id =:potentialContactId and lt.is_potential=1");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("potentialContactId", potentialContactId);
        sqlQuery.setString("contactId", contactId);
        int num = sqlQuery.executeUpdate();
        return num;
	}
	
	public int updateContact(String instId, String contactId, boolean isPotential) {
		StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".lead_task lt ");
        if(isPotential) {
        	sql.append("set lt.contact_id =:contactId, lt.is_potential=1");
        } else {
        	sql.append("set lt.contact_id =:contactId, lt.is_potential=0");
        }
        sql.append(" where lt.BPM_INSTANCE_ID=:instId");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("contactId", contactId);
        sqlQuery.setString("instId", instId);
        int num = sqlQuery.executeUpdate();
        return num;
	}

	protected void genHibernateSql(StringBuilder sql, CampaignTaskDto mTaskDto) {
		if (StringUtils.isNotBlank(mTaskDto.getCampaignName())) {
			sql.append(" AND ca.name like :campaignName");
		}
		if (StringUtils.isNotBlank(mTaskDto.getStartDate())) {
			//sql.append(" AND to_char(m.create_date,'yyyy-mm-dd hh24:mi:ss') >= :startDate");
			sql.append(" AND m.create_date >= to_date(:startDate, 'yyyy-mm-dd hh24:mi:ss')");
		}

		if (StringUtils.isNotBlank(mTaskDto.getEndDate())) {
			//sql.append(" AND to_char(m.create_date,'yyyy-mm-dd hh24:mi:ss') <= :endDate");
			sql.append(" AND m.create_date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss')");
		}

		if (StringUtils.isNotBlank(mTaskDto.getCustomerID())) {
			sql.append(" AND (con.contactid = :customerID or pc.id= :customerIDPotential)" );
		}

		if (StringUtils.isNotBlank(mTaskDto.getCustomerName())) {
			sql.append(" AND (con.name like :customerName or pc.name= :customerNamePotential)");
		}
/*		if (StringUtils.isNotBlank(mTaskDto.getTaskType())
				&& !StringUtils.isNotBlank(mTaskDto.getTaskType())) {
			sql.append(" AND ltp.name = :taskType");
		}*/
		
		if (mTaskDto.getTaskSourceType() != null) {
			sql.append(" AND m.SOURCE_TYPE = :sourceType");
		}
		if (StringUtils.isNotBlank(mTaskDto.getStatus())) {
			sql.append(" AND m.status = :status");
		} else if(mTaskDto.getStatuses() != null && mTaskDto.getStatuses().size() > 0){
			sql.append(" and m.status in(:statuses) ");
		} else if(StringUtils.isBlank(mTaskDto.getInstId())){
            sql.append(" and m.status in(")
                    .append(String.valueOf(CampaignTaskStatus.INITIALIZED.getIndex())).append(",")
                    .append(String.valueOf(CampaignTaskStatus.ACTIVE.getIndex())).append(")");
        }
		if (StringUtils.isNotBlank(mTaskDto.getUserID())) {
			sql.append(" AND m.userID = :userID");
		}
		if (StringUtils.isNotBlank(mTaskDto.getOwner())) {
			sql.append(" AND le.owner = :owner");
		}
		if (StringUtils.isNotBlank(mTaskDto.getInstId())) {
			sql.append(" AND m.BPM_INSTANCE_ID = :instId");
		}
	}

	private void assignValue(Query sqlQuery, CampaignTaskDto mTaskDto) {
		if (StringUtils.isNotBlank(mTaskDto.getCampaignName())) {
			sqlQuery.setString("campaignName", mTaskDto.getCampaignName());
		}
		if (StringUtils.isNotBlank(mTaskDto.getUserID())) {
			sqlQuery.setString("userID", mTaskDto.getUserID());
		}
		if (StringUtils.isNotBlank(mTaskDto.getOwner())) {
			sqlQuery.setString("owner", mTaskDto.getOwner());
		}
		if (StringUtils.isNotBlank(mTaskDto.getStartDate())) {
			sqlQuery.setString("startDate", mTaskDto.getStartDate());
		}
		if (StringUtils.isNotBlank(mTaskDto.getEndDate())) {
			sqlQuery.setString("endDate", mTaskDto.getEndDate());
		}
		if (StringUtils.isNotBlank(mTaskDto.getCustomerID())) {
			sqlQuery.setString("customerID", mTaskDto.getCustomerID());
			sqlQuery.setString("customerIDPotential", mTaskDto.getCustomerID());
		}
		if (StringUtils.isNotBlank(mTaskDto.getCustomerName())) {
			sqlQuery.setString("customerName", mTaskDto.getCustomerName());
			sqlQuery.setString("customerNamePotential", mTaskDto.getCustomerName());
		}
		if (StringUtils.isNotBlank(mTaskDto.getStatus())) {
			sqlQuery.setString("status", mTaskDto.getStatus());
		} else if(mTaskDto.getStatuses() != null && mTaskDto.getStatuses().size() > 0) {
			sqlQuery.setParameterList("statuses", mTaskDto.getStatuses());
		}
		if (mTaskDto.getTaskSourceType() != null) {
			sqlQuery.setInteger("sourceType", mTaskDto.getTaskSourceType());
		}
		if (StringUtils.isNotBlank(mTaskDto.getInstId())) {
			sqlQuery.setString("instId", mTaskDto.getInstId());
		}
	}
	
	
}
