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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.UserBpmDao;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskVO;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;

/**
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:36:39
 */
@SuppressWarnings("all")
@Repository
public class UserBpmDaoImpl extends
		GenericDaoHibernate<UserBpm, java.lang.Long> implements Serializable,
		UserBpmDao {
	private static String TABLE_NICK_NAME = "ub";
	public UserBpmDaoImpl() {
		super(UserBpm.class);
	}

	public List<ApprovingTaskVO> queryChangeRequest(ApprovingTaskDto aTaskDto,
			DataGridModel dataModel) {
		StringBuilder sb = new StringBuilder();
		//field
		sb.append("select * from (select A.*, rownum rn from ( select ub.id id, ").append(TABLE_NICK_NAME).append(".busi_type busiType, ")
		  .append(TABLE_NICK_NAME).append(".create_date crDate,")
		  .append(TABLE_NICK_NAME).append(".contact_id cID, con.name cName, pc.contact_id pcid, pc.name pcName,")
		  .append(TABLE_NICK_NAME).append(".order_id orderID,")
		  .append(TABLE_NICK_NAME).append(".status status,ub.create_user crUser,")
		  .append("oh.crusr orCrUsrID, ub.work_group orCrUsrGrpName ")
		  //from clause
		  .append("from ").append(Constants.MARKETING_SCHEMA).append(".user_bpm ub ")
		  .append("left join "+Constants.IAGENT_SCHEMA+".orderhist oh on ").append(TABLE_NICK_NAME).append(".order_id= oh.orderid ")
		  .append("left join "+Constants.IAGENT_SCHEMA+".contact con on ").append(TABLE_NICK_NAME).append(".contact_id=con.contactid ")
		  .append("left join ").append(Constants.MARKETING_SCHEMA).append(".potential_contact pc on ").append(TABLE_NICK_NAME).append(".contact_id=pc.contact_id ")
		  //where clause
		  .append("where 1=1 ");
		
		genHibernateSql(sb, aTaskDto);
		sb.append(" order by ub.status,crDate desc");
		sb.append(" ) A where rownum<=:rows ) where rn >:page");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());

		assignValue(sqlQuery, aTaskDto);
		sqlQuery.setInteger("page", dataModel.getStartRow());
		sqlQuery.setInteger("rows", dataModel.getStartRow()+dataModel.getRows());
		
		List<ApprovingTaskVO> objList = sqlQuery.addEntity(ApprovingTaskVO.class).list();
		return objList;
	}
	
	public List<UserBpm> queryUnCompletedOrderChangeRequest(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ub.id from ")
		  .append(Constants.MARKETING_SCHEMA).append(".user_bpm ub ")
		  .append("left join ").append(Constants.MARKETING_SCHEMA).append(".user_bpm_task ubt on ub.id = ubt.USER_BPM_ID ")
		  .append("where ub.order_id =:orderId ")
		  .append("and ubt.status in (")
		  .append(AuditTaskStatus.UNASSIGNED.getIndex()).append(",")
		  .append(AuditTaskStatus.ASSITANTWAITING.getIndex()).append(",")
		  .append(AuditTaskStatus.REJECTED.getIndex()).append(") ")
		  .append("group by ub.id");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString())
				.addScalar("id", LongType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(UserBpm.class));
		sqlQuery.setString("orderId", orderId);
		List<UserBpm> userBpms = sqlQuery.list();
		return userBpms;
	}
	
	public UserBpm queryApprovingTaskById(String batchId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ")
				  .append(Constants.MARKETING_SCHEMA)
				  .append(".user_bpm ub where ub.id=:batchId");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sqlBuilder.toString());
		sqlQuery.setString("batchId", batchId);
		UserBpm ub = (UserBpm) sqlQuery.addEntity(UserBpm.class).uniqueResult();
		this.getSessionFactory().getCurrentSession().evict(ub);
		return ub;
	}
	
	public int cancelChangeRequestByBatchID(String batchID) {
		StringBuilder sql = new StringBuilder("update "+Constants.MARKETING_SCHEMA+".user_bpm ub set ub.STATUS=");
		sql.append(String.valueOf(AuditTaskStatus.CANCELED.getIndex()));
		sql.append(" where ub.id =:batchID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("batchID", batchID);
		int updatedMum = sqlQuery.executeUpdate();
		return updatedMum;
	}
	
	public int updateChangeRequestStatus(String batchID, String status) {
		StringBuilder sql = new StringBuilder("update "+Constants.MARKETING_SCHEMA+".user_bpm ub set ub.STATUS=");
		sql.append(status);
		sql.append(" where ub.id =:batchID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("batchID", batchID);
		int updatedMum = sqlQuery.executeUpdate();
		return updatedMum;
	}
	
	public int updateChangeRequestUser(String batchID, String approveUser, Date approveDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Constants.MARKETING_SCHEMA)
		   .append(".user_bpm ub ")
		   .append("set ub.APPROVE_USER=:approveUser,")
		   .append("ub.APPROVE_DATE=:approveDate")
		   .append(" where ub.id =:batchID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setDate("approveDate", approveDate);
		sqlQuery.setString("approveUser", approveUser);
		sqlQuery.setLong("batchID", Long.valueOf(batchID));
		int updatedMum = sqlQuery.executeUpdate();
		return updatedMum;
	}
	
	public String getChangeRequestRemark(String batchID) {
		StringBuilder sql = new StringBuilder("select ub.status from "+Constants.MARKETING_SCHEMA+".user_bpm ub");
		sql.append(" where ub.id =:batchID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("batchID", batchID);
		List<Object> objList = sqlQuery.list();
		return (String)objList.get(0);
	}
	
	public String getChangeRequestStatus(String batchID) {
		StringBuilder sql = new StringBuilder("select ub.status from "+Constants.MARKETING_SCHEMA+".user_bpm ub");
		sql.append(" where ub.id =:batchID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("batchID", batchID);
		List<Object> objList = sqlQuery.list();
		return (String)objList.get(0);
	}
	
	public Integer queryCount(ApprovingTaskDto aTaskDto) {
		StringBuilder sb = new StringBuilder();
		//field
		sb.append("select count(ub.id) ")
		  //from clause
		  .append("from ").append(Constants.MARKETING_SCHEMA).append(".user_bpm ub ")
		  .append("left join "+Constants.IAGENT_SCHEMA+".orderhist oh on ").append(TABLE_NICK_NAME).append(".order_id= oh.orderid ")
		  .append("left join "+Constants.IAGENT_SCHEMA+".contact con on ").append(TABLE_NICK_NAME).append(".contact_id=con.contactid ")
		  .append("left join ").append(Constants.MARKETING_SCHEMA).append(".potential_contact pc on ").append(TABLE_NICK_NAME).append(".contact_id=pc.contact_id ")
		  //where clause
		  .append("where 1=1 ");
		
		genHibernateSql(sb, aTaskDto);
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());
		assignValue(sqlQuery, aTaskDto);
		List<Object> objList = sqlQuery.list();
		int count = ((BigDecimal)objList.get(0)).intValue();
		return count;
	}

	protected void genHibernateSql(StringBuilder sql, ApprovingTaskDto aTaskDto) {
		if (StringUtils.isNotBlank(aTaskDto.getTaskType())) {
			sql.append(" AND ub.busi_type =:taskType");
		}
		if(aTaskDto.getTaskTypes() != null) {
			sql.append(" AND ub.busi_type in(:taskTypes)");
		}
		if (StringUtils.isNotBlank(aTaskDto.getTaskStatus())) {
			sql.append(" AND ub.status =:status");
		} else if(aTaskDto.getTaskStatuses() != null && aTaskDto.getTaskStatuses().size() > 0 ) {
			sql.append(" AND ub.status in(:statuses)");
		}
		if (StringUtils.isNotBlank(aTaskDto.getStartDate())) {
			sql.append(" AND ub.create_date >= to_date(:startDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(aTaskDto.getEndDate())) {
			sql.append(" AND ub.create_date <= to_date(:endDate, 'yyyy-mm-dd hh24:mi:ss')");
		}
		if (StringUtils.isNotBlank(aTaskDto.getContactID())) {
			sql.append(" AND (con.contactid = :contactid or pc.contact_id= :customerIDPotential)" );
		}
		if (StringUtils.isNotBlank(aTaskDto.getContactName())) {
			sql.append(" AND (con.name = :contactName or pc.name= :contactNamePotential)" );
		}
		if (StringUtils.isNotBlank(aTaskDto.getOrderID())) {
			sql.append(" AND ub.order_id =:orderid");
		}
		if (StringUtils.isNotBlank(aTaskDto.getAppliedUserID())) {
			sql.append(" AND ub.create_user =:createUser");
		}
		if (StringUtils.isNotBlank(aTaskDto.getOrderCreatedUserID())) {
			sql.append(" AND oh.crusr = :ordercr");
		}
		if (StringUtils.isNotBlank(aTaskDto.getDepartment())) {
			sql.append(" AND ub.department = :department");
		}
		if (StringUtils.isNotBlank(aTaskDto.getWorkGrp())) {
			sql.append(" AND ub.work_group = :workGroup");
		}
	}
	
	private void assignValue(Query sqlQuery, ApprovingTaskDto aTaskDto) {
		if (StringUtils.isNotBlank(aTaskDto.getTaskType())) {
			sqlQuery.setString("taskType", aTaskDto.getTaskType());
		}
		if(aTaskDto.getTaskTypes() != null) {
			sqlQuery.setParameterList("taskTypes", aTaskDto.getTaskTypes());
		}
		if (StringUtils.isNotBlank(aTaskDto.getTaskStatus())) {
			sqlQuery.setString("status", aTaskDto.getTaskStatus());
		} else if(aTaskDto.getTaskStatuses() != null && aTaskDto.getTaskStatuses().size() > 0 ) {
			sqlQuery.setParameterList("statuses", aTaskDto.getTaskStatuses());
		}
		if (StringUtils.isNotBlank(aTaskDto.getStartDate())) {
			sqlQuery.setString("startDate", aTaskDto.getStartDate());
		}
		if (StringUtils.isNotBlank(aTaskDto.getEndDate())) {
			sqlQuery.setString("endDate", aTaskDto.getEndDate());
		}
		if (StringUtils.isNotBlank(aTaskDto.getContactID())) {
			sqlQuery.setString("contactid", aTaskDto.getContactID());
			sqlQuery.setString("customerIDPotential", aTaskDto.getContactID());
		}
		if (StringUtils.isNotBlank(aTaskDto.getContactName())) {
			sqlQuery.setString("contactName", aTaskDto.getContactName());
			sqlQuery.setString("contactNamePotential", aTaskDto.getContactName());
		}
		if (StringUtils.isNotBlank(aTaskDto.getOrderID())) {
			sqlQuery.setString("orderid", aTaskDto.getOrderID());
		}
		if (StringUtils.isNotBlank(aTaskDto.getAppliedUserID())) {
			sqlQuery.setString("createUser", aTaskDto.getAppliedUserID());
		}
		if (StringUtils.isNotBlank(aTaskDto.getOrderCreatedUserID())) {
			sqlQuery.setString("ordercr", aTaskDto.getOrderCreatedUserID());
		}
		if (StringUtils.isNotBlank(aTaskDto.getDepartment())) {
			sqlQuery.setString("department", aTaskDto.getDepartment());
		}
		if (StringUtils.isNotBlank(aTaskDto.getWorkGrp())) {
			sqlQuery.setString("workGroup", aTaskDto.getWorkGrp());
		}
	}
	
}
