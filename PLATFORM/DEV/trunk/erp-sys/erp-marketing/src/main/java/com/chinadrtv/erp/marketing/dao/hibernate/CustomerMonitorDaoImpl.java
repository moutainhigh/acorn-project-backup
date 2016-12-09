/*
 * @(#)CustomerMonitorDaoImpl.java 1.0 2014-2-26下午2:14:25
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.CustomerMonitorDao;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-26 下午2:14:25
 * 
 */
@Repository
public class CustomerMonitorDaoImpl extends
		GenericDaoHibernate<CustomerMonitor, java.lang.Long> implements
		Serializable, CustomerMonitorDao {

	public CustomerMonitorDaoImpl() {
		super(CustomerMonitor.class);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<CustomerMonitor> query(CustomerMonitor customerMonitor,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"from  CustomerMonitor c where 1=1 ");
		Map<String, Parameter> params = genSql(sql, customerMonitor);
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by c.id desc");
		return findPageList(sql.toString(), params);
	}

	@Override
	public Integer queryCount(CustomerMonitor customerMonitor) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(c.id) from  CustomerMonitor c where 1=1 ");
		Map<String, Parameter> params = genSql(sql, customerMonitor);
		return findPageCount(sql.toString(), params);
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerMonitor customerMonitor) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(customerMonitor.getBatchCode())) {
			Parameter batchCode = new ParameterString("batchCode",
					customerMonitor.getBatchCode());
			sql.append(" and c.batchCode=:batchCode");
			paras.put("batchCode", batchCode);
		}
		if (!StringUtil.isNullOrBank(customerMonitor.getGroupCode())) {
			Parameter groupCode = new ParameterString("groupCode",
					customerMonitor.getGroupCode());
			sql.append(" and c.groupCode=:groupCode");
			paras.put("groupCode", groupCode);
		}

		return paras;

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saves(CustomerMonitor customerMonitor) {
		// TODO Auto-generated method stub
		String sql = "insert into  ACOAPP_MARKETING.CUSTOMER_MONITOR ( ID ,GROUP_CODE ,GROUP_NAME ,BATCH_CODE,SQL_CONTENT,STATUS,EXCEPTION,SQL_COUNT,CUSTOMER_RECOVERY)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { customerMonitor.getId(),
				customerMonitor.getGroupCode(), customerMonitor.getGroupName(),
				customerMonitor.getBatchCode(),
				customerMonitor.getSqlContent(), customerMonitor.getStatus(),
				customerMonitor.getException(), customerMonitor.getSqlCount(),
				customerMonitor.getCustomerRecovery() });

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void updates(CustomerMonitor customerMonitor) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"update  ACOAPP_MARKETING.CUSTOMER_MONITOR  set STATUS = ? ,SQL_COUNT = ? ,CUSTOMER_RECOVERY = ?  ,EXCEPTION = ?,COST_TIMES = ? where id = ?");
		jdbcTemplate
				.update(sql.toString(),
						new Object[] { customerMonitor.getStatus(),
								customerMonitor.getSqlCount(),
								customerMonitor.getCustomerRecovery(),
								customerMonitor.getException(),
								customerMonitor.getCostTimes(),
								customerMonitor.getId() });
	}

	@Override
	public Long getSeq() {
		// TODO Auto-generated method stub
		String sql = "select acoapp_marketing.SEQ_CUSTOMER_MONITOR.nextval from dual";
		return jdbcTemplate.queryForLong(sql);
	}
}
