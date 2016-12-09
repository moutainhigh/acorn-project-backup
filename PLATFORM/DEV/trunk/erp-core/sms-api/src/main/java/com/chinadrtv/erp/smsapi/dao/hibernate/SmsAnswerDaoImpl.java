/*
 * @(#)SmsAnswerDaoImpl.java 1.0 2013-2-22下午5:07:27
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsAnswerDao;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午5:07:27
 * 
 */
@Repository
public class SmsAnswerDaoImpl extends GenericDaoHibernate<SmsAnswer, Long>
		implements SmsAnswerDao {

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
	 */
	public SmsAnswerDaoImpl() {
		super(SmsAnswer.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: saveSmsAnswer</p> <p>Description: </p>
	 * 
	 * @param smsAnswers
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsAnswerDao#saveSmsAnswer(java.util.List)
	 */
	public void saveSmsAnswer(SmsAnswer smsAnswers) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(smsAnswers);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCountsByMobile
	 * </p>
	 * <p>
	 * Description:根据手机号码查询总量
	 * </p>
	 * 
	 * @param mobile
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsAnswerDao#getCountsByMobile(java.lang
	 *      .String)
	 */
	@Override
	public Long getCountsByMobile(String mobile) {
		// TODO Auto-generated method stub
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - 1);
		Date historydate = curr.getTime();
		Date nowDate = new Date();
		String sql = "select count (t.mobile)"
				+ " from SmsAnswer t  where t.mobile = ?  "
				+ " and t.receiveTime >=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')"
				+ " and t.receiveTime <=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')";
		try {
			return Long.valueOf(getHibernateTemplate()
					.find(sql,
							new Object[] { mobile,
									DateTimeUtil.sim3.format(historydate),
									DateTimeUtil.sim3.format(nowDate) }).get(0)
					.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsAnswerByMobile
	 * </p>
	 * <p>
	 * Description:根据手机号码查询分页
	 * </p>
	 * 
	 * @param mobile
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsAnswerDao#getSmsAnswerByMobile(java.lang
	 *      .String, com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public List<SmsAnswer> getSmsAnswerByMobile(String mobile,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - 1);
		Date historydate = curr.getTime();
		Date nowDate = new Date();
		String sql = "select t.mobile,t.receiveContent,t.receiveTime "
				+ " from SmsAnswer t  where t.mobile =:mobile  and t.status='0' "
				+ " and t.receiveTime >=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')"
				+ " and t.receiveTime <=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')";

		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter mobiles = new ParameterString("mobile", mobile.toString());
		params.put("mobile", mobiles);
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		List objList = findPageList(sql.toString(), params);
		List<SmsAnswer> result = new ArrayList<SmsAnswer>();
		Object[] obj = null;
		SmsAnswer smsAnswer = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsAnswer = new SmsAnswer();
			if (obj[0] != null) {
				smsAnswer.setMobile(obj[0].toString());
			}
			if (obj[1] != null) {
				smsAnswer.setReceiveContent(obj[2].toString());
			}
			if (obj[2] != null) {
				smsAnswer.setReceiveTime(((Date) obj[7]));
			}
			result.add(smsAnswer);
		}
		return result;
	}

}
