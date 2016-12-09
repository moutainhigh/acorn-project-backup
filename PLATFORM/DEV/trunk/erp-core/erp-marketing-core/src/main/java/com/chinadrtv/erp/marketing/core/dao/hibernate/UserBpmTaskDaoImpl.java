/*
 * @(#)LeadTaskDaoImpl.java 1.0 2013-5-10上午11:36:39
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dao.UserBpmTaskDao;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskVO;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:36:39
 * 
 */
@SuppressWarnings("all")
@Repository
public class UserBpmTaskDaoImpl extends
		GenericDaoHibernate<UserBpmTask, java.lang.Long> implements Serializable,
		UserBpmTaskDao {
	public UserBpmTaskDaoImpl() {
		super(UserBpmTask.class);
	}
	
	public List<UserBpmTask> queryApprovingTaskByBatchID(String batchID) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt where ubt.USER_BPM_ID=:batchID");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sqlBuilder.toString());
		sqlQuery.setString("batchID", batchID);
		List<UserBpmTask> objList = sqlQuery.addEntity(UserBpmTask.class).list();
		return objList;
	}
	public int queryNumOfProcessedTaskByBatchID(String batchID) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select count(*) from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt where 1=1 ")
				  .append("and ubt.USER_BPM_ID=:batchID ")
				  .append("and ubt.status != '").append(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex())).append("'");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sqlBuilder.toString());
		sqlQuery.setString("batchID", batchID);
		Object obj = sqlQuery.uniqueResult();
		int number = ((BigDecimal)obj).intValue();
		return number;
	}

    public List<UserBpmTask> queryTasksByParentInstId(String instId) {
        Session session = getSessionFactory().getCurrentSession();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from ")
                .append(Constants.MARKETING_SCHEMA)
                .append(".user_bpm_task ubt where ubt.PARENT_INS_ID=:instId");
        SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString()).addEntity(UserBpmTask.class);
        sqlQuery.setString("instId", instId);

        List<UserBpmTask> userBpmTasks = (List<UserBpmTask>)sqlQuery.list();
        for(UserBpmTask subtask : userBpmTasks) {
            session.evict(subtask);
        }
        return userBpmTasks;
    }

	public UserBpmTask queryTaskByInstId(String instId) {
        Session session = getSessionFactory().getCurrentSession();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt where ubt.bpm_ins_id=:instId");
		SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString()).addEntity(UserBpmTask.class);
		sqlQuery.setString("instId", instId);
		UserBpmTask userBpmTask = (UserBpmTask)sqlQuery.uniqueResult();
        evictUserBpmTask(session, userBpmTask);
		return userBpmTask;
	}
	
	//ugly, just for the non->ems
	public OrderChange queryOrderChangeByInstId(String instId) {
        Session session = getSessionFactory().getCurrentSession();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select oc.* from ")
				  .append(Constants.IAGENT_SCHEMA)
				  .append(".order_change oc left join ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt on ubt.change_obj_id=oc.order_change_id ")
				  .append("where ubt.bpm_ins_id=:instId");
		SQLQuery sqlQuery = session.createSQLQuery(sqlBuilder.toString()).addEntity(OrderChange.class);
		sqlQuery.setString("instId", instId);
		OrderChange orderChange = (OrderChange)sqlQuery.uniqueResult();
		return orderChange;
	}

    private void evictUserBpmTask(Session session, UserBpmTask userBpmTask){
        for(UserBpmTask subtask : userBpmTask.getSubTasks()){
            evictUserBpmTask(session, subtask);
        }
        session.evict(userBpmTask);
    }
	
	public List<UserBpmTask> queryUnProcessedContactBaseChangeTask(String contactId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select ubt.* from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt ")
				  .append("left join "+Constants.MARKETING_SCHEMA+".user_bpm ub on ub.id = ubt.USER_BPM_ID ")
				  .append("left join "+Constants.IAGENT_SCHEMA+".contact_change cc on ubt.bpm_ins_id=cc.proc_inst_id ")
				  .append("where 1=1 ")
				  .append("and ub.contact_id =:contactId ")
				  .append("and ubt.busi_type = ").append(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()).append(" ")
				  .append("and ubt.status in (").append(AuditTaskStatus.UNASSIGNED.getIndex())
				  .append(",").append(AuditTaskStatus.REJECTED.getIndex()).append(") ")
				  .append("and cc.name is not null");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sqlBuilder.toString());
		sqlQuery.setString("contactId", contactId);
		List<UserBpmTask> objList = sqlQuery.addEntity(UserBpmTask.class).list();
		return objList;
	}
	
	public List<UserBpmTask> queryUnterminatedOrderChangeTask(String orderId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select ubt.* from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt ")
				  .append("left join "+Constants.MARKETING_SCHEMA+".user_bpm ub on ub.id = ubt.USER_BPM_ID ")
				  .append("where 1=1 ")
				  .append("and ub.order_id =:orderId ")
				  .append("and (ubt.status in (")
				  .append(AuditTaskStatus.UNASSIGNED.getIndex())
				  .append(",").append(AuditTaskStatus.ASSITANTWAITING.getIndex())
				  .append(") or (ubt.status = 2 and ub.busi_type in (0, 1)")
				  .append(")) ");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sqlBuilder.toString());
		sqlQuery.setString("orderId", orderId);
		List<UserBpmTask> objList = sqlQuery.addEntity(UserBpmTask.class).list();
		return objList;
	}
	
	public int updateUserTaskStatus(String instID, String status, String approveRemark) {
		StringBuilder sql = new StringBuilder("update ");
		sql.append(Constants.MARKETING_SCHEMA);
		sql.append(".user_bpm_task ubt set ubt.STATUS=");
		sql.append(status);
		if(StringUtils.isNotBlank(approveRemark)) {
			sql.append(",ubt.approve_remark=:approveRemark");
		}
		sql.append(" where ubt.BPM_INS_ID =:instID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("instID", instID);
		if(StringUtils.isNotBlank(approveRemark)) {
			sqlQuery.setString("approveRemark", approveRemark);
		}
		int updatedMum = sqlQuery.executeUpdate();
		return updatedMum;
	}
	
	public List<Object> filterPendingInstances(List<String> instances) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ubt.bpm_ins_id from ")
		   .append(Constants.MARKETING_SCHEMA)
		   .append(".user_bpm_task ubt where ubt.BPM_INS_ID in(:instances) and status=:status");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setParameterList("instances", instances);
		sqlQuery.setString("status", String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
		List<Object> status = sqlQuery.list();
		return status;
	}
	
	public List<Object> getChangeObjIdByBatchIdOrInstanceId(String batchId, String instanceId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ubt.change_obj_id from ")
		   .append(Constants.MARKETING_SCHEMA)
		   .append(".user_bpm_task ubt where 1=1 ");
		if(StringUtils.isNotBlank(batchId)) {
			sql.append(" and ubt.USER_BPM_ID=:batchId ");
		}
		if(StringUtils.isNotBlank(instanceId)) {
			sql.append(" and ubt.bpm_ins_id=:instanceId ");
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		if(StringUtils.isNotBlank(batchId)) {
			sqlQuery.setString("batchId", batchId);
		}
		if(StringUtils.isNotBlank(instanceId)) {
			sqlQuery.setString("instanceId", instanceId);
		}
		List<Object> changeIds = sqlQuery.list();
		return changeIds;
	}
	
	public List<UserBpmTask> queryManagerApprovedEmsChangeProcess(String startDate, String endDate, boolean isEms, int start, int end) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select ubt.* from ")
			.append(Constants.MARKETING_SCHEMA)
		   .append(".user_bpm_task ubt ")
		   .append("left join "+Constants.MARKETING_SCHEMA+".user_bpm ub on ub.id = ubt.USER_BPM_ID ")
		   .append(" where 1=1 and ubt.status='5' ")
		   .append(" and ubt.BUSI_TYPE='8' ");
		if (StringUtils.isNotBlank(startDate)) {
			sql.append(" AND ub.create_date >= to_date(:startDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" AND ub.create_date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		sql.append(" order by ub.create_date")
		   .append(" ) A where rownum<:end ) where rn >:start");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		if (StringUtils.isNotBlank(startDate)) {
			sqlQuery.setString("startDate", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			sqlQuery.setString("endDate", endDate);
		}
		sqlQuery.setInteger("end", end);
		sqlQuery.setInteger("start", start);
		List<UserBpmTask> managerApprovedEmsChangeProcesses = sqlQuery.addEntity(UserBpmTask.class).list();
		return managerApprovedEmsChangeProcesses;
	}
	
	public int queryManagerApprovedEmsChangeProcessCount(String startDate, String endDate, boolean isEms, int start, int end) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from ")
			.append(Constants.MARKETING_SCHEMA)
		   .append(".user_bpm_task ubt ")
		   .append(" left join "+Constants.MARKETING_SCHEMA+".user_bpm ub on ub.id = ubt.USER_BPM_ID ")
		   .append("  where 1=1 and ubt.status='5' ")
		   .append(" and ubt.BUSI_TYPE='8' ");
		if (StringUtils.isNotBlank(startDate)) {
			sql.append(" AND ub.create_date >= to_date(:startDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append(" AND ub.create_date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		if (StringUtils.isNotBlank(startDate)) {
			sqlQuery.setString("startDate", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			sqlQuery.setString("endDate", endDate);
		}
		int number = ((BigDecimal)sqlQuery.uniqueResult()).intValue();
		return number;
	}
	public static void main(String[] args) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select ubt.* from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm_task ubt ")
				  .append("left join "+Constants.MARKETING_SCHEMA+".user_bpm ub on ub.id = ubt.USER_BPM_ID ")
				  .append("where 1=1 ")
				  .append("and ub.order_id =:orderId ")
				  .append("and (ubt.status in (")
				  .append(AuditTaskStatus.UNASSIGNED.getIndex())
				  .append(",").append(AuditTaskStatus.ASSITANTWAITING.getIndex())
				  .append(") or (ubt.status = 2 and ub.busi_type in (0, 1)")
				  .append(")) ");
		System.out.println(sqlBuilder.toString());
	}
}
