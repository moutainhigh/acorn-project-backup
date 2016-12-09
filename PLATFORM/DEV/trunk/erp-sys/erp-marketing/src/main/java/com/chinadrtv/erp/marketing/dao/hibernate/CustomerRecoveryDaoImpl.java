/*
 * @(#)CustomerRecoveryDaoImpl.java 1.0 2013-4-17下午3:25:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao;
import com.chinadrtv.erp.model.marketing.CustomerRecovery;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 下午3:25:41
 * 
 */
@Repository
public class CustomerRecoveryDaoImpl extends
		GenericDaoHibernate<CustomerRecovery, java.lang.String> implements
		CustomerRecoveryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param persistentClass
	 */
	public CustomerRecoveryDaoImpl(Class<CustomerRecovery> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

	public CustomerRecoveryDaoImpl() {
		super(CustomerRecovery.class);
	}

	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param customerRecovery
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#insert(com.chinadrtv
	 * .erp.marketing.model.CustomerRecovery)
	 */
	public void insert(CustomerRecovery customerRecovery) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(customerRecovery);
	}

	/*
	 * (非 Javadoc) <p>Title: getCustomerRecovery</p> <p>Description: </p>
	 * 
	 * @param batchCode
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#getCustomerRecovery
	 * (java.lang.String)
	 */
	public List<CustomerRecovery> getCustomerRecovery(String batchCode) {
		// TODO Auto-generated method stub
		String sql = "from CustomerRecovery where batchId = ?";
		List<CustomerRecovery> list = getHibernateTemplate().find(sql,
				new Object[] { batchCode });
		return list;
	}

	/*
	 * (非 Javadoc) <p>Title: getCountByBatchId</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#getCountByBatchId
	 * (java.lang.String)
	 */
	public Long getCountByBatchId(String batchid) {
		// TODO Auto-generated method stub
		String sql = "select count (*)  from customer_recover where batch_id = ? ";
		Long result = null;
		try {
			result = jdbcTemplate.queryForLong(sql, new Object[] { batchid });
		} catch (Exception e) {
			// TODO: handle exception
			result = 0l;
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: getRecoverCount</p> <p>Description: </p>
	 * 
	 * @param batchId
	 * 
	 * @param groupCode
	 * 
	 * @param days
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerBatchDetailDao#getRecoverCount
	 * (java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public void insertRecoverCount(String groupCode) {
		// TODO Auto-generated method stub
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("  insert into customer_recovery (id,batch_id,group_code,CUSTOMER_ID)  select seq_customer_recovery.nextval,  c.batchid, '"
				+ groupCode + "',c.contactid  from  iagent.ob_contact c ");
		sqlCount.append("where exists (" + "select 1 from customer_batch b "
				+ "where b.batch_code=c.batchid " + "and b.group_code=?" + ") ");
		sqlCount.append("and (c.status ='0' or c.status is null) ");
		int temp = jdbcTemplate.update(sqlCount.toString(),
				new Object[] { groupCode });
		System.out.println(sqlCount.toString() + "===" + temp);

	}

	/*
	 * (非 Javadoc) <p>Title: queryObContactUnuse</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#queryObContactUnuse
	 * (java.lang.String)
	 */
	public Long queryObContactUnuse(String batchid) {
		// TODO Auto-generated method stub
		String sql = "select  count(*)  from iagent.ob_contact where batchid = ? and  (status ='0' or status is null)";
		Long result = 0l;
		try {
			result = jdbcTemplate.queryForLong(sql, new Object[] { batchid });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryObContactUsed</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#queryObContactUsed
	 * (java.lang.String)
	 */
	public Long queryObContactUsed(String batchid) {
		// TODO Auto-generated method stub
		String sql = "select  count(*)  from iagent.ob_contact where batchid = ? and  status ='-1' ";
		Long result = 0l;
		try {
			result = jdbcTemplate.queryForLong(sql, new Object[] { batchid });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryTotalObContactUsed</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @param status
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao#queryTotalObContactUsed
	 * (java.lang.String, java.lang.String)
	 */
	public Long queryTotalObContactUsed(String batchid, String status) {
		// TODO Auto-generated method stub
		Long result = 0l;

		StringBuffer sql = new StringBuffer(
				"select count(*)  from iagent.ob_contact where batchid in ("
						+ batchid + ")");
		if (status.equals("1")) {
			sql.append("and (status ='0' or status is null)");
		} else {
			sql.append("and (status ='-1')");
		}
		try {
			System.out.println(sql);
			result = jdbcTemplate.queryForLong(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

}
