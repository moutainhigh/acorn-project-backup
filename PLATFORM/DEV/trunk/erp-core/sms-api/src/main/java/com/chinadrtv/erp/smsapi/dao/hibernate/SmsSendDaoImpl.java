/*
 * @(#)SmsSendDaoImpl.java 1.0 2013-2-20上午9:44:15
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.model.SmsSend;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午9:44:15
 * 
 */
@Repository
public class SmsSendDaoImpl extends GenericDaoHibernate<SmsSend, Long>
		implements SmsSendDao {

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
	public SmsSendDaoImpl() {
		super(SmsSend.class);
	}

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

	/*
	 * 保存发送记录
	 * 
	 * @param smsSend
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDao#saveSmsSend(com.chinadrtv.erp
	 * .smsapi.module.SmsSend)
	 */
	public void saveSmsSend(SmsSend smsSend) {
		// TODO Auto-generated method stub
		try {
			getHibernateTemplate().saveOrUpdate(smsSend);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据
	 * 
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	public void updateSmsSend(String uuid, String status, String errorCode,
			String errorMsg) {
		SmsSend smsSned = getByUuid(uuid);
		smsSned.setSendStatus(status);
		if (errorCode != null && errorMsg != null) {
			smsSned.setErrorCode(errorCode);
			smsSned.setErrorInfo(errorMsg);
		}
		getHibernateTemplate().update(smsSned);

	}

	/*
	 * (非 Javadoc) <p>Title: getByUuid</p> <p>Description: </p>
	 * 
	 * @param uuid
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDao#getByUuid(java.lang.String)
	 */
	public SmsSend getByUuid(String uuid) {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		Query query = session.createQuery("from SmsSend where uuid =:uuid");
		query.setString("uuid", uuid);
		SmsSend smsSend = (SmsSend) query.list().get(0);
		return smsSend;
	}

	/*
	 * (非 Javadoc) <p>Title: getSeq</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDao#getSeq()
	 */
	public Long getSeq() {
		// TODO Auto-generated method stub
		SQLQuery sqllist = getSession().createSQLQuery(
				"select  ACOAPP_MARKETING.SEQ_SMS_SEND.nextval from dual");
		List list = sqllist.list();
		java.math.BigDecimal bigDecimal = (BigDecimal) list.get(0);
		return Long.valueOf(bigDecimal.toString());
	}

	/*
	 * (非 Javadoc) <p>Title: getByBatchid</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDao#getByBatchid(java.lang.String)
	 */
	public SmsSend getByBatchid(String batchId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sql = "from SmsSend where batchId = ? ";
		SmsSend smsSend = (SmsSend) getHibernateTemplate().find(sql,
				new Object[] { batchId }).get(0);
		return smsSend;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getbyCampaign
	 * </p>
	 * <p>
	 * Description:根据campaignid 查询 发送批次
	 * </p>
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDao#getbyCampaign(java.lang.Long)
	 */
	@Override
	public List<SmsSend> getbyCampaign(Long campaignId) {
		// TODO Auto-generated method stub
		String hql = "from SmsSend where campaignId = ?";

		List<SmsSend> list = getHibernateTemplate().find(hql,
				new Object[] { campaignId.toString() });
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}
}
