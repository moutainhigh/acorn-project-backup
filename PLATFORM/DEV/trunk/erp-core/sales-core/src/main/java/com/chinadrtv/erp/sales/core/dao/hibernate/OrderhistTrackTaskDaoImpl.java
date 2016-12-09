/*
 * @(#)OrderhistTrackTaskDaoImpl.java 1.0 2013年12月24日下午3:48:13
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dao.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OrderhistTrackTask;
import com.chinadrtv.erp.sales.core.dao.OrderhistTrackTaskDao;
import com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto;

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
 * @since 2013年12月24日 下午3:48:13 
 * 
 */
@Repository
public class OrderhistTrackTaskDaoImpl extends GenericDaoHibernate<OrderhistTrackTask, Long> implements OrderhistTrackTaskDao {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderhistTrackTaskDaoImpl.class);
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public OrderhistTrackTaskDaoImpl() {
		super(OrderhistTrackTask.class);
	}

	/** 
	 * <p>Title: queryByOrderId</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return OrderhistTrackTask
	 * @see com.chinadrtv.erp.sales.core.dao.OrderhistTrackTaskDao#queryByOrderId(java.lang.String)
	 */ 
	@Override
	public OrderhistTrackTask queryByOrderId(String orderId) {
		String hql = "select o from OrderhistTrackTask o where o.orderId = :orderId";
		return this.find(hql, new ParameterString("orderId", orderId));
	}

	/** 
	 * <p>Title: queryPageCount</p>
	 * <p>Description: </p>
	 * @param orderhistTrackTaskDto
	 * @return
	 * @see com.chinadrtv.erp.sales.core.dao.OrderhistTrackTaskDao#queryPageCount(com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto)
	 */ 
	@Override
	public Integer queryPageCount(OrderhistTrackTaskDto dto) {
		
		StringBuffer sb = new StringBuffer();
		
		String sql = this.querySql(dto);
		
		sb.append("select count(*) from ( ");
		
		sb.append(sql);
		
		sb.append(" )");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		
		this.parameterizedQuery(query, dto);
		
		BigDecimal rs = (BigDecimal) query.list().get(0);
		
		return rs.intValue();
	}

	/** 
	 * <p>Title: queryPageList</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param orderhistTrackTaskDto
	 * @return
	 * @see com.chinadrtv.erp.sales.core.dao.OrderhistTrackTaskDao#queryPageList(com.chinadrtv.erp.constant.DataGridModel, com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto)
	 */ 
	@Override
	public List<OrderhistTrackTaskDto> queryPageList(DataGridModel dataGridModel, OrderhistTrackTaskDto dto) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		
		String querySql = this.querySql(dto);
		sb.append(querySql);
		
		sb.append("  ) row_ where rownum <=:endRow ) where rownum_ > :beginRow ");
		
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		
		this.parameterizedQuery(query, dto);

		Integer beginRow = dataGridModel.getStartRow();
		Integer endRow = dataGridModel.getRows() + beginRow;
		query.setParameter("beginRow", beginRow);
		query.setParameter("endRow", endRow);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> mapList = query.list();
		
		List<OrderhistTrackTaskDto> dtoList = this.adapter(mapList);
		
		return dtoList;
	}

	
    /**
	 * <p></p>
	 * @param mapList
	 * @return
	 */
	private List<OrderhistTrackTaskDto> adapter(List<Map<String, Object>> mapList) {
		List<OrderhistTrackTaskDto> dtoList = new ArrayList<OrderhistTrackTaskDto>();
		
		for(Map<String, Object> rsMap : mapList) {
			dtoList.add(this.adapter(rsMap));
		}
		return dtoList;
	}

	/**
	 * <p></p>
	 * @param rsMap
	 * @return
	 */
	public OrderhistTrackTaskDto adapter(Map<String, Object> rsMap) {
		OrderhistTrackTaskDto dto = new OrderhistTrackTaskDto();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Long id = null == rsMap.get("ID") ? null : Long.parseLong(rsMap.get("ID").toString());
		String orderId = null == rsMap.get("ORDER_ID") ? null : rsMap.get("ORDER_ID").toString();
		String orderStatus = null == rsMap.get("STATUS_DESC") ? null : rsMap.get("STATUS_DESC").toString();
		String assignUser = null == rsMap.get("ASSIGN_USER") ? null : rsMap.get("ASSIGN_USER").toString();
		String trackUser = null == rsMap.get("TRACK_USER") ? null : rsMap.get("TRACK_USER").toString();
		Long taskId = null == rsMap.get("TASK_ID") ? null : Long.parseLong(rsMap.get("TASK_ID").toString());
		
		String mailId = null == rsMap.get("MAILID") ? null : rsMap.get("MAILID").toString();
		BigDecimal totalPrice = null == rsMap.get("TOTALPRICE") ? null : BigDecimal.valueOf(Double.parseDouble(rsMap.get("TOTALPRICE").toString()));
		String crusr = null == rsMap.get("CRUSR") ? null : rsMap.get("CRUSR").toString();
		String contactId = null == rsMap.get("CONTACTID") ? null : rsMap.get("CONTACTID").toString();
		String contactName = null == rsMap.get("NAME") ? null : rsMap.get("NAME").toString();
		String trackRemark = null == rsMap.get("TRACK_REMARK") ? null : rsMap.get("TRACK_REMARK").toString();
		
		Date assignTime = null;
		Date trackTime = null;
		Date crdt = null;
		Date rfoutdat = null;
		try {
			assignTime = null == rsMap.get("ASSIGN_TIME") ? null : sdf.parse(rsMap.get("ASSIGN_TIME").toString());
			trackTime = null == rsMap.get("TRACK_TIME") ? null : sdf.parse(rsMap.get("TRACK_TIME").toString());
			crdt = null == rsMap.get("CRDT") ? null : sdf.parse(rsMap.get("CRDT").toString());
			rfoutdat = null == rsMap.get("RFOUTDAT") ? null : sdf.parse(rsMap.get("RFOUTDAT").toString());
		} catch (ParseException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		
		dto.setId(id);
		dto.setOrderId(orderId);
		dto.setOrderStatus(orderStatus);
		dto.setAssignUser(assignUser);
		dto.setTrackUser(trackUser);
		dto.setTrackTime(trackTime);
		dto.setTaskId(taskId);
		dto.setAssignTime(assignTime);
		dto.setContactId(contactId);
		dto.setContactName(contactName);
		dto.setMailId(mailId);
		dto.setTotalprice(totalPrice);
		dto.setCrusr(crusr);
		dto.setCrdt(crdt);
		dto.setRfoutdat(rfoutdat);
		dto.setTrackRemark(trackRemark);
		
		return dto;
	}

	@Override
    public List<OrderhistTrackTask> queryByOrderIds(List<String> orderIdList) {
        DetachedCriteria criteria = DetachedCriteria.forClass(OrderhistTrackTask.class);
        criteria.add(Restrictions.in("orderId", orderIdList));
        return this.getHibernateTemplate().findByCriteria(criteria);
    }


	/**
	 * <p></p>
	 * @param query
	 * @param dto
	 */
	private void parameterizedQuery(Query query, OrderhistTrackTaskDto dto) {
		String orderId = dto.getOrderId();
		String contactId = dto.getContactId();
		String orderStatus = dto.getOrderStatus();
		String mailId = dto.getMailId();
		String trackUser = dto.getTrackUser();
		String crusr = dto.getCrusr();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(null != orderId && !"".equals(orderId)) {
			query.setParameter("orderId", orderId);
		}
		if(null != contactId && !"".equals(contactId)) {
			query.setParameter("contactId", contactId);
		}
		if(null != orderStatus && !"".equals(orderStatus)) {
			query.setParameter("orderStatus", orderStatus);
		}
		if(null != mailId && !"".equals(mailId)) {
			query.setParameter("mailId", mailId);
		}
		if(null != crusr && !"".equals(crusr)) {
			query.setParameter("crusr", crusr);
		}
		if(null != trackUser && !"".equals(trackUser)) {
			query.setParameter("trackUser", trackUser);
		}
		if(null != dto.getBeginDate()) {
			String beginDate = sdf.format(dto.getBeginDate());
			query.setParameter("beginDate", beginDate);
		}
		if(null != dto.getEndDate()) {
			String endDate = sdf.format(dto.getEndDate());
			query.setParameter("endDate", endDate);
		}
	}

	/**
	 * <p></p>
	 * @param orderhistTrackTaskDto
	 * @return
	 */
	private String querySql(OrderhistTrackTaskDto dto) {
		StringBuffer sb = new StringBuffer();
		String sql = " select ott.*, oh.mailid, oh.crdt, oh.totalprice, oh.crusr, oh.contactid, c.name, " +
					 " oh.rfoutdat, oh.status, oh.track_remark, n.dsc status_desc " +
					 " from iagent.orderhist_track_task ott inner join iagent.orderhist oh on ott.order_id = oh.orderid " +
					 " left join iagent.contact c on oh.contactid = c.contactid " +
					 " left join iagent.names n on n.tid='ORDERSTATUS' and n.id=oh.status" +
					 " where 1=1 ";
		sb.append(sql);
		
		if(null != dto.getOrderId() && !"".equals(dto.getOrderId())) {
			sb.append(" and ott.order_id = :orderId ");
		}
		if(null != dto.getContactId() && !"".equals(dto.getContactId())) {
			sb.append(" and oh.contactid = :contactId ");
		}
		if(null != dto.getContactName() && !"".equals(dto.getContactName())) {
			sb.append(" and c.name like '%"+ dto.getContactName() +"%' ");
		}
		if(null != dto.getOrderStatus() && !"".equals(dto.getOrderStatus())) {
			sb.append(" and oh.status = :orderStatus ");
		}
		if(null != dto.getMailId() && !"".equals(dto.getMailId())) {
			sb.append(" and oh.mailid = :mailId ");
		}
		if(null != dto.getCrusr() && !"".equals(dto.getCrusr())) {
			sb.append(" and oh.crusr = :crusr ");
		}
		if(null != dto.getTrackUser() && !"".equals(dto.getTrackUser())) {
			sb.append(" and oh.track_usr = :trackUser ");
		}
		if(null != dto.getBeginDate()) {
			sb.append(" and trunc(ott.assign_time) >= trunc(to_date(:beginDate, 'yyyy-mm-dd')) ");
		}
		if(null != dto.getEndDate()) {
			sb.append(" and trunc(ott.assign_time) <= trunc(to_date(:endDate, 'yyyy-mm-dd')) ");
		}
		return sb.toString();
	}
	
}
