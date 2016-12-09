/*
 * @(#)SmsDaoImpl.java 1.0 2013-2-18下午6:14:24
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsBatchDao;
import com.chinadrtv.erp.smsapi.model.SmsBatch;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-18 下午6:14:24
 * 
 */
@Repository
public class SmsBatchDaoImpl extends GenericDaoHibernate<SmsBatch, Long>
		implements SmsBatchDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("jdbcTemplateSms")
	private JdbcTemplate jdbcTemplateSms;

	/*
	 * (非 Javadoc) <p>Title: setSessionFactory</p> <p>Description: </p>
	 * 
	 * @param sessionFactory
	 * 
	 * @see
	 * com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate#setSessionFactory
	 * (org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	@Qualifier("sessionFactorySms")
	public void setSessionFactory(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public SmsBatchDaoImpl(Class<SmsBatch> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: getBatchList</p> <p>Description: </p>
	 * 
	 * @param batchId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsDao#getBatchList(java.lang.String)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<SmsBatch> getBatchList(String batchId) {
		String sql = "from SmsBatch where batchId=:batchId ";
		Session session = getSession();
		Query query = session.createQuery(sql);
		query.setString("batchId", batchId);
		// TODO Auto-generated method stub
		return query.list();
	}

	/*
	 * (非 Javadoc) <p>Title: deleteAllByBatchId</p> <p>Description: </p>
	 * 
	 * @param batchId
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsBatchDao#deleteAllByBatchId(java.lang
	 * .String)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void deleteAllByBatchId(String batchId) {
		// TODO Auto-generated method stub
		String sql = "DELETE ACOAPP_MARKETING.SMS_BATCH WHERE BATCHID= '"
				+ batchId + "'";
		jdbcTemplate.execute(sql);
	}

	public SmsBatchDaoImpl() {
		super(SmsBatch.class);
	}

	/*
	 * (非 Javadoc) <p>Title: insertSmsBatch</p> <p>Description: </p>
	 * 
	 * @param smsBatch
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsBatchDao#insertSmsBatch(com.chinadrtv
	 * .erp.smsapi.module.SmsBatch)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void insertSmsBatch(List<SmsBatch> smsBatch) {
		// TODO Auto-generated method stub
		final List<SmsBatch> smsBatchs = smsBatch;
		String sql = "insert into ACOAPP_MARKETING.sms_batch(ID,BATCHID,UUID,DEPARTMENT,TOMOBILE,CREATETIME,CREATOR,TIMESTAMPS,"
				+ "CONNECTID,PARAM1,PARAM2,PARAM3,PARAM4,PARAM5,PARAM6,PARAM7,PARAM8,PARAM9,PARAM10) "
				+ " values(SEQ_SMS_BATCH.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplateSms.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				String batchId = smsBatchs.get(i).getBatchId();
				String uuid = smsBatchs.get(i).getUuid();
				String department = smsBatchs.get(i).getDepartment();
				String tomobile = smsBatchs.get(i).getTombile();
				Date cretaetime = new Date();
				String creator = smsBatchs.get(i).getCreator();
				Date timeStamp = smsBatchs.get(i).getTimestamps();
				String connectid = smsBatchs.get(i).getConnectId();
				String param1 = smsBatchs.get(i).getParam1();
				String param2 = smsBatchs.get(i).getParam2();
				String param3 = smsBatchs.get(i).getParam3();
				String param4 = smsBatchs.get(i).getParam4();
				String param5 = smsBatchs.get(i).getParam5();
				String param6 = smsBatchs.get(i).getParam6();
				String param7 = smsBatchs.get(i).getParam7();
				String param8 = smsBatchs.get(i).getParam8();
				String param9 = smsBatchs.get(i).getParam9();
				String param10 = smsBatchs.get(i).getParam10();
				ps.setString(1, batchId);
				ps.setString(2, uuid);
				ps.setString(3, department);
				ps.setString(4, tomobile);
				ps.setDate(5, new java.sql.Date(cretaetime.getTime()));
				ps.setString(6, creator);
				ps.setDate(7, new java.sql.Date(timeStamp.getTime()));
				ps.setString(8, connectid);
				ps.setString(9, param1);
				ps.setString(10, param2);
				ps.setString(11, param3);
				ps.setString(12, param4);
				ps.setString(13, param5);
				ps.setString(14, param6);
				ps.setString(15, param7);
				ps.setString(16, param8);
				ps.setString(17, param9);
				ps.setString(18, param10);
			}

			public int getBatchSize() {
				return smsBatchs.size();
			}
		});
		// getHibernateTemplate().save(smsBatch);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getBatchPage
	 * </p>
	 * <p>
	 * Description: 分页查询
	 * </p>
	 * 
	 * @param sql
	 * 
	 * @param offset
	 * 
	 * @param length
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsBatchDao#getBatchPage(java.lang.String,
	 *      int, int)
	 */
	public List<SmsBatch> getBatchPage(DataGridModel data, String batchid) {
		// TODO Auto-generated method stub
		List<SmsBatch> objList = new ArrayList<SmsBatch>();
		try {
			final String hql = "from SmsBatch where 1=1  and batchId = '"
					+ batchid + "'";
			Map<String, Parameter> params = new HashMap<String, Parameter>();
			Parameter page = new ParameterInteger("page", data.getStartRow());
			page.setForPageQuery(true);
			Parameter rows = new ParameterInteger("rows", data.getRows());
			rows.setForPageQuery(true);
			params.put("page", page);
			params.put("rows", rows);
			objList = findPageList(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return objList;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getBatchCount
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param batchId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsBatchDao#getBatchCount(java.lang.String)
	 */
	public Integer getBatchCount(String batchId) {
		// TODO Auto-generated method stub
		String sql = "select count(1) from ACOAPP_MARKETING.sms_batch  where batchid = ?";
		return jdbcTemplateSms.queryForInt(sql, new Object[] { batchId });
	}
}
