/*
 * @(#)DeliveryRegulationDaoImpl.java 1.0 2013-3-25上午10:38:59
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterDouble;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.oms.dto.OrderAssignRuleDto;
import com.google.code.ssm.api.InvalidateAssignCache;

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
 * @since 2013-3-25 上午10:38:59 
 * 
 */
@Repository
public class OrderAssignRuleDaoImpl extends GenericDaoHibernateBase<OrderAssignRule, Long> implements OrderAssignRuleDao{

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public OrderAssignRuleDaoImpl() {
		super(OrderAssignRule.class);
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

	
	/* (none Javadoc)
	* <p>Title: queryPageList</p>
	* <p>Description: </p>
	* @param odr
	* @param dataModel
	* @return
	* @see com.chinadrtv.erp.oms.dao.OmsDeliveryRegulationDao#queryPageList(com.chinadrtv.erp.oms.domain.OmsDeliveryRegulation, com.chinadrtv.erp.oms.common.DataGridModel)
	*/ 
	@SuppressWarnings("rawtypes")
	public List<OrderAssignRule> queryPageList(OrderAssignRuleDto odr, DataGridModel dataModel) {

		Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select odr from OrderAssignRule odr where 1=1 ");
		if(null != odr.getId()){
			sb.append(" and odr.id=:id");
			paramMap.put("id", new ParameterLong("id", odr.getId()));
		}
		if(null != odr.getName() && !"".equals(odr.getName())){
			sb.append(" and odr.name like '%" + odr.getName() + "%'");
		}
		if(null != odr.getEntityId()){
			sb.append(" and odr.entityId=:entityId");
			paramMap.put("entityId", new ParameterLong("entityId", odr.getEntityId()));
		}
		if(null != odr.getOrderChannel() && null!=odr.getOrderChannel().getId()){
			sb.append(" and odr.orderChannel.id=:channelId");
			paramMap.put("channelId", new ParameterLong("channelId", odr.getOrderChannel().getId()));
		}
		if(null != odr.getAreaGroup() && null!=odr.getAreaGroup().getId()){
			sb.append(" and odr.areaGroup.id=:areaGroupId");
			paramMap.put("areaGroupId", new ParameterLong("areaGroupId", odr.getAreaGroup().getId()));
		}
		if(null != odr.getWarehouseId()){
			sb.append(" and odr.warehouseId=:warehouseId");
			paramMap.put("warehouseId", new ParameterLong("warehouseId", odr.getWarehouseId()));
		}
		if(null != odr.getPriority()){
			sb.append(" and odr.priority=:priority");
			paramMap.put("priority", new ParameterLong("priority", odr.getPriority()));
		}
		if(null != odr.getProdCode() && !"".equals(odr.getProdCode())){
			sb.append(" and odr.prodCode=:prodCode");
			paramMap.put("prodCode", new ParameterString("prodCode", odr.getProdCode()));
		}
		if(null != odr.getBeginMinAmount()){
			sb.append(" and odr.minAmount>=:beginMinAmount");
			paramMap.put("beginMinAmount", new ParameterDouble("beginMinAmount", odr.getBeginMinAmount()));
		}
		if(null != odr.getEndMinAmount()){
			sb.append(" and odr.minAmount<=:endMinAmount");
			paramMap.put("endMinAmount", new ParameterDouble("endMinAmount", odr.getEndMinAmount()));
		}
		if(null != odr.getBeginMaxAmount()){
			sb.append(" and odr.maxAmount>=:beginMaxAmount");
			paramMap.put("beginMaxAmount", new ParameterDouble("beginMaxAmount", odr.getBeginMaxAmount()));
		}
		if(null != odr.getEndMaxAmount()){
			sb.append(" and odr.maxAmount<=:endMaxAmount");
			paramMap.put("endMaxAmount", new ParameterDouble("endMaxAmount", odr.getEndMaxAmount()));
		}
		
		sb.append(" order by odr.crDT desc");
		
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);
		paramMap.put("page", page);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		paramMap.put("rows", rows);
		
		return this.findPageList(sb.toString(), paramMap);
	}

	
	/* 
	 * 
	* <p>Title: queryPageCount</p>
	* <p>Description: </p>
	* @param odr
	* @return
	* @see com.chinadrtv.erp.oms.dao.OmsDeliveryRegulationDao#queryPageCount(com.chinadrtv.erp.oms.domain.OmsDeliveryRegulation)
	*/ 
	@SuppressWarnings("rawtypes")
	public int queryPageCount(OrderAssignRuleDto odr) {
		Map<String, Parameter> paramMap = new HashMap<String, Parameter>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(odr) from OrderAssignRule odr where 1=1 ");
		if(null != odr.getId()){
			sb.append(" and odr.id=:id");
			paramMap.put("id", new ParameterLong("id", odr.getId()));
		}
		if(null != odr.getName() && !"".equals(odr.getName())){
			sb.append(" and odr.name like '%" + odr.getName() + "%'");
		}
		if(null != odr.getEntityId()){
			sb.append(" and odr.entityId=:entityId");
			paramMap.put("entityId", new ParameterLong("entityId", odr.getEntityId()));
		}
		if(null != odr.getOrderChannel() && null!=odr.getOrderChannel().getId()){
			sb.append(" and odr.orderChannel.id=:channelId");
			paramMap.put("channelId", new ParameterLong("channelId", odr.getOrderChannel().getId()));
		}
		if(null != odr.getAreaGroup() && null!=odr.getAreaGroup().getId()){
			sb.append(" and odr.areaGroup.id=:areaGroupId");
			paramMap.put("areaGroupId", new ParameterLong("areaGroupId", odr.getAreaGroup().getId()));
		}
		if(null != odr.getWarehouseId()){
			sb.append(" and odr.warehouseId=:warehouseId");
			paramMap.put("warehouseId", new ParameterLong("warehouseId", odr.getWarehouseId()));
		}
		if(null != odr.getPriority()){
			sb.append(" and odr.priority=:priority");
			paramMap.put("priority", new ParameterLong("priority", odr.getPriority()));
		}
		if(null != odr.getProdCode() && !"".equals(odr.getProdCode())){
			sb.append(" and odr.prodCode=:prodCode");
			paramMap.put("prodCode", new ParameterString("prodCode", odr.getProdCode()));
		}
		if(null != odr.getBeginMinAmount()){
			sb.append(" and odr.minAmount>=:beginMinAmount");
			paramMap.put("beginMinAmount", new ParameterDouble("beginMinAmount", odr.getBeginMinAmount()));
		}
		if(null != odr.getEndMinAmount()){
			sb.append(" and odr.minAmount<=:endMinAmount");
			paramMap.put("endMinAmount", new ParameterDouble("endMinAmount", odr.getEndMinAmount()));
		}
		if(null != odr.getBeginMaxAmount()){
			sb.append(" and odr.maxAmount>=:beginMaxAmount");
			paramMap.put("beginMaxAmount", new ParameterDouble("beginMaxAmount", odr.getBeginMaxAmount()));
		}
		if(null != odr.getEndMaxAmount()){
			sb.append(" and odr.maxAmount<=:endMaxAmount");
			paramMap.put("endMaxAmount", new ParameterDouble("endMaxAmount", odr.getEndMaxAmount()));
		}
		
		sb.append(" order by odr.crDT desc");
		
		return this.findPageCount(sb.toString(), paramMap);
	}

	
	/*
	 * 查询交叉数据
	* <p>Title: queryCrossList</p>
	* <p>Description: </p>
	* @param channelId
	* @param priority
	* @return
	* @see com.chinadrtv.erp.oms.dao.OrderAssignRuleDao#queryCrossList(java.lang.Long, java.lang.Long)
	*/ 
	public List<OrderAssignRule> queryCrossList(OrderAssignRule oar, Long channelId, Long priority) {
		String hql = "select oar from OrderAssignRule oar where " +
				"oar.orderChannel.id=:channelId and oar.priority=:priority and oar.id!=:id";
		
		return this.findList(hql, new ParameterLong("channelId", channelId), 
								new ParameterLong("priority", priority),
								new ParameterLong("id", null==oar.getId() ? -1 : oar.getId()));
	}
	
}
