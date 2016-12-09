/*
 * @(#)OrderDaoImpl.java 1.0 2013-5-7下午1:20:25
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.dao.OrderDao;

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
 * @since 2013-5-7 下午1:20:25 
 * 
 */
@Repository
public class OrderDaoImpl extends GenericDaoHibernate<Order, Long> implements OrderDao {
	
	public OrderDaoImpl() {
		super(Order.class);
	}
	
	/** 
	 * <p>Title: 查询联系人是否有未出库订单</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<Order>
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryOrderByContactId(java.lang.String)
	 */ 
	public List<Order> querySpecialOrder(String contactId) {
		String hql = "select oh from Order oh "+
					 " where 1=1 "+
					 " and oh.getcontactid=:contactId "+
					 " and oh.status not in ('0','5','6','8') "+
					 " and nvl(oh.customizestatus, '0')='0' ";
		
		return this.findList(hql, new ParameterString("contactId", contactId));
	}

	/** 
	 * <p>Title: 查询该地址是否有未出库订单</p>
	 * <p>Description: </p>
	 * @param addressId
	 * @return List<Order>
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryOrderByContactId(java.lang.String)
	 */ 
	public List<Order> haveExWarehouseOrderByAddress(String addressId) {
		String hql = "select oh from Order oh "+
					 " where 1=1 "+
					 " and oh.address.addressId=:addressId "+
					 " and oh.status not in ('0','5','6','8') "+
					 " and nvl(oh.customizestatus, '0')='0' ";
		
		return this.findList(hql, new ParameterString("addressId", addressId));
	}

	/** 
	 * <p>Title: queryOrderCountByContactId</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return int
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryOrderCountByContactId(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	public int queryOrderCountByContactId(String contactId) {
		String hql = "select count(o) from Order o where o.contactid=:contactId order by o.crdt desc";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		params.put("contactId", new ParameterString("contactId", contactId));
		return this.findPageCount(hql, params);
	}

	/** 
	 * <p>Title: queryOrderListByContactId</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param contactId
	 * @return List<Order>
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryOrderListByContactId(com.chinadrtv.erp.uc.common.DataGridModel, java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	public List<Order> queryOrderListByContactId(DataGridModel dataGridModel, String contactId) {
		String hql = "select o from Order o where o.contactid=:contactId order by o.crdt desc";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		params.put("contactId", new ParameterString("contactId", contactId));
		
		Parameter page = new ParameterInteger("page", dataGridModel.getStartRow());
		page.setForPageQuery(true);
		params.put("page", page);

		Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
		rows.setForPageQuery(true);
		params.put("rows", rows);
		
		return this.findPageList(hql, params);
	}

	/** 
	 * <p>Title: queryByOrderId</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return Order
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryByOrderId(java.lang.String)
	 */ 
	public Order queryByOrderId(String orderId) {
    	String hql = "select oh from com.chinadrtv.erp.model.agent.Order oh where oh.orderid=:orderid";
    	Parameter<String> orderid = new ParameterString("orderid", orderId);
    	Order order = this.find(hql, orderid);
    	return order;
	}

	/** 
	 * <p>Title: queryOrderCreaterByContact</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<String>
	 * @see com.chinadrtv.erp.uc.dao.OrderDao#queryOrderCreaterByContact(java.lang.String)
	 */ 
	@SuppressWarnings("unchecked")
	public List<String> queryOrderCreaterByContact(String contactId) {
		String sql = "select distinct oh.crusr from iagent.orderhist oh " +
				 " where oh.contactid=:contactId " +
				 " and oh.status in ('1', '2', '5', '7') ";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter("contactId", contactId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> rsList = query.list();
		List<String> creatorList = new ArrayList<String>();
		for(Map<String, Object> map : rsList){
			String crusr = map.get("CRUSR").toString();
			creatorList.add(crusr);
		}
		return creatorList;
	}

    @Override
    public BigDecimal queryTotalAmountByContactId(String customerId) {
        String hql = "select sum(totalprice) from Order where contactid=:customerId";
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setString("customerId", customerId);
        return (BigDecimal)hqlQuery.uniqueResult();
    }

//    @Override
//    public List<Order> findByCreateDateRange(String minOrderCreateDate, String maxOrderCreateDate) {
//        StringBuffer hql = new StringBuffer();
//        hql.append("from Order o where 1=1 ");
//        hql.append(" and o.crdt  >= to_date(:beginDate,'yyyy-MM-dd HH24:mi:ss')");
//        hql.append(" and o.crdt  < to_date(:endDate,'yyyy-MM-dd HH24:mi:ss')");
//        Query query = this.getSession().createQuery(hql.toString());
//        query.setString("beginDate", minOrderCreateDate);
//        query.setString("endDate", maxOrderCreateDate);
//        return query.list();
//    }

//    @Override
//    public BigDecimal calculateOrderTransferBlackList(String contactId) {
//        String sql = "select COUNT(DISTINCT case when o.status IN ('4', '5') THEN o.orderid end)/COUNT(DISTINCT o.orderid) " +
//                "from iagent.orderhist o where o.contactid=:contactId";
//        Query query = this.getSession().createSQLQuery(sql);
//        query.setParameter("contactId", contactId);
//        return (BigDecimal) query.uniqueResult();
//    }
}