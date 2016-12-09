/*
 * @(#)LeadInterActionDaoImpl.java 1.0 2013-5-21下午1:18:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;

/**
 * @author yangfei
 * @author cuiming
 * @version 1.0
 * @since 2013-5-21 下午1:18:19
 * 
 */
@Repository
public class LeadInterActionDaoImpl extends
		GenericDaoHibernate<LeadInteraction, java.lang.Long> implements
		LeadInterActionDao {

	public LeadInterActionDaoImpl() {
		super(LeadInteraction.class);
	}

	/**
	 * @description 新增交互记录
	 */
	public void insertLeadInterAction(LeadInteraction leadInteraction) {
		getHibernateTemplate().save(leadInteraction);
	}
	
	/** 
	 * <p>Title: querySendHistoryCount</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadInteractionOrderDao#querySendHistoryCount(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public Integer querySendHistoryCount(String contactId) {
		String hql = " select count(li) from LeadInteraction li " +
					 " where li.contactId=:contactId and li.interActionType='"+LeadInteractionType.SMS.getIndex()+"' and li.status='0' "+
					 " order by li.createDate desc ";
		Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
		paramMap.put("contactId", new ParameterString("contactId", contactId));
		return this.findPageCount(hql, paramMap);
	}

	/** 
	 * <p>Title: querySendHistory</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @param dataGridModel
	 * @return List<LeadInteraction>
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadInteractionOrderDao#querySendHistory(java.lang.String, com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public List<LeadInteraction> querySendHistory(String contactId, DataGridModel dataGridModel) {
		String hql = " select li from LeadInteraction li " +
					 " where li.contactId=:contactId and li.interActionType='"+LeadInteractionType.SMS.getIndex()+"' and li.status='0' " +
					 " order by li.createDate desc ";
		
		Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
		paramMap.put("contactId", new ParameterString("contactId", contactId));
		
		Parameter page = new ParameterInteger("page", dataGridModel.getStartRow());
		page.setForPageQuery(true);
		paramMap.put("page", page);

		Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
		rows.setForPageQuery(true);
		paramMap.put("rows", rows);
		
		return this.findPageList(hql, paramMap);
	}
	
	public LeadInteraction getLatestPhoneInterationByInstId(String instId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select li.id,li.lead_id from ")
		   .append(Constants.MARKETING_SCHEMA).append(".lead_interaction li ")
		   .append("left join ").append(Constants.MARKETING_SCHEMA).append(".lead_task lt ")
		   .append("on lt.lead_id = li.lead_id ")
		   .append("where li.interaction_type != '").append(LeadInteractionType.SMS.getIndexString()).append("' ")
		   .append("and lt.bpm_instance_id =:instId ")
		   .append("order by li.create_date desc")
		   .append(" ) A where rownum<=:rows ) where rn >:page");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("instId", instId);
		sqlQuery.setInteger("page", 0);
		sqlQuery.setInteger("rows", 1);

        Object obj=sqlQuery.uniqueResult();
        if(obj!=null)
        {
            Object[] datas=(Object[])obj;
            LeadInteraction leadInteraction=new LeadInteraction();
            if(datas[0]!=null)
                leadInteraction.setId(Long.parseLong(datas[0].toString()));
            if(datas[1]!=null)
                leadInteraction.setLeadId(Long.parseLong(datas[1].toString()));
            return leadInteraction;
        }
        return null;
		//LeadInteraction leadInteraction = (LeadInteraction)sqlQuery.addEntity(LeadInteraction.class).uniqueResult();
		//return leadInteraction;
	}
	
	public LeadInteraction getLatestPhoneInterationByLeadId(Long leadId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select * from ")
		   .append(Constants.MARKETING_SCHEMA).append(".lead_interaction li ")
		   .append("where li.interaction_type != '").append(LeadInteractionType.SMS.getIndexString()).append("' ")
		   .append("and li.lead_id =:leadId ")
		   .append("order by li.create_date desc ")
		   .append(") A where rownum<=:rows ) where rn >:page");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setLong("leadId", leadId);
		sqlQuery.setInteger("page", 0);
		sqlQuery.setInteger("rows", 1);
		LeadInteraction leadInteraction = (LeadInteraction)sqlQuery.addEntity(LeadInteraction.class).uniqueResult();
		return leadInteraction;
	}
	
    public int updatePotential2Normal(String contactId, String potentialContactId) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".LEAD_INTERACTION li ")
           .append(" set li.contact_id =:contactId ")
           .append(" where li.contact_id =:potentialContactId");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("potentialContactId", potentialContactId);
        sqlQuery.setString("contactId", contactId);
        int num = sqlQuery.executeUpdate();
        return num;
    }
    
	public int updateContact(long leadId, String contactId, boolean isPotential) {
		StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".lead_interaction li ");
        sql.append("set li.contact_id =:contactId ");
        sql.append(" where li.lead_id =:leadId");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("contactId", contactId);
        sqlQuery.setLong("leadId", leadId);
        int num = sqlQuery.executeUpdate();
        return num;
	}
	
	public int queryLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto) {
    	StringBuilder sql = new StringBuilder();
		sql.append("select count(li.id) ")
		   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ");
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
			   .append("on li.id = lio.lead_interaction_id ");
		}
		sql.append("where 1=1 ");
		genGeneralHibernateSql(sql, leadInteractionSearchDto, true, false);
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, leadInteractionSearchDto, true);
		Object obj = sqlQuery.uniqueResult();
		int count = ((BigDecimal) obj).intValue();
		return count;
    }
	
	public Map<String, AgentUserInfo4TeleDist> queryLeadInteractionCountInBatch(LeadInteractionSearchDto leadInteractionSearchDto) {
    	StringBuilder sql = new StringBuilder();
		sql.append("select /*+ INDEX(li IDX_MYCUSTOMER) */ li.create_user userId, count(li.id) intradayInlineCount ")
		   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ");
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
			   .append("on li.id = lio.lead_interaction_id ");
		}
		sql.append("where 1=1 ");
		genGeneralHibernateSql(sql, leadInteractionSearchDto, true, false);
		sql.append(" group by li.create_user");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString())
				.addScalar("userId", StringType.INSTANCE)
        		.addScalar("intradayInlineCount", IntegerType.INSTANCE)
        		.setResultTransformer(Transformers.aliasToBean(AgentUserInfo4TeleDist.class));
		assignValue(sqlQuery, leadInteractionSearchDto, true);
		List<AgentUserInfo4TeleDist> userCounts= sqlQuery.list();
		Map<String, AgentUserInfo4TeleDist> agentUsersMap = new HashMap<String, AgentUserInfo4TeleDist>();
		for(AgentUserInfo4TeleDist agentUser : userCounts) {
			agentUsersMap.put(agentUser.getUserId(), agentUser);
		}
		return agentUsersMap;
    }
    
/*    select *
    from acoapp_marketing.lead_interaction li
    left join acoapp_marketing.lead_interaction_order lio
      on li.id = lio.lead_interaction_id
   where li.time_length >= 20
     and li.time_length < 180
     and li.ani='15036233666'
     and li.dnis='24100000'
     and li.create_user='12650'
     and li.contact_id='929028298'
     and lio.order_id is null
     and li.is_call_back=0*/
    
    public int queryAllocatableLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto) {
    	StringBuilder sql = new StringBuilder();
    	if(leadInteractionSearchDto.isDeDuplicatedNeeded()) {
			sql.append("select count(li0.id) ")
			   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li0 ");
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le0 ")
			   .append("on li0.lead_id = le0.id ")
			   .append("where li0.rowid = (");
			sql.append("select max(li.rowid) ");
    	} else {
    		sql.append("select count(li.id) ");
    	}
		sql.append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ");
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
			   .append("on li.id = lio.lead_interaction_id ");
		}
		sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le ")
		   .append("on li.LEAD_ID = le.id ");
		sql.append("where 1=1 ");
		genGeneralHibernateSql(sql, leadInteractionSearchDto, true, leadInteractionSearchDto.isDeDuplicatedNeeded());
		if(!leadInteractionSearchDto.isDeptIdNotNullNeeded()) {
			genCallbackDeDuplicatedSql(sql);
		} else {
			sql.append(" AND li.DEPT_ID is null");
		}
		if(leadInteractionSearchDto.isDeDuplicatedNeeded()) {
			sql.append(")");
			if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingLowDate())) {
				sql.append(" AND li0.create_date >= TO_DATE(:incomingLowDate, 'YYYY-MM-DD HH24:MI:SS')");
			}
			if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingHighDate())) {
				sql.append(" AND li0.create_date <= TO_DATE(:incomingHighDate, 'YYYY-MM-DD HH24:MI:SS')");
			}
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, leadInteractionSearchDto, true);
		Object obj = sqlQuery.uniqueResult();
		int count = ((BigDecimal) obj).intValue();
		return count;
    }
    
    /**
     * select li0.*
  from ACOAPP_MARKETING.lead_interaction li0
  left join ACOAPP_MARKETING.lead le0
      on li0.lead_id = le0.id
 where li0.rowid =
       (select max(li.rowid)
          from ACOAPP_MARKETING.lead_interaction li
          left join ACOAPP_MARKETING.lead_interaction_order lio
            on li.id = lio.lead_interaction_id
          left join ACOAPP_MARKETING.lead le
            on li.lead_id = le.id
         where 1 = 1
           AND li.time_length >= 20
           AND li.time_length < 180
           AND li.create_date >=
               TO_DATE('2013-07-16', 'YYYY-MM-DD HH24:MI:SS')
           AND li.create_date <=
               TO_DATE('2013-07-30', 'YYYY-MM-DD HH24:MI:SS')
           AND li.create_user = '12650'
           and li.is_valid=1
           --AND li.contact_id = '929028298'
           and lio.order_id is null
           and li.allocated_number in (0, 1)
           and li.ani = li0.ani
           and li.contact_id = li0.contact_id
           and le0.CAMPAIGN_ID = le.CAMPAIGN_ID
           and (li.ani, li.contact_id, le.CAMPAIGN_ID) not in 
               (select cb.phn2, cb.CONTACT_ID, cb.MEDIAPLAN_ID 
                  from ACOAPP_MARKETING.Callback cb
                  left join ACOAPP_MARKETING.Lead_Task lt
                    on cb.task_id = lt.bpm_instance_id
                 where cb.phn2 = li.ani
                   and cb.contact_id = li.contact_id
                   and cb.MEDIAPLAN_ID = le.CAMPAIGN_ID
                   and  cb.task_id is not null
                   and lt.create_date < sysdate
                   and lt.create_date > (sysdate - interval '8' hour)))
     */
    public List<Object> queryAllocatableLeadInteraction(LeadInteractionSearchDto leadInteractionSearchDto) {
    	StringBuilder sql = new StringBuilder();
    	if(leadInteractionSearchDto.isDeDuplicatedNeeded()) {
			sql.append("select li0.id ")
			   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li0 ");
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le0 ")
			   .append("on li0.lead_id = le0.id ")
			   .append("where li0.rowid = (")
			   .append("select max(li.rowid) ");
    	} else {
    		sql.append("select li.id ");
    	}
		   sql.append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ");
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
			   .append("on li.id = lio.lead_interaction_id ");
		}
		sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le ")
		   .append("on li.LEAD_ID = le.id ");
		sql.append("where 1=1 ");
		genGeneralHibernateSql(sql, leadInteractionSearchDto, true, leadInteractionSearchDto.isDeDuplicatedNeeded());
		if(!leadInteractionSearchDto.isDeptIdNotNullNeeded()) {
			genCallbackDeDuplicatedSql(sql);
		} else {
			sql.append(" AND li.DEPT_ID is null");
		}
		if(leadInteractionSearchDto.isDeDuplicatedNeeded()) {
			sql.append(")");
			if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingLowDate())) {
				sql.append(" AND li0.create_date >= TO_DATE(:incomingLowDate, 'YYYY-MM-DD HH24:MI:SS')");
			}
			if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingHighDate())) {
				sql.append(" AND li0.create_date <= TO_DATE(:incomingHighDate, 'YYYY-MM-DD HH24:MI:SS')");
			}
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, leadInteractionSearchDto, true);
		List<Object> objs = sqlQuery.list();
		return objs;
    }
    
    /**
     *  select li.id
   from ACOAPP_MARKETING.lead_interaction li
   left join ACOAPP_MARKETING.lead_interaction_order lio
     on li.id = lio.lead_interaction_id
   left join ACOAPP_MARKETING.lead le
     on li.lead_id = le.id
  where 1 = 1
    AND li.time_length >= 20
    AND li.time_length < 180
    AND li.create_date >= TO_DATE('2013-07-16', 'YYYY-MM-DD HH24:MI:SS')
    AND li.create_date <= TO_DATE('2013-07-30', 'YYYY-MM-DD HH24:MI:SS')
    AND li.create_user = '12650'
       --AND li.contact_id = '929028298'
    and lio.order_id is null
    and li.allocated_number in (0, 1)
    and li.id not in(2057)
    and (li.ani, li.contact_id, le.CAMPAIGN_ID) in  
    (select li2.ani, li2.contact_id, le2.CAMPAIGN_ID 
       from ACOAPP_MARKETING.lead_interaction li2
   left join ACOAPP_MARKETING.lead le2
     on li2.lead_id = le2.id
     where li2.id in(2057))
    and (li.ani, li.contact_id, le.CAMPAIGN_ID) not in
        (select cb.phn2, cb.CONTACT_ID, cb.MEDIAPLAN_ID
           from ACOAPP_MARKETING.Callback cb
           left join ACOAPP_MARKETING.Lead_Task lt
             on cb.task_id = lt.bpm_instance_id
          where cb.phn2 = li.ani
            and cb.contact_id = li.contact_id
            and cb.MEDIAPLAN_ID = le.CAMPAIGN_ID
            and cb.task_id is not null
            and lt.create_date < sysdate        
            and lt.create_date > (sysdate - interval '8' hour))
     */
    public List<Object> queryObsoleteInteraction(LeadInteractionSearchDto leadInteractionSearchDto) {
    	StringBuilder sql = new StringBuilder();
		sql.append("select li.id ")
		   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ");
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
			   .append("on li.id = lio.lead_interaction_id ");
		}
		sql.append("left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le ")
		   .append("on li.LEAD_ID = le.id ");
		sql.append("where 1=1 ");
		genGeneralHibernateSql(sql, leadInteractionSearchDto, false, true);
		genCallbackDeDuplicatedSql(sql);
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, leadInteractionSearchDto, false);
		List<Object> objs = sqlQuery.list();
    	return objs;
    }
    
    /**
     * 
     * genHibernateSql
     * @param sql
     * @param leadInteractionSearchDto 
     * void
     * @exception 
     * @since  1.0.0
     */
    protected void genGeneralHibernateSql(StringBuilder sql, LeadInteractionSearchDto leadInteractionSearchDto, 
    		boolean selectionNeeded, boolean deDuplicatedNeeded) {
    	if(leadInteractionSearchDto.getLowCallDuration() != -1) {
    		//ugly, so?
    		if(leadInteractionSearchDto.getLowCallDuration() == 20) {
    			//开区间
    			sql.append(" AND li.time_length >:lowTimeLength");
    		} else {
    			sql.append(" AND li.time_length >=:lowTimeLength");
    		}
    	}
    	if(leadInteractionSearchDto.getHighCallDuration() != -1) {
    		if(leadInteractionSearchDto.getHighCallDuration() == 180) {
    			//开区间
    			sql.append(" AND li.time_length <:highTimeLength");
    		} else {
    			sql.append(" AND li.time_length <=:highTimeLength");
    		}
    	}
		if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingLowDate())) {
			sql.append(" AND li.create_date >= TO_DATE(:incomingLowDate, 'YYYY-MM-DD HH24:MI:SS')");
		}
		if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingHighDate())) {
			sql.append(" AND li.create_date <= TO_DATE(:incomingHighDate, 'YYYY-MM-DD HH24:MI:SS')");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getAni())) {
			sql.append(" AND li.ani=:ani");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getDnis())) {
			sql.append(" AND li.dnis=:dnis");
		}
		//added by youngphy, 0325; 去淘汰记录可以跨部门
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getDeptId()) && selectionNeeded) {
			sql.append(" AND li.DEPT_ID=:deptId");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getUserId())) {
			sql.append(" AND li.create_user=:userId");
		} else if(leadInteractionSearchDto.getUserIds() != null && leadInteractionSearchDto.getUserIds().size() > 0) {
			sql.append(" AND li.create_user in(:userIds)");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getContactId())) {
			sql.append(" AND li.contact_id=:contactId");
		}
		if(leadInteractionSearchDto.isOrderRecordEmilationNeeded()) {
			sql.append(" and lio.order_id is null");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getManagerGrp())) {
			sql.append(" and li.group_code in( ")
			   .append("select cbg.grpid ")
			   .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".callback_group cbg")
			   .append(" where cbg.grptype = 1")
			   .append(" and cbg.calltype = 3")
			   .append(" and cbg.active = 1 ")
			   .append(" and cbg.grpidm =:grpidm")
			   .append(")");
		}
		if(leadInteractionSearchDto.getAllocatedNumbers() != null && 
				leadInteractionSearchDto.getAllocatedNumbers().size() > 0) {
			sql.append(" and li.allocated_number in (:allocatedNumbers)");
		}
		sql.append(" and li.is_valid=1");
		sql.append(" and li.BPMINSTID is null");
		if(leadInteractionSearchDto.getLeadInteractionType() != null && 
				leadInteractionSearchDto.getLeadInteractionType().size() > 0) {
			sql.append(" and li.interaction_type in(:interactionTypes) ");
		}
		if(leadInteractionSearchDto.getCallType() != null && 
				leadInteractionSearchDto.getCallType().size() > 0) {
			sql.append(" and li.CALL_TYPE in(:callTypes) ");
		}
		if(leadInteractionSearchDto.getCallResult() != null && 
				leadInteractionSearchDto.getCallResult().size() > 0) {
			sql.append(" and li.CALL_RESULT in(:callResults) ");
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getAcdGroup())) {
			sql.append(" and li.ACDGROUP=:acdGroup ");
		} else if(leadInteractionSearchDto.getAcdGroups() != null && 
				leadInteractionSearchDto.getAcdGroups().size() > 0) {
			sql.append(" and li.ACDGROUP in(:acdGroups) ");
		}
		if(deDuplicatedNeeded) {
			if(selectionNeeded) {
				//根据ani,contact_id,CAMPAIGN_ID去重
				sql.append(" and li.ani = li0.ani")
				   .append(" and li.contact_id = li0.contact_id")
				   .append(" and le.CAMPAIGN_ID = le0.CAMPAIGN_ID");
			} else {
				sql.append(" and li.id not in (:selectedInteractions)");
				sql.append(" and (li.ani, li.contact_id, le.CAMPAIGN_ID) in (");
				sql.append(" select li2.ani, li2.contact_id, le2.CAMPAIGN_ID")
				   .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li2")
				   .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le2")
				   .append(" on li2.LEAD_ID = le2.id ")
				   .append(" where li2.id in(:selectedInteractions))");
			}
		}
	}
    
    protected void genCallbackDeDuplicatedSql(StringBuilder sql) {
		sql.append(" and (li.ani, li.contact_id, le.CAMPAIGN_ID) not in ( ")
		   .append("	select cb.phn2, cb.CONTACT_ID, cb.MEDIAPLAN_ID ")
		   .append("		from ACOAPP_MARKETING.Callback cb")
		   .append(" 		left join ACOAPP_MARKETING.Lead_Task lt on cb.task_id = lt.bpm_instance_id")
		   .append("	where cb.phn2 = li.ani")
		   .append("		and cb.contact_id = li.contact_id")
		   .append("		and cb.MEDIAPLAN_ID = le.CAMPAIGN_ID")
		   .append("		and cb.task_id is not null")
		   .append("		and cb.TYPE != '3'")
		   .append("		 and cb.dbdt > (sysdate - interval '8' hour))");
		sql.append(" and (li.ani, li.contact_id, le.CAMPAIGN_ID) not in ( ")
		   .append("	select cb.phn2, cb.latent_contact_id, cb.MEDIAPLAN_ID ")
		   .append("		from ACOAPP_MARKETING.Callback cb")
		   .append(" 		left join ACOAPP_MARKETING.Lead_Task lt on cb.task_id = lt.bpm_instance_id")
		   .append("	where cb.phn2 = li.ani")
		   .append("		and cb.latent_contact_id = li.contact_id")
		   .append("		and cb.MEDIAPLAN_ID = le.CAMPAIGN_ID")
		   .append("		and cb.task_id is not null")
		   .append("		and cb.TYPE != '3'")
		   .append("		 and cb.dbdt > (sysdate - interval '8' hour))");
    }

	private void assignValue(Query sqlQuery, LeadInteractionSearchDto leadInteractionSearchDto, boolean selectionNeeded) {
		if(leadInteractionSearchDto.getLowCallDuration() != -1) {
			sqlQuery.setInteger("lowTimeLength", leadInteractionSearchDto.getLowCallDuration());
    	}
    	if(leadInteractionSearchDto.getHighCallDuration() != -1) {
    		sqlQuery.setInteger("highTimeLength", leadInteractionSearchDto.getHighCallDuration());
    	}
		if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingLowDate())) {
			sqlQuery.setString("incomingLowDate", leadInteractionSearchDto.getIncomingLowDate());
		}
		if (StringUtils.isNotBlank(leadInteractionSearchDto.getIncomingHighDate())) {
			sqlQuery.setString("incomingHighDate", leadInteractionSearchDto.getIncomingHighDate());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getAni())) {
			sqlQuery.setString("ani", leadInteractionSearchDto.getAni());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getDnis())) {
			sqlQuery.setString("dnis", leadInteractionSearchDto.getDnis());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getDeptId()) && selectionNeeded) {
			sqlQuery.setString("deptId", leadInteractionSearchDto.getDeptId());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getUserId())) {
			sqlQuery.setString("userId", leadInteractionSearchDto.getUserId());
		} else if(leadInteractionSearchDto.getUserIds() != null && leadInteractionSearchDto.getUserIds().size() > 0) {
			sqlQuery.setParameterList("userIds", leadInteractionSearchDto.getUserIds());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getContactId())) {
			sqlQuery.setString("contactId", leadInteractionSearchDto.getContactId());
		}
		if(leadInteractionSearchDto.getAllocatedNumbers() != null && 
				leadInteractionSearchDto.getAllocatedNumbers().size() > 0) {
			sqlQuery.setParameterList("allocatedNumbers", leadInteractionSearchDto.getAllocatedNumbers());
		}
		if(!selectionNeeded) {
			sqlQuery.setParameterList("selectedInteractions", leadInteractionSearchDto.getSelectedInteractions());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getManagerGrp())) {
			sqlQuery.setString("grpidm", leadInteractionSearchDto.getManagerGrp());
		}
		if(leadInteractionSearchDto.getLeadInteractionType() != null && 
				leadInteractionSearchDto.getLeadInteractionType().size() > 0) {
			sqlQuery.setParameterList("interactionTypes", leadInteractionSearchDto.getLeadInteractionType());
		}
		if(leadInteractionSearchDto.getCallType() != null && 
				leadInteractionSearchDto.getCallType().size() > 0) {
			sqlQuery.setParameterList("callTypes", leadInteractionSearchDto.getCallType());
		}
		if(leadInteractionSearchDto.getCallResult() != null && 
				leadInteractionSearchDto.getCallResult().size() > 0) {
			sqlQuery.setParameterList("callResults", leadInteractionSearchDto.getCallResult());
		}
		if(StringUtils.isNotBlank(leadInteractionSearchDto.getAcdGroup())) {
			sqlQuery.setString("acdGroup", leadInteractionSearchDto.getAcdGroup());
		} else if(leadInteractionSearchDto.getAcdGroups() != null && 
				leadInteractionSearchDto.getAcdGroups().size() > 0) {
			sqlQuery.setParameterList("acdGroups", leadInteractionSearchDto.getAcdGroups());
		}
	}

	@Override
	public LeadInteraction queryByResponseCode(String uuid) {
		String hql = "select li from LeadInteraction li where li.interActionType='4' and li.appResponseCode=:uuid";
		return this.find(hql, new ParameterString("uuid", uuid));
	}
	
	//坐席要看到自己当天的呼出时长
/*	select sum(li.time_length) from acoapp_marketing.lead_interaction li where li.create_user='12650' 
			and to_date('2013-07-29','yyyy-mm-dd hh24:mi:ss') <= li.create_date
			and to_date('2013-07-30','yyyy-mm-dd hh24:mi:ss') >= li.create_date
			and li.interaction_type in('1','3')
			and li.ani not in('11805')*/
	public long queryTotalOutcallDuration(List<String> blackPhoneList, String agentId, String lowDate, String highDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(li.time_length) from ")
		   .append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li ")
		   .append("where 1=1 ")
		   .append("and li.create_user=:userId ")
		   .append("and to_date(:lowDate,'yyyy-mm-dd hh24:mi:ss') <= li.create_date ")
		   .append("and to_date(:highDate,'yyyy-mm-dd hh24:mi:ss') >= li.create_date ")
		   .append("and li.interaction_type in('1','3') ");
		if(blackPhoneList != null && blackPhoneList.size() > 0) {
			sql.append("and li.ani not in(:blackPhoneList)");
		}
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("userId", agentId);
		sqlQuery.setString("lowDate", lowDate);
		sqlQuery.setString("highDate", highDate);
		if(blackPhoneList != null && blackPhoneList.size() > 0) {
			sqlQuery.setParameterList("blackPhoneList", blackPhoneList);
		}
		Object obj = sqlQuery.uniqueResult();
		long totalTime = 0;
		if(obj != null) {
			totalTime = ((BigDecimal)obj).longValue();
		}
		return totalTime;
	}

	/** 
	 * <p>Title: updateDeptByIndex</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @param deptId
	 * @param affectedQty
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao#updateDeptByIndex(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto, java.lang.String, java.lang.Integer)
	 */ 
	@Override
	public Integer updateDeptByIndex(String leadInterationId, String deptId) {
		String sql = "update acoapp_marketing.lead_interaction li " +
						" set li.dept_id = :deptId "+
						" where 1=1 " + 
						" and li.id = :leadInterationId ";
		
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter("deptId", deptId);
		query.setLong("leadInterationId", Long.valueOf(leadInterationId));
		
		int affctedQty = query.executeUpdate();
		
		return affctedQty;
	}

}
