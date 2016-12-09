/*
 * @(#)OrderUrgentApplicationDaoImpl.java 1.0 2013-7-10下午3:54:55
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dao.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.sales.core.dao.OrderUrgentApplicationDao;
import com.chinadrtv.erp.uc.dto.UrgentOrderDto;
import com.chinadrtv.erp.user.util.PermissionHelper;

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
 * @since 2013-7-10 下午3:54:55 
 * 
 */
@Repository
public class OrderUrgentApplicationDaoImpl extends GenericDaoHibernateBase<OrderUrgentApplication, Long> implements
		OrderUrgentApplicationDao {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderUrgentApplicationDaoImpl.class);
	public OrderUrgentApplicationDaoImpl() {
		super(OrderUrgentApplication.class);
	}

	@Autowired
	@Required
	@Qualifier("sessionFactoryCtb")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

	/** 
	 * <p>Title: queryPageCount</p>
	 * <p>Description: </p>
	 * @param dto
	 * @param dataModel
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.OrderUrgentApplicationDao#queryPageCount(com.chinadrtv.erp.uc.dto.UrgentOrderDto, com.chinadrtv.erp.constant.DataGridModel)
	 */ 
	@Override
	public Integer queryPageCount(UrgentOrderDto dto, DataGridModel dataModel) {

		StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) from ( ");
		
		String querySql = this.querySql(dto);
		sb.append(querySql);
		
		sb.append(" )");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, dto);
		BigDecimal rs = (BigDecimal) query.list().get(0);
		
		return rs.intValue();
	}


	/** 
	 * <p>Title: queryPageList</p>
	 * <p>Description: </p>
	 * @param dto
	 * @param dataModel
	 * @return List<UrgentOrderDto>
	 * @see com.chinadrtv.erp.uc.dao.OrderUrgentApplicationDao#queryPageList(com.chinadrtv.erp.uc.dto.UrgentOrderDto, com.chinadrtv.erp.constant.DataGridModel)
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryPageList(UrgentOrderDto dto, DataGridModel dataModel) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		
		String querySql = this.querySql(dto);
		sb.append(querySql);
		
		Integer beginRow = dataModel.getStartRow();
		Integer endRow = dataModel.getRows() + beginRow;
		sb.append("  ) row_ where rownum <=:endRow ) where rownum_ > :beginRow ");
		
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, dto);
		query.setParameter("beginRow", beginRow);
		query.setParameter("endRow", endRow);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> mapList = query.list();
		
		return mapList;
	}
	
	
	/**
	 * <p>拼接SQL</p>
	 * @param dto
	 * @return String
	 */
	private String querySql(UrgentOrderDto dto) {
		
		String sql = " select distinct oh.crdt, oh.status order_status, oh.totalprice, oh.crusr, oh.contactid,  " +
				 " c.name, oh.rfoutdat, oh.mailid mail_id, oa.* " +
				 " from acoapp_cntrpbank.orderurgentapplication oa " + 
				 " inner join iagent.orderhist oh on oa.orderid = oh.orderid " +
				 " left join iagent.contact c on oh.contactid = c.contactid " +
				 " left join iagent.contact cc on oh.getcontactid = cc.contactid" +
				 " left join iagent.phone p on oh.getcontactid = p.contactid" +
				 " left join iagent.address_ext ae on oh.addressid = ae.addressid" +
				 " left join iagent.orderdet od on oh.orderid = od.orderid" +
				 " where 1=1 ";
		
		String orderId = dto.getOrderid();
		String contactId = dto.getContactId();
		String contactName = dto.getContactName();
		Integer appStatus = dto.getAppStatus();
		String orderStatus = dto.getOrderStatus();
		Date beginDate = dto.getBeginCrDate();
		Date endDate = dto.getEndCrDate();
		String recieverName = dto.getReceiverName();
		String phone = dto.getReceiverPhone();
		String parentId = dto.getParentOrderId();
		String payType = dto.getPaytype();
		String itemCode = dto.getItemCode();
		Date beginRfoutdt = dto.getBeginRfoutdt();
		Date endRfoutdt = dto.getEndRfoutdt();
		String mailId = dto.getMailId();

		StringBuffer sb = new StringBuffer();
		
		sb.append(sql);
		
		/*String whereCondition = PermissionHelper.getFilterCondition();
		if(null!=whereCondition && !"".equals(whereCondition)){
			sb.append(" and oh.crusr=:crusr ");
		}else{*/
			String agentId = dto.getAgentId();
			if(null!=agentId && !"".equals(agentId)){
				sb.append(" and oh.crusr=:crusr ");
			}
		//}
		
		if(null!=orderId && !"".equals(orderId)){
			sb.append(" and oa.orderid=:orderId ");
		}
		if(null!=mailId && !"".equals(mailId)){
			sb.append(" and oh.mailid=:mailId ");
		}
		if(null!=contactId && !"".equals(contactId)){
			sb.append(" and oh.contactid=:contactId ");
		}
		if(null!=contactName && !"".equals(contactName)){
			sb.append(" and c.name like :contactName ");
		}
		if(null!=orderStatus && !"".equals(orderStatus)){
			sb.append(" and oh.status=:orderStatus ");
		}
		if(null!=beginDate){
			sb.append(" and trunc(oh.crdt)>=trunc(to_date(:beginDateStr , 'yyyy-mm-dd')) ");
		}
		if(null!=endDate){
			sb.append(" and trunc(oh.crdt)<=trunc(to_date(:endDateStr , 'yyyy-mm-dd')) ");
		}
		if(null!=appStatus){
			sb.append(" and oa.status=:appStatus ");
		}
		if(null!=recieverName && !"".equals(recieverName)){
			sb.append(" and cc.name like :recieverName ");
		}
		if(null!=phone && !"".equals(phone)){
			sb.append(" and p.phone_num=:phone ");
		}
		if(null!=parentId && !"".equals(parentId)){
			sb.append(" and oh.parentid=:parentId ");
		}
		if(null!=payType && !"".equals(payType)){
			sb.append(" and oh.paytype=:payType ");
		}
		if(null!=itemCode && !"".equals(itemCode)){
			sb.append(" and od.prodid=:itemCode ");
		}
		if(null!=beginRfoutdt){
			sb.append(" and trunc(oh.rfoutdat)-trunc(to_date(:beginRfoutdtStr , 'yyyy-mm-dd'))>=0 ");
		}
		if(null!=endRfoutdt){
			sb.append(" and trunc(oh.rfoutdat)-trunc(to_date(:endRfoutdtStr , 'yyyy-mm-dd'))<=0 ");
		}
		if(null!=dto.getProvinceid() && !"".equals(dto.getProvinceid())){
			sb.append(" and ae.provinceid =:provinceid ");
		}
		if(null!=dto.getCityid()){
			sb.append(" and ae.cityid=:cityid ");
		}
		if(null!=dto.getCountyid()){
			sb.append(" and ae.countyid=:countyid ");
		}
		if(null!=dto.getAreaid()){
			sb.append(" and ae.areaid=:areaid ");
		}
		
		sb.append(" order by oa.appdate desc");

		return sb.toString();
	}
	

	/**
	 * <p>参数化query</p>
	 * @param query
	 * @param dto
	 */
	@SuppressWarnings("rawtypes")
	private void parameterizedQuery(Query query, UrgentOrderDto dto) {
		String orderId = dto.getOrderid();
		String contactId = dto.getContactId();
		String contactName = dto.getContactName();
		Integer appStatus = dto.getAppStatus();
		String orderStatus = dto.getOrderStatus();
		Date beginDate = dto.getBeginCrDate();
		Date endDate = dto.getEndCrDate();
		String recieverName = dto.getReceiverName();
		String phone = dto.getReceiverPhone();
		String parentId = dto.getParentOrderId();
		String payType = dto.getPaytype();
		String itemCode = dto.getItemCode();
		Date beginRfoutdt = dto.getBeginRfoutdt();
		Date endRfoutdt = dto.getEndRfoutdt();
		String mailId = dto.getMailId();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		/*String whereCondition = PermissionHelper.getFilterCondition();
		if(null!=whereCondition && !"".equals(whereCondition)){
			Map<String, Parameter> configParams = PermissionHelper.getFilterParameter();
			Parameter perParam = configParams.get("useruserId");
			query.setParameter("crusr", perParam.getValue().toString());
		}else{*/
			String agentId = dto.getAgentId();
			if(null!=agentId && !"".equals(agentId)){
				query.setParameter("crusr", agentId);
			}
		//}
		
		if(null!=orderId && !"".equals(orderId)){
			query.setParameter("orderId", orderId);
		}
		if(null!=mailId && !"".equals(mailId)){
			query.setParameter("mailId", mailId);
		}
		if(null!=contactId && !"".equals(contactId)){
			query.setParameter("contactId", contactId);
		}
		if(null!=contactName && !"".equals(contactName)){
			query.setParameter("contactName", "%"+contactName+"%");
		}
		if(null!=orderStatus && !"".equals(orderStatus)){
			query.setParameter("orderStatus", orderStatus);
		}
		if(null!=beginDate){
			String beginDateStr = sdf.format(beginDate);
			query.setParameter("beginDateStr", beginDateStr);
		}
		if(null!=endDate){
			String endDateStr = sdf.format(endDate);
			query.setParameter("endDateStr", endDateStr);
		}
		if(null!=appStatus){
			query.setParameter("appStatus", appStatus);
		}
		if(null!=recieverName && !"".equals(recieverName)){
			query.setParameter("recieverName", "%"+recieverName+"%");
		}
		if(null!=phone && !"".equals(phone)){
			query.setParameter("phone", phone);
		}
		if(null!=parentId && !"".equals(parentId)){
			query.setParameter("parentId", parentId);
		}
		if(null!=payType && !"".equals(payType)){
			query.setParameter("payType", payType);
		}
		if(null!=itemCode && !"".equals(itemCode)){
			query.setParameter("itemCode", itemCode);
		}
		if(null!=beginRfoutdt){
			String beginRfoutdtStr = sdf.format(beginRfoutdt);
			query.setParameter("beginRfoutdtStr", beginRfoutdtStr);
		}
		if(null!=endRfoutdt){
			String endRfoutdtStr = sdf.format(endRfoutdt);
			query.setParameter("endRfoutdtStr", endRfoutdtStr);
		}
		if(null!=dto.getProvinceid() && !"".equals(dto.getProvinceid())){
			query.setParameter("provinceid", dto.getProvinceid());
		}
		if(null!=dto.getCityid()){
			query.setParameter("cityid", dto.getCityid());
		}
		if(null!=dto.getCountyid()){
			query.setParameter("countyid", dto.getCountyid());
		}
		if(null!=dto.getAreaid()){
			query.setParameter("areaid", dto.getAreaid());
		}
	}


	/**
	 * <p>Dto适配器</p>
	 * @param rowMap
	 * @return UrgentOrderDto
	 */
	public UrgentOrderDto dtoAdaptor(Map<String, Object> rowMap) {
		UrgentOrderDto dto = new UrgentOrderDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String orderId = null==rowMap.get("ORDERID") ? null : rowMap.get("ORDERID").toString();
		Date crdt = null;
		Date rfoutdt = null;
		Date appDate = null;
		Date chkDate = null;
		Date enddate = null;
		try {
			crdt = null==rowMap.get("CRDT") ? null : sdf.parse(rowMap.get("CRDT").toString());
			rfoutdt = null==rowMap.get("RFOUTDAT") ? null : sdf.parse(rowMap.get("RFOUTDAT").toString());
			appDate = null==rowMap.get("APPDATE") ? null : sdf.parse(rowMap.get("APPDATE").toString());
			chkDate = null==rowMap.get("CHKDATE") ? null : sdf.parse(rowMap.get("CHKDATE").toString());
			enddate = null==rowMap.get("ENDDATE") ? null : sdf.parse(rowMap.get("ENDDATE").toString());
		} catch (ParseException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		String orderStatus = null==rowMap.get("ORDER_STATUS") ? null : rowMap.get("ORDER_STATUS").toString();
		BigDecimal totalprice = null==rowMap.get("TOTALPRICE") ? null : new BigDecimal(rowMap.get("TOTALPRICE").toString());
		String crusr = null==rowMap.get("CRUSR") ? null : rowMap.get("CRUSR").toString();
		String contactId = null==rowMap.get("CONTACTID") ? null : rowMap.get("CONTACTID").toString();
		String name = null==rowMap.get("NAME") ? null : rowMap.get("NAME").toString();
		String mailId = null==rowMap.get("MAIL_ID") ? null : rowMap.get("MAIL_ID").toString();
		String apppsn = null==rowMap.get("APPPSN") ? null : rowMap.get("APPPSN").toString();
		Integer urgentStatus = null==rowMap.get("STATUS") ? null : new Integer(rowMap.get("STATUS").toString());
		String class_ = null==rowMap.get("CLASS") ? null : rowMap.get("CLASS").toString();
		String chkpsn = null==rowMap.get("CHKPSN") ? null : rowMap.get("CHKPSN").toString();
		String applicationreason = null==rowMap.get("APPLICATIONREASON") ? null : rowMap.get("APPLICATIONREASON").toString();
		String endpsn = null==rowMap.get("ENDPSN") ? null : rowMap.get("ENDPSN").toString();
		String appdsc = null==rowMap.get("APPDSC") ? null : rowMap.get("APPDSC").toString();
		String chkreason = null==rowMap.get("CHKREASON") ? null : rowMap.get("CHKREASON").toString();
		
		dto.setOrderid(orderId);
		dto.setCrdt(crdt);
		dto.setRfoutdt(rfoutdt);
		dto.setAppdate(appDate);
		dto.setChkdate(chkDate);
		dto.setOrderStatus(orderStatus);
		dto.setTotalprice(totalprice);
		dto.setCrusr(crusr);
		dto.setContactId(contactId);
		dto.setContactName(name);
		dto.setMailId(mailId);
		dto.setEnddate(enddate);
		dto.setApppsn(apppsn);
		dto.setClass_(class_);
		dto.setChkpsn(chkpsn);
		dto.setApplicationreason(applicationreason);
		dto.setEndpsn(endpsn);
		dto.setAppdsc(appdsc);
		dto.setAppStatus(urgentStatus);
		dto.setChkreason(chkreason);
		
		//提交坐席
		//List<OrderUrgentApplication> appAgentList = this.queryAppAgent(orderId);
		//String appAgent = printStringList(appAgentList);
		//dto.setApppsn(appAgent);
		//dto.setReceiverAddress(null);
		
		return dto;
	}


	/**
	 * <p>查询订单的催送坐席</p>
	 * @param orderId
	 * @return List<String>
	 */
	/*private List<OrderUrgentApplication> queryAppAgent(String orderId) {
		String hql = "select oa from OrderUrgentApplication oa where oa.orderid=:orderId ";
		return this.findList(hql, new ParameterString("orderId", orderId));
	}

	private static String printStringList(List<OrderUrgentApplication> list) {
		if (null == list) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (OrderUrgentApplication obj : list) {
			sb.append(null==obj ? "null" : obj.getApppsn() + ",");
		}

		String str = sb.toString();
		if(str.endsWith(",")){
			str = str.substring(0, str.length()-1);
		}
		
		return str;
	}*/

	/** 
	 * <p>Title: queryLatest</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return 查询最近的催送单
	 * @see com.chinadrtv.erp.sales.core.dao.OrderUrgentApplicationDao#queryLatest(java.lang.String)
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public OrderUrgentApplication queryLatest(String orderId) {
		String sql = "select t1.tuid from acoapp_cntrpbank.orderurgentapplication t1 "+
					 " inner join (select oa.orderid, max(oa.appdate) appdate " +
					 " from acoapp_cntrpbank.orderurgentapplication oa " +
					 " where oa.orderid = :orderId " +
					 " group by oa.orderid) t2 " +
					 " on t1.orderid = t2.orderid " +
					 " and t1.appdate = t2.appdate " ;

		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter("orderId", orderId);
		List<BigDecimal> rsList = query.list();
		if(rsList==null || rsList.size()==0){
			return null;
		}
		BigDecimal id = rsList.get(0);
		return this.get(id.longValue());
	}

	/** 
	 * <p>Title: queryFinishedUrgentOrder</p>
	 * <p>Description: </p>
	 * @param agentId
	 * @param beginDate
	 * @param endDate
	 * @return Model list
	 * @see com.chinadrtv.erp.sales.core.dao.OrderUrgentApplicationDao#queryFinishedUrgentOrder(java.lang.String, java.util.Date, java.util.Date)
	 */ 
	@Override
	public List<OrderUrgentApplication> queryFinishedUrgentOrder(String agentId, Date beginDate, Date endDate) {
		String hql = "select o from OrderUrgentApplication o where o.apppsn=:agentId and o.status=3 " +
					 "	and o.enddate<=:endDate and o.enddate>=:beginDate";
		
		return this.findList(hql, new ParameterString("agentId", agentId), 
							new ParameterTimestamp("beginDate", beginDate), new ParameterTimestamp("endDate", endDate));
	}

}
