/*
 * @(#)ShipmentRefundDaoImpl.java 1.0 2013-3-28上午10:36:45
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDao;
import com.chinadrtv.erp.oms.dto.ShipmentRefundDto;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-3-28 上午10:36:45 
 * 
 */
@Repository
public class ShipmentRefundDaoImpl extends GenericDaoHibernate<ShipmentRefund, Long> implements ShipmentRefundDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ShipmentRefundDaoImpl() {
		super(ShipmentRefund.class);
	}

	/* 
	 * 根据mailId 与 shipmentId 查询
	* <p>Title: queryByParam</p>
	* <p>Description: </p>
	* @param mailId
	* @param shipmentId
	* @return ShipmentRefund
	* @see com.chinadrtv.erp.oms.dao.ShipmentRefundDao#queryByParam(java.lang.String, java.lang.String)
	*/ 
	@SuppressWarnings("rawtypes")
	public ShipmentRefund queryByParam(String mailId, String shipmentId, String entityId) {
		
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select sr from ShipmentRefund sr where 1=1 and sr.entityId=:entityId and (sr.status=1 or sr.status=2)");
		params.put("entityId", new ParameterString("entityId", entityId));
		if(null != mailId && !"".equals(mailId)){
			sb.append(" and sr.mailId=:mailId ");
			params.put("mailId", new ParameterString("mailId", mailId));
		}
		if(null != shipmentId && !"".equals(shipmentId)){
			if(shipmentId.indexOf("V")>0){
				shipmentId += "%";
			}else{
				shipmentId += "V%";
			}
			sb.append(" and sr.shipmentId like :shipmentId ");
			params.put("shipmentId", new ParameterString("shipmentId", shipmentId));
		}
		return this.find(sb.toString(), params);
	}

	/* 
	 * 检查校验行数
	* <p>Title: queryCheckCount</p>
	* <p>Description: </p>
	* @param agentUser
	* @return
	* @see com.chinadrtv.erp.oms.dao.ShipmentRefundDao#queryCheckCount(com.chinadrtv.erp.user.model.AgentUser)
	*/ 
	@SuppressWarnings({"rawtypes"})
	public Map<String, Object> queryCheckCount(String entityId, AgentUser agentUser) {
		String allCheckHql = "select count(sr) from ShipmentRefund sr where sr.entityId=:entityId and sr.status=2";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		params.put("entityId", new ParameterString("entityId", entityId));
		Integer allCheckCount = this.findPageCount(allCheckHql, params);
		
		String selfCheckhql = "select count(sr) from ShipmentRefund sr where sr.entityId=:entityId and sr.status=2 and sr.agentUser=:agentUser";
		params.put("agentUser", new ParameterString("agentUser", agentUser.getUserId()));
		Integer selfCheckCount = this.findPageCount(selfCheckhql, params);
		
		String listHql = "select sr from ShipmentRefund sr where sr.entityId=:entityId and sr.status=2";
		List<ShipmentRefund> srList = this.findList(listHql, new ParameterString("entityId", entityId));
		
		List<ShipmentRefundDto> srDtoList = new ArrayList<ShipmentRefundDto>();
		
		for(ShipmentRefund sr : srList){
			Set<ShipmentRefundDetail> srdSet =  sr.getShipmentRefundDetails();
			Long agentQty = 0L;
			Long warehouseQty = 0L;
			for(ShipmentRefundDetail srd : srdSet){
				agentQty += srd.getAgentQty();
				warehouseQty += srd.getWarehouseQty();
			}
			ShipmentRefundDto srDto = new ShipmentRefundDto();
			BeanUtils.copyProperties(sr, srDto);
			String hisLabel = "";
			if(agentQty.equals(warehouseQty)){
				hisLabel = "完整";
			}else{
				hisLabel = "有丢失";
			}
			srDto.setHisLabel(hisLabel);
			srDtoList.add(srDto);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("allCheckCount", allCheckCount);
		rsMap.put("selfCheckCount", selfCheckCount);
		rsMap.put("srList", srDtoList);
		
		return rsMap;
	}



	/** 
	 * <p>Title: 半拒收退货确认查询</p>
	 * <p>Description: 半拒收退货确认查询</p>
	 * @param shipmentRefund
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.oms.dao.ShipmentRefundDao#queryRefundSendList(com.chinadrtv.erp.model.trade.ShipmentRefund)
	 */ 
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List<Map<String, Object>> queryRefundSendList(ShipmentRefundDto srDto) {
		if(null==srDto.getEntityId() || "".equals(srDto.getEntityId())){
			return new ArrayList();
		}
		
		String sql = "select to_char(sr.id) id, sr.shipment_id, sr.mail_id, sr.entity_id, to_char(sr.agent_create_date, 'yyyy-mm-dd hh24:mi:ss') create_date, sr.agent_user, " +
				" to_char(cp.name) name, sh.warehouse_id, to_char(wh.warehouse_name) warehouse_name, decode(sum(srd.agent_qty)-sum(srd.warehouse_qty), null, 0, 0) qty, sr.reject_code " +
				" from shipment_refund sr inner join shipment_header sh on sr.shipment_id = sh.shipment_id " +
				" left join iagent.company cp on sh.entity_id = cp.companyid " +
				" left join warehouse wh on sh.warehouse_id = wh.warehouse_id " +
				" inner join shipment_refund_detail srd on sr.shipment_id = srd.shipment_id " +
				" where 1=1 " +
				" and sr.status=2 " +
				" and sr.entity_id = '"+srDto.getEntityId()+"' " +
				" and sh.warehouse_id = '"+srDto.getWarehouseId()+"' " +
				" group by sr.id, sr.shipment_id, sr.mail_id, sr.entity_id, sr.agent_create_date, sr.agent_user, " +
				" cp.name, sh.warehouse_id, wh.warehouse_name, sr.reject_code";
		
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}


	/** 
	 * <p>Title: 导出</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return List<ShipmentRefundDetail>
	 * @see com.chinadrtv.erp.oms.dao.ShipmentRefundDao#exportRefundSendList(java.lang.String)
	 */ 
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportRefundSendList(String ids) {
		String sql = " select srd.item_id, srd.item_desc, sum(srd.warehouse_qty) total_qty " +
					 " from shipment_refund sr inner join shipment_refund_detail srd on sr.shipment_id = srd.shipment_id " +
					 " where 1=1 " +
					 " and sr.id in ("+ids+") " +
					 " group by srd.item_id, srd.item_desc ";
		
		Query query = this.hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}




	/*==============================================================================*/








    //start by xzk
    public void addShipmentRefund(ShipmentRefund shipmentRefund) {
        this.save(shipmentRefund);
    }

    public void updateShipmentRefund(ShipmentRefund shipmentRefund) {
        this.saveOrUpdate(shipmentRefund);
    }

    public ShipmentRefund getShipmentRefund(Long id)
    {
        return this.get(id);
    }

    public List<ShipmentRefund> getShipmentRefundFromOrderId(String orderId)
    {
        return this.findList("from ShipmentRefund where orderId=:orderId",new ParameterString("orderId",orderId));
    }


}
