/*
 * @(#)LeadInteractionOrderDaoImpl.java 1.0 2013-7-3下午3:42:58
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.LeadInteractionOrderDao;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-7-3 下午3:42:58 
 * 
 */
@Repository
public class LeadInteractionOrderDaoImpl extends GenericDaoHibernate<LeadInteractionOrder, Long> implements
		LeadInteractionOrderDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public LeadInteractionOrderDaoImpl() {
		super(LeadInteractionOrder.class);
	}
	
	@SuppressWarnings("unchecked")
	public String queryCampaignByOrderId(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select le.campaign_id ")
		   .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
		   .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li on lio.lead_interaction_id=li.id")
		   .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le on li.lead_id=le.id")
		   .append(" where lio.order_id=:orderId and li.interaction_type != '4'");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("orderId", orderId);
		List<Object> objList = sqlQuery.list();
		Object obj = null;
		if(objList != null && objList.size() > 0) {
			obj = objList.get(0);
		}
		String campaignId = null;
		if(obj != null) {
			campaignId = String.valueOf(((BigDecimal)obj).longValue());
		}
		return campaignId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryLeadCampaignByOrderId(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select le.id leadId, le.campaign_id camId")
		   .append(" from ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction_order lio ")
		   .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead_interaction li on lio.lead_interaction_id=li.id")
		   .append(" left join ").append(SchemaConstants.MARKETING_SCHEMA).append(".lead le on li.lead_id=le.id")
		   .append(" where lio.order_id=:orderId and li.interaction_type != '4'");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("orderId", orderId);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> mapList  = sqlQuery.list();
		return mapList;
	}

	
}
