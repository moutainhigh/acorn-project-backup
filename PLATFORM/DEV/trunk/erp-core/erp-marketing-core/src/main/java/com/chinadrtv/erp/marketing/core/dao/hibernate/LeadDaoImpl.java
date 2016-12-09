/*
 * @(#)LeadDaoImpl.java 1.0 2013-5-9上午10:42:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.LeadStatus;
import com.chinadrtv.erp.marketing.core.dao.LeadDao;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.dto.LeadQueryDto;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-9 上午10:42:30
 * 
 */
@Repository
public class LeadDaoImpl extends GenericDaoHibernate<Lead, java.lang.Long>
		implements Serializable, LeadDao {
	
	public LeadDaoImpl() {
		super(Lead.class);
	}

	public void insertLead(Lead lead) {
		getHibernateTemplate().evict(lead);
		Long id = (Long)getHibernateTemplate().save(lead);
		lead.setId(id);
	}
	
	public Lead getLastestAliveLead(String agentId, String contactId, String campaignId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from (select le.* from ")
		   .append(Constants.MARKETING_SCHEMA).append(".lead le ")
		   .append("where le.status in(0,3,4,5)")
		   .append(" AND to_char(le.end_date,'yyyy-mm-dd hh24:mi:ss') >=:endDate ")
		   .append(" and le.owner =:agentId ")
		   .append(" and le.campaign_id =:campaignId ")
		   .append(" and (le.contact_id =:contactId or le.potential_contact_id=:potentialContactId) ")
		   .append(" order by le.create_date desc")
		   .append(" ) A where rownum<=:rows ) where rn >:page");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("agentId", agentId);
		sqlQuery.setString("contactId", contactId);
		sqlQuery.setString("potentialContactId", contactId);
		sqlQuery.setString("campaignId", campaignId);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String endDate = format.format(new Date());
		sqlQuery.setString("endDate", endDate);
		sqlQuery.setInteger("page", 0);
		sqlQuery.setInteger("rows", 1);
		Lead lead = (Lead)sqlQuery.addEntity(Lead.class).uniqueResult();
		return lead;
	}

    public Lead getLastestAliveLead(String agentId, String contactId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from (select A.*, rownum rn from (select le.* from ")
                .append(Constants.MARKETING_SCHEMA).append(".lead le ")
                .append("where le.status in(0,3,4,5)")
                .append(" AND to_char(le.end_date,'yyyy-mm-dd hh24:mi:ss') >=:endDate ")
                .append(" and le.owner =:agentId ")
                .append(" and (le.contact_id =:contactId or le.potential_contact_id=:potentialContactId) ")
                .append("order by le.create_date desc")
                .append(" ) A where rownum<=:rows ) where rn >:page");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("agentId", agentId);
        sqlQuery.setString("contactId", contactId);
        sqlQuery.setString("potentialContactId", contactId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String endDate = format.format(new Date());
		sqlQuery.setString("endDate", endDate);
		sqlQuery.setInteger("page", 0);
        sqlQuery.setInteger("rows", 1);
        Lead lead = (Lead)sqlQuery.addEntity(Lead.class).uniqueResult();
        return lead;
    }
    
    public int updateStatus4OverdueLead(long leadId) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ")
    	   .append(Constants.MARKETING_SCHEMA).append(".lead le set le.status=")
    	   .append(LeadStatus.SYSTEMSTOP.getIndex())
    	   .append(" where le.id =:leadId");
    	SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
    	sqlQuery.setLong("leadId", leadId);
    	int number = sqlQuery.executeUpdate();
    	return number;
    }
    
	public List<Object> queryOverdueLead() {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select le.id from ")
    	   .append(Constants.MARKETING_SCHEMA).append(".lead le ")
    	   .append(" where le.status != 2 and (le.end_date <=sysdate or le.end_date is null) ");
    	SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		List<Object> objs = sqlQuery.list();
		return objs;
	}

    public int updatePotential2Normal(String contactId, String potentialContactId) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".lead le ")
           .append(" set le.contact_id =:contactId, le.is_potential = 0 ")
           .append(" where le.potential_contact_id =:potentialContactId");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("potentialContactId", potentialContactId);
        sqlQuery.setString("contactId", contactId);
        int num = sqlQuery.executeUpdate();
        return num;
    }
    
    /**
     * update ACOAPP_MARKETING.lead le
   set le.potential_contact_id = '123'
  where le.id in (select lt.lead_id from ACOAPP_MARKETING.Lead_Task lt where lt.bpm_instance_id = '79779')
     */
	public int updateContact(String instId, String contactId, boolean isPotential) {
		StringBuilder sql = new StringBuilder();
        sql.append("update ").append(Constants.MARKETING_SCHEMA).append(".lead le ");
        if(!isPotential) {
        	sql.append("set le.contact_id =:contactId, le.POTENTIAL_CONTACT_ID='', le.IS_POTENTIAL=0 ");
        } else {
        	sql.append("set le.POTENTIAL_CONTACT_ID =:contactId, le.contact_id ='', le.IS_POTENTIAL=1 ");
        }
        sql.append(" where le.id in (select lt.lead_id from ")
           .append(Constants.MARKETING_SCHEMA)
           .append(".Lead_Task lt where lt.BPM_INSTANCE_ID=:instId)");
        SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        sqlQuery.setString("contactId", contactId);
        sqlQuery.setString("instId", instId);
        int num = sqlQuery.executeUpdate();
        return num;
	}
    
    public List<Map<String, Object>> query(LeadQueryDto leadQueryDto, DataGridModel dataModel) {
    	StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select ")
		   .append("le.id            leadId,")
		   .append("le.SOURCE_ID     sourceId,")
		   .append("le.CREATE_DATE   crdate,")
		   .append("le.ANI           ani,")
		   .append("le.CONTACT_ID    contactId,")
		   .append("le.POTENTIAL_CONTACT_ID   potentialContactId,")
		   .append("le.status       status")
		   .append("from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le ");
		   sql.append("where 1=1 ");
		genHibernateSql(sql, leadQueryDto);
		sql.append(" order by le.CREATE_DATE");
		sql.append(" ) A where rownum<=:rows ) where rn >:page");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, leadQueryDto);
		sqlQuery.setInteger("page", dataModel.getStartRow());
		sqlQuery.setInteger("rows", dataModel.getStartRow() + dataModel.getRows());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> mapList  = sqlQuery.list();
		return mapList;
    }
    
    protected void genHibernateSql(StringBuilder sql, LeadQueryDto leadQueryDto) {
		if (StringUtils.isNotBlank(leadQueryDto.getContactId())) {
			sql.append(" AND le.contact_id like :contactId");
		}
		if (StringUtils.isNotBlank(leadQueryDto.getStartDate())) {
			sql.append(" AND to_char(le.create_date,'yyyy-mm-dd hh24:mi:ss') >= :startDate");
		}
		if (StringUtils.isNotBlank(leadQueryDto.getEndDate())) {
			sql.append(" AND to_char(le.create_date,'yyyy-mm-dd hh24:mi:ss') <= :endDate");
		}
	}

	private void assignValue(Query sqlQuery, LeadQueryDto leadQueryDto) {
		if (StringUtils.isNotBlank(leadQueryDto.getContactId())) {
			sqlQuery.setString("contactId", leadQueryDto.getContactId());
		}
		if (StringUtils.isNotBlank(leadQueryDto.getStartDate())) {
			sqlQuery.setString("startDate", leadQueryDto.getStartDate());
		}
		if (StringUtils.isNotBlank(leadQueryDto.getEndDate())) {
			sqlQuery.setString("endDate", leadQueryDto.getEndDate());
		}
	}

}
