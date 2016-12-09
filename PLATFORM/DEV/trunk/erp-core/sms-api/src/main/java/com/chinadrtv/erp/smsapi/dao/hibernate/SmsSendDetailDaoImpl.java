/*
 * @(#)SmsSendDetailDaoImpl.java 1.0 2013-2-20上午10:10:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午10:10:42
 * 
 */
@Repository
public class SmsSendDetailDaoImpl extends
		GenericDaoHibernate<SmsSendDetail, Long> implements SmsSendDetailDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	@Qualifier("jdbcTemplateSms")
	private JdbcTemplate jdbcTemplateSms;

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
	public SmsSendDetailDaoImpl(Class<SmsSendDetail> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
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
	 * (非 Javadoc) <p>Title: saveDetailList</p> <p>Description: </p>
	 * 
	 * @param smsSendDetail
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#saveDetailList(java.util
	 * .List)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveDetailList(List<SmsSendDetail> smsSendDetail) {
		final List<SmsSendDetail> smsSendDetails = smsSendDetail;
		String sql = "insert into ACOAPP_MARKETING.sms_send_detail (ID,MOBILE,CONNECTID,CONTENT,UUID,SENDTIME,BATCHID,RECEIVE_STATUS,"
				+ " FEEDBACK_STATUS,CREATETIME,CREATOR,SOURCE,CHANNEL,"
				+ "SMSSTOP_STATUS,SMSSTOP_STATUS_DES,FEEDBACK_STATUS_DES,RECEIVE_STATUS_DES,LAST_MODIFY_DATE,SMS_TYPE,CAMPAIGN_ID) "
				+ "values(ACOAPP_MARKETING.SEQ_SMS_DETAIL.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplateSms.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				String tomobile = smsSendDetails.get(i).getMobile();
				String connectid = smsSendDetails.get(i).getConnectId();
				String content = smsSendDetails.get(i).getContent();
				String uuid = smsSendDetails.get(i).getUuid();
				Date sendtime = new Date();
				String batchId = smsSendDetails.get(i).getBatchId();
				String receiveStatus = smsSendDetails.get(i).getReceiveStatus();
				String feedbackStatus = smsSendDetails.get(i)
						.getFeedbackStatus();
				Date createTime = smsSendDetails.get(i).getCreatetime();
				String creator = smsSendDetails.get(i).getCreator();
				String source = smsSendDetails.get(i).getSource();
				String channel = smsSendDetails.get(i).getChannel();
				String smsstopStatus = smsSendDetails.get(i).getSmsStopStatus();
				String smsstopStatusDes = smsSendDetails.get(i)
						.getSmsStopStatusDes();
				String feedbackStatusDes = smsSendDetails.get(i)
						.getFeedbackStatusDes();
				String receiveStatusDes = smsSendDetails.get(i)
						.getReceiveStatusDes();
				Long campaignId = smsSendDetails.get(i).getCampaignId();
				Date lastmodifyDate = smsSendDetails.get(i).getLastModifyDate();
				String smsType = smsSendDetails.get(i).getSmsType();
				ps.setString(1, tomobile);
				ps.setString(2, connectid);
				ps.setString(3, content);
				ps.setString(4, uuid);
				ps.setTimestamp(5, new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime()));
				ps.setString(6, batchId);
				ps.setString(7, receiveStatus);
				ps.setString(8, feedbackStatus);
				ps.setTimestamp(9, new java.sql.Timestamp(createTime.getTime()));
				ps.setString(10, creator);
				ps.setString(11, source);
				ps.setString(12, channel);
				ps.setString(13, smsstopStatus);
				ps.setString(14, smsstopStatusDes);
				ps.setString(15, feedbackStatusDes);
				ps.setString(16, receiveStatusDes);
				ps.setTimestamp(17, new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime()));
				ps.setString(18, smsType);
				ps.setLong(19, campaignId);
			}

			public int getBatchSize() {
				return smsSendDetails.size();
			}
		});
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void updateDetailList(List<SmsSendDetail> smsSendDetail) {
		Transaction tx = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().beginTransaction();
		// getHibernateTemplate().saveOrUpdateAll(smsSendDetail);
		for (int i = 0; i < smsSendDetail.size(); i++) {
			getHibernateTemplate().getSessionFactory().getCurrentSession()
					.update(smsSendDetail.get(i));
		}
		tx.commit();
	}

	public SmsSendDetailDaoImpl() {
		super(SmsSendDetail.class);
	}

	/*
	 * (非 Javadoc) <p>Title: getByUuid</p> <p>Description: </p>
	 * 
	 * @param uuid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getByUuid(java.lang.String)
	 */
	public SmsSendDetail getByUuid(String uuid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Query query = session
				.createQuery("from SmsSendDetail where uuid =:uuid");
		query.setString("uuid", uuid);
		SmsSendDetail smsSendDetail = (SmsSendDetail) query.list().get(0);
		return smsSendDetail;
	}

	/*
	 * 查询某一批次某一时间内短信状态数量
	 * 
	 * @param batchid
	 * 
	 * @param status
	 * 
	 * @param times
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getCounts(java.lang.String,
	 * java.lang.String, java.sql.Date)
	 */
	public Long getCounts(String batchid, String status) {
		// TODO Auto-generated method stub
		String sql = "select count (t."
				+ status
				+ ") from SmsSendDetail t  where t.batchId = ? and   (t.receiveStatus = '1'  or t.receiveStatus = '0' )";
		try {
			return Long.valueOf(getHibernateTemplate()
					.find(sql, new Object[] { batchid }).get(0).toString());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (非 Javadoc) <p>Title: getTimeList</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getTimeList(java.lang.String
	 * )
	 * 
	 * select count(s.receive_status_time), to_char(s.receive_status_time,
	 * 'yyyy-mm-dd hh24') from sms_send_detail s where
	 * s.batchid='2013040124151441000000001' group by
	 * to_char(s.receive_status_time, 'yyyy-mm-dd hh24')
	 */
	public List<Map<String, Object>> getTimeList(String batchid, String status) {
		// TODO Auto-generated method stub
		String sql = "select count(t."
				+ status
				+ ") as count, to_char(t."
				+ status
				+ ",'yyyy-mm-dd hh24' ) as time   from  ACOAPP_MARKETING.sms_send_detail t where batchId = ?  and  t."
				+ status
				+ " is not null group by to_char(t."
				+ status
				+ ",'yyyy-mm-dd hh24' )   having count(1) >= 1  order by time  ";
		List<Map<String, Object>> result = (List<Map<String, Object>>) jdbcTemplateSms
				.queryForList(sql, new Object[] { batchid });
		return result;
	}

	public Long getTimeCount(String batchid, String date, String statusTime) {
		String sql = "select count (t1." + statusTime
				+ ") from SmsSendDetail  t1 where to_char(t1." + statusTime
				+ ",'yyyy-mm-dd hh24')  = ? and batchId = ? ";
		return Long.valueOf(getHibernateTemplate()
				.find(sql, new Object[] { date, batchid }).get(0).toString());

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsSendDetailsByBatchId
	 * </p>
	 * 
	 * <p>
	 * Description: 根据batchid mobile 查询
	 * </p>
	 * 
	 * @param batchId
	 * 
	 * @param mobile
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getSmsSendDetailsByBatchId
	 *      (java.lang.String, java.lang.String)
	 */
	public SmsSendDetail getSmsSendDetailsByBatchId(String batchId,
			String mobile) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Query query = session
				.createQuery("from SmsSendDetail where batchId =:batchId and mobile =:mobile order by  createtime desc ");
		query.setString("batchId", batchId);
		query.setString("mobile", mobile);
		List<SmsSendDetail> list = (List<SmsSendDetail>) query.list();
		return list.get(0);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: removeByBatchid
	 * </p>
	 * <p>
	 * Description: 根据batchid 删除数据
	 * </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#removeByBatchid(java.lang
	 *      .String)
	 */
	public Boolean removeByBatchid(String batchid) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		String sql = "delete from ACOAPP_MARKETING.sms_send_detail where batchId = '"
				+ batchid + "'";
		try {
			jdbcTemplateSms.execute(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			// TODO: handle exception
		}
		return flag;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCountsByContactId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param contactId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getCountsByContactId(java
	 *      .lang.String)
	 */
	@Override
	public Long getCountsByContactId(String contactId) {
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - 1);
		Date historydate = curr.getTime();
		Date nowDate = new Date();
		String sql = "select count (t.connectId)"
				+ " from SmsSendDetail t  where t.connectId = ? "
				+ " and t.receiveStatusTime >=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')"
				+ " and t.receiveStatusTime <=TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS')";
		try {
			return Long.valueOf(getHibernateTemplate()
					.find(sql,
							new Object[] { contactId,
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
	 * Title: getSmsSendDetailsByContactId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param contactId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao#getSmsSendDetailsByContactId
	 *      (java.lang.String)
	 */
	@Override
	public List<SmsSendDetail> getSmsSendDetailsByContactId(String contactId,
			DataGridModel dataGridModel) {
		// TODO Auto-generated method stub
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - 1);
		Date historydate = curr.getTime();
		Date nowDate = new Date();
		StringBuffer sql = new StringBuffer(
				"select o.id ,o.mobile,o.connectId,o.uuid,o.batchId,o.receiveStatus,feedbackStatus, o.receiveStatusTime,o.feedbackStatusTime"
						+ " , o.createtime,o.creator,o.source,o.channel,o.receiveStatusDes,o.feedbackStatusDes,o.content from SmsSendDetail o where  connectId=:connectId"
						+ " and o.receiveStatusTime >=TO_DATE( '"
						+ DateTimeUtil.sim3.format(historydate)
						+ "', 'YYYY-MM-DD HH24:MI:SS')"
						+ " and o.receiveStatusTime <=TO_DATE( '"
						+ DateTimeUtil.sim3.format(nowDate)
						+ "', 'YYYY-MM-DD HH24:MI:SS')");
		sql.append(" order by o.createtime desc");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter connectId = new ParameterString("connectId",
				contactId.toString());
		params.put("connectId", connectId);
		Parameter page = new ParameterInteger("page",
				dataGridModel.getStartRow());
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		List objList = findPageList(sql.toString(), params);
		List<SmsSendDetail> result = new ArrayList<SmsSendDetail>();
		Object[] obj = null;
		SmsSendDetail smsSendDetails = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsSendDetails = new SmsSendDetail();
			smsSendDetails.setId((Long) obj[0]);
			if (obj[1] != null) {
				smsSendDetails.setMobile(obj[1].toString());
			}
			if (obj[2] != null) {
				smsSendDetails.setConnectId(obj[2].toString());
			}
			if (obj[3] != null) {
				smsSendDetails.setUuid(obj[3].toString());
			}
			if (obj[4] != null) {
				smsSendDetails.setBatchId(obj[4].toString());
			}
			if (obj[5] != null) {
				smsSendDetails.setReceiveStatus(obj[5].toString());
			}
			if (obj[6] != null) {
				smsSendDetails.setFeedbackStatus(obj[6].toString());
			}
			if (obj[7] != null) {
				smsSendDetails.setReceiveStatusTime(((Date) obj[7]));
			}
			if (obj[8] != null) {
				smsSendDetails.setFeedbackStatusTime(((Date) obj[8]));
			}
			if (obj[9] != null) {
				smsSendDetails.setCreatetime(((Date) obj[9]));
			}
			if (obj[10] != null) {
				smsSendDetails.setCreator(obj[10].toString());
			}
			if (obj[11] != null) {
				smsSendDetails.setSource(obj[11].toString());
			}
			if (obj[12] != null) {
				smsSendDetails.setChannel(obj[12].toString());
			}
			if (obj[13] != null) {
				smsSendDetails.setReceiveStatusDes(obj[13].toString());
			}
			if (obj[14] != null) {
				smsSendDetails.setFeedbackStatusDes(obj[14].toString());
			}
			if (obj[15] != null) {
				smsSendDetails.setContent(obj[15].toString());
			}
			result.add(smsSendDetails);
		}

		return result;
	}
}
