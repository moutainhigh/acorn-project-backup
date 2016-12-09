/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SchemaConstants;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.ProblematicOrder;
import com.chinadrtv.erp.tc.core.dao.ProblematicOrderDao;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderDto;

/**
 * 2013-7-24 下午2:21:36
 * @version 1.0.0
 * @author yangfei
 *
 */
@Repository
public class ProblematicOrderDaoImpl extends GenericDaoHibernate<ProblematicOrder, String> implements ProblematicOrderDao{
	public ProblematicOrderDaoImpl() {
		super(ProblematicOrder.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> query(ProblematicOrderDto problematicOrderDto, DataGridModel dataModel) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select ")
		   .append("po.id          problemId,")
		   .append("po.orderid     orderid,")
		   .append("oh.crdt        crdate,")
		   .append("oh.status      status,")
		   .append("nm3.dsc        statusDsc,")
		   .append("oh.mailprice   mailprice,")
		   .append("oh.totalprice   totalprice,")
		   .append("oh.crusr       crusr,")
		   .append("con.contactid   contactId,")
		   .append("con.name        contactName,")
		   .append("oh.rfoutdat    sentDate,")
		   .append("po.crdt        problemDate,")
		   .append("po.crusr       problemUser,")
		   .append("po.problemtype problemtype,")
		   .append("nm.dsc         problemTypeDsc,")
		   .append("po.problemdsc  problemDsc,")
		   .append("po.problemstatus problemStatus,")
		   .append("nm2.dsc        problemStatusDsc ")
		   .append("from ").append(SchemaConstants.IAGENT_SCHEMA).append(".problematicorder po ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".orderhist oh on po.orderid = oh.orderid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact con on oh.contactid=con.contactid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm  on nm.tid='PROBLEMTYPE' and nm.id=po.problemtype ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm2 on nm2.tid='PROBLEMSTATUS' and nm2.id=po.problemstatus ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm3 on nm3.tid='ORDERSTATUS' and nm3.id=oh.status ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".address_ext ae on oh.addressid = ae.addressid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact c on oh.contactid = c.contactid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact cc on oh.getcontactid = cc.contactid ");
		if(StringUtils.isNotBlank(problematicOrderDto.getReceiverPhone())) {
		   sql.append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".phone p on oh.getcontactid = p.contactid ");
		};
		if(StringUtils.isNotBlank(problematicOrderDto.getItemCode())) {
		   sql.append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".orderdet od on oh.orderid = od.orderid ");
		}
		   sql.append("where 1=1 ");
		
		genHibernateSql(sql, problematicOrderDto);
		sql.append(" order by oh.crdt");
		sql.append(" ) A where rownum<=:rows ) where rn >:page");
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, problematicOrderDto);
		sqlQuery.setInteger("page", dataModel.getStartRow());
		sqlQuery.setInteger("rows", dataModel.getStartRow() + dataModel.getRows());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> mapList  = sqlQuery.list();
		return mapList;
	}
	
	public Integer queryCount(ProblematicOrderDto problematicOrderDto) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(po.orderid)")
		   .append("from ").append(SchemaConstants.IAGENT_SCHEMA).append(".problematicorder po ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".orderhist oh on po.orderid = oh.orderid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact con on oh.contactid=con.contactid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm  on nm.tid='PROBLEMTYPE' and nm.id=po.problemtype ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm2 on nm2.tid='PROBLEMSTATUS' and nm2.id=po.problemstatus ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".names nm3 on nm3.tid='ORDERSTATUS' and nm3.id=oh.status ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".address_ext ae on oh.addressid = ae.addressid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact c on oh.contactid = c.contactid ")
		   .append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".contact cc on oh.getcontactid = cc.contactid ");
		if(StringUtils.isNotBlank(problematicOrderDto.getReceiverPhone())) {
		   sql.append("left join ").append(SchemaConstants.IAGENT_SCHEMA).append(".phone p on oh.getcontactid = p.contactid ");
		}
		if(StringUtils.isNotBlank(problematicOrderDto.getItemCode())) {
		   sql.append("left join iagent.orderdet od on oh.orderid = od.orderid ");
		}
		   sql.append("where 1=1 ");
		genHibernateSql(sql, problematicOrderDto);
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		assignValue(sqlQuery, problematicOrderDto);
		Object obj = sqlQuery.uniqueResult();
		int count = ((BigDecimal) obj).intValue();
		return count;
	}
	
	public int replyProblematicOrder(ProblematicOrderDto problematicOrderDto) {
		StringBuilder sql = new StringBuilder();
		sql.append("update "+SchemaConstants.IAGENT_SCHEMA+".PROBLEMATICORDER pc set ")
		   .append(" pc.REPLYDSC=:replyDsc, pc.problemstatus=2, pc.redt=:replyDate ")
		   .append(" where pc.id=:pid");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setString("replyDsc", problematicOrderDto.getReplyContent());
		sqlQuery.setString("pid", problematicOrderDto.getProblemId());
		sqlQuery.setDate("replyDate", new Date());
		int number = sqlQuery.executeUpdate();
		return number;
	}
	
	protected void genHibernateSql(StringBuilder sql, ProblematicOrderDto problematicOrderDto) {
		if (StringUtils.isNotBlank(problematicOrderDto.getOrderid())) {
			sql.append(" AND po.orderid =:orderId");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getOrderStatus())) {
			sql.append(" AND oh.status =:orderStatus");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getMailId())) {
			sql.append(" AND oh.MAILID =:mailId");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getContactId())) {
			sql.append(" AND oh.contactid = :contactId" );
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getContactName())) {
			sql.append(" AND con.name like :contactName ");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getCrusr())) {
			sql.append(" AND oh.crusr = :crUsr");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getBeginCrDate())) {
			sql.append(" AND oh.crdt >= TO_DATE(:startDate, 'YYYY-MM-DD HH24:MI:SS')");
		}

		if (StringUtils.isNotBlank(problematicOrderDto.getEndCrDate())) {
			sql.append(" AND oh.crdt <= TO_DATE(:endDate, 'YYYY-MM-DD HH24:MI:SS')");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getProcessStatus())) {
			sql.append(" AND po.problemstatus =:problemstatus");
		}  else {
			sql.append(" AND po.problemstatus in('1','2')");
		}
		
		//optional
		//收货地址
		if (StringUtils.isNotBlank(problematicOrderDto.getProvinceid())) {
			sql.append(" AND ae.PROVINCEID = :provinceId");
		}
		if (problematicOrderDto.getCityid() != null) {
			sql.append(" AND ae.cityid = :cityId");
		}
		if (problematicOrderDto.getCountyid() != null) {
			sql.append(" AND ae.COUNTYID = :countyId");
		}
		if (problematicOrderDto.getAreaid() != null) {
			sql.append(" AND ae.AREAID = :areaId");
		}
		//收货人信息
		if (StringUtils.isNotBlank(problematicOrderDto.getReceiverName())) {
			sql.append(" AND cc.name like :receiverName ");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getReceiverPhone())) {
			sql.append(" AND p.PHN2 = :receiverPhone");
		}
		//出库时间
		if (StringUtils.isNotBlank(problematicOrderDto.getBeginRfoutdt())) {
			sql.append(" AND oh.rfoutdat >= TO_DATE(:rfStartDate, 'YYYY-MM-DD HH24:MI:SS')");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getEndRfoutdt())) {
			sql.append(" AND oh.rfoutdat <= TO_DATE(:rfEndDate, 'YYYY-MM-DD HH24:MI:SS')");
		}
		//父订单号
		if (StringUtils.isNotBlank(problematicOrderDto.getParentOrderId())) {
			sql.append(" AND oh.PARENTID = :parentId" );
		}
		//
		if (StringUtils.isNotBlank(problematicOrderDto.getPaytype())) {
			sql.append(" AND oh.PAYTYPE =:payType");
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getItemCode())) {
			sql.append(" AND (od.PRODID like :itemCode or od.PRODSCODE like :itemCode or od.PRODNAME like :itemCode)");
		}
	}

	private void assignValue(Query sqlQuery, ProblematicOrderDto problematicOrderDto) {
		if (StringUtils.isNotBlank(problematicOrderDto.getOrderid())) {
			sqlQuery.setString("orderId", problematicOrderDto.getOrderid());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getOrderStatus())) {
			sqlQuery.setString("orderStatus", problematicOrderDto.getOrderStatus());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getMailId())) {
			sqlQuery.setString("mailId", problematicOrderDto.getMailId());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getContactId())) {
			sqlQuery.setString("contactId", problematicOrderDto.getContactId());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getContactName())) {
			sqlQuery.setString("contactName", problematicOrderDto.getContactName());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getCrusr())) {
			sqlQuery.setString("crUsr", problematicOrderDto.getCrusr());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getBeginCrDate())) {
			sqlQuery.setString("startDate", problematicOrderDto.getBeginCrDate());
		}

		if (StringUtils.isNotBlank(problematicOrderDto.getEndCrDate())) {
			sqlQuery.setString("endDate", problematicOrderDto.getEndCrDate());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getProcessStatus())) {
			sqlQuery.setString("problemstatus", problematicOrderDto.getProcessStatus());
		}
		
		//optional
		//收货地址
		if (StringUtils.isNotBlank(problematicOrderDto.getProvinceid())) {
			sqlQuery.setString("provinceId", problematicOrderDto.getProvinceid());
		}
		if (problematicOrderDto.getCityid() != null) {
			sqlQuery.setInteger("cityId", problematicOrderDto.getCityid());
		}
		if (problematicOrderDto.getCountyid() != null) {
			sqlQuery.setInteger("countyId", problematicOrderDto.getCountyid());
		}
		if (problematicOrderDto.getAreaid() != null) {
			sqlQuery.setInteger("areaId", problematicOrderDto.getAreaid());
		}
		//收货人信息
		if (StringUtils.isNotBlank(problematicOrderDto.getReceiverName())) {
			sqlQuery.setString("receiverName", problematicOrderDto.getReceiverName());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getReceiverPhone())) {
			sqlQuery.setString("receiverPhone", problematicOrderDto.getReceiverPhone());
		}
		//出库时间
		if (StringUtils.isNotBlank(problematicOrderDto.getBeginRfoutdt())) {
			sqlQuery.setString("rfStartDate", problematicOrderDto.getBeginRfoutdt());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getEndRfoutdt())) {
			sqlQuery.setString("rfEndDate", problematicOrderDto.getEndRfoutdt());
		}
		//父订单号
		if (StringUtils.isNotBlank(problematicOrderDto.getParentOrderId())) {
			sqlQuery.setString("parentId", problematicOrderDto.getParentOrderId());
		}
		//
		if (StringUtils.isNotBlank(problematicOrderDto.getPaytype())) {
			sqlQuery.setString("payType", problematicOrderDto.getPaytype());
		}
		if (StringUtils.isNotBlank(problematicOrderDto.getItemCode())) {
			sqlQuery.setString("itemCode", "%"+problematicOrderDto.getItemCode()+"%");
		}
	}
}
