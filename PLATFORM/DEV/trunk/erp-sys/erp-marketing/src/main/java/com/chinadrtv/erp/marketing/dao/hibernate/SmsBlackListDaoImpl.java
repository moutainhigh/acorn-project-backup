/*
 * @(#)SmsBlackListDaoImpl.java 1.0 2013-1-22下午4:19:39
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.dao.SmsBlackListDao;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-22 下午4:19:39
 * 
 */
@Repository
public class SmsBlackListDaoImpl extends
		GenericDaoHibernate<SmsBlackList, java.lang.String> implements
		SmsBlackListDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private CampaignDao campaignDao;

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
	public SmsBlackListDaoImpl(Class<SmsBlackList> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
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
	public SmsBlackListDaoImpl(Class<SmsBlackList> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
		// TODO Auto-generated constructor stub
	}

	public SmsBlackListDaoImpl() {
		super(SmsBlackList.class);
	}

	/*
	 * (非 Javadoc) <p>Title: insertBlcakList</p> <p>Description: </p>
	 * 
	 * @param smsBlackList
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsBlackListDao#insertBlcakList(com.chinadrtv
	 * .erp.marketing.model.SmsBlackList)
	 */
	public void insertBlcakList(SmsBlackList smsBlackList) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(smsBlackList);

	}

	/*
	 * (非 Javadoc) <p>Title: querAll</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsBlackListDao#querAll()
	 */
	public List<SmsBlackList> querAll() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from SmsBlackList where 1=1");
	}

	/**
	 * 根据联系人id 获得手机号码
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsBlackListDao#queryPhoneList(java.util
	 *      .List)
	 * 
	 * 
	 *      SELECT PHN2, CONTACTID from (SELECT PHN2, CONTACTID, ROWNUM AS NO
	 *      FROM iagent22.phone p WHERE exists (select DISTINCT t3.customer_Id
	 *      from CRMMARKETING.Customer_Detail t3 where t3.customer_id =
	 *      p.contactid and group_code = 'G000000527') and not exists (select 1
	 *      from sms_blacklist b where b.user_phone_num=p.phn2))
	 */
	public List<Map<String, Object>> queryPhoneList(String groupCode,
			String flag, String blackFlag, Integer begin, Integer end) {
		StringBuilder sql = new StringBuilder(
				"SELECT PHN2, CONTACTID from (SELECT /*+ first_rows*/ PHN2, CONTACTID,phonetypid,ROWNUM AS NO FROM  iagent.phone p WHERE exists (select DISTINCT t3.customer_Id from Customer_Detail t3 where t3.customer_id = p.contactid and group_code = ?  ) ");

		if (flag != null && flag.equals("on")) {
			sql.append(" AND  PRMPHN = 'Y'");
		}

		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
		}

		// sql.append(" ) x where 1=1 ");

		sql.append(") where no> ? and no <= ?  ");
		// TODO Auto-generated method stub
		List<Map<String, Object>> phoneList = jdbcTemplate.queryForList(
				sql.toString(), new Object[] { groupCode, begin, end });
		return phoneList;
	}

	/***
	 * 查出用户 和 手机总数
	 * 
	 * @Description: TODO
	 * @param blackFlag
	 * @param phoneFlag
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Integer getUserCount(String groupCode, String phoneFlag,
			String blackFlag) {
		StringBuilder sql = new StringBuilder(
				"SELECT  count(*)  FROM ( select /*+ first_rows*/  phn2,contactid,phonetypid,rownum as no from iagent.phone p WHERE exists   (select DISTINCT t3.customer_Id    from Customer_Detail t3   where t3.customer_id=p.contactid and group_code=? ) ");

		if (phoneFlag != null && phoneFlag.equals("on")) {
			sql.append(" AND  PRMPHN = 'Y' ");
		}
		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
		}
		// sql.append(") x where 1=1 ");

		sql.append(")");
		return jdbcTemplate.queryForInt(sql.toString(),
				new Object[] { groupCode });
	}

	/**
	 * 根据联系人id 获得手机号码
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsBlackListDao#queryPhoneList(java.util
	 *      .List)
	 * 
	 * 
	 *      SELECT PHN2, CONTACTID from (SELECT PHN2, CONTACTID, ROWNUM AS NO
	 *      FROM iagent22.phone p WHERE exists (select DISTINCT t3.customer_Id
	 *      from CRMMARKETING.Customer_Detail t3 where t3.customer_id =
	 *      p.contactid and group_code = 'G000000527') and not exists (select 1
	 *      from sms_blacklist b where b.user_phone_num=p.phn2))
	 */
	public List<Map<String, Object>> queryPhoneListForCampaign(
			String groupCode, String flag, String blackFlag, Integer begin,
			Integer end, Long campaignId, Date now) {
		Campaign campaign = campaignDao.get(campaignId);
		Date campaignCrDate = campaign.getCreateDate();
		Date customerCrDate = campaign.getGroup().getCrtDate();
		StringBuilder sql = new StringBuilder("SELECT PHN2, CONTACTID from (");

		// sql.append("SELECT * FROM (");
		sql.append("SELECT /*+ first_rows*/ PHN2, CONTACTID,ROWNUM AS NO FROM  iagent.phone p WHERE  p.contactid in  ");
		sql.append("(");
		sql.append("select tmp.connectid from (");
		sql.append("select t3.customer_id connectid ");
		sql.append("  from Customer_Detail t3 ");
		sql.append(" where group_code = ?   and t3.crt_date >= ? and t3.crt_date <= ? ");
		sql.append("   and not exists (select 1 ");
		sql.append("          from campaign_receiver c ");
		sql.append("         where c.customer_id = t3.customer_id  and c.batch_code = t3.batch_code and c.campaign_id=?  and c.create_date >= ? and  c.create_date <= ?) ");
		sql.append("	and t3.crt_date<=? ");
		sql.append("union all ");
		sql.append("select t.connectid connectid ");
		sql.append("  from sms_send_detail t ");
		sql.append(" where t.smsstop_status = '1' ");
		sql.append("   and t.batchid in  ");
		sql.append(" (select c.batch_id from campaign_batch c where  c.campaign_id=?) ");
		sql.append(" ) tmp ");
		sql.append(")");

		if (flag != null && flag.equals("on")) {
			sql.append(" AND  PRMPHN = 'Y'");
		}

		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
		}
		// sql.append(") x where 1=1 ");
		sql.append(" ) where no> ? and no <= ?");
		// TODO Auto-generated method stub\

		List<Map<String, Object>> phoneList = jdbcTemplate.queryForList(
				sql.toString(), new Object[] { groupCode, customerCrDate, now,
						campaignId, campaignCrDate, now, now, campaignId,
						begin, end });
		return phoneList;
	}

	// public List<Map<String, Object>> queryPhoneListForCampaign(
	// String groupCode, String flag, String blackFlag, Integer begin,
	// Integer end, Long campaignId, Date now) {
	// StringBuilder sql = new StringBuilder("SELECT PHN2, CONTACTID from (");
	//
	// // sql.append("SELECT * FROM (");
	// sql.append("SELECT /*+ first_rows*/ PHN2, CONTACTID,ROWNUM AS NO FROM  iagent.phone p WHERE  p.contactid in  ");
	// sql.append("(");
	// sql.append("select tmp.connectid from (");
	// sql.append("select t3.customer_id connectid ");
	// sql.append("  from Customer_Detail t3 ");
	// sql.append(" where group_code = ? ");
	// sql.append("   and not exists (select 1 ");
	// sql.append("          from campaign_receiver c ");
	// sql.append("         where c.customer_id = t3.customer_id  and c.batch_code = t3.batch_code and c.campaign_id=?) ");
	// sql.append("	and t3.crt_date<=? ");
	// sql.append("union all ");
	// sql.append("select t.connectid connectid ");
	// sql.append("  from sms_send_detail t ");
	// sql.append(" where t.smsstop_status = '1' ");
	// sql.append("   and t.batchid in  ");
	// sql.append(" (select c.batch_id from campaign_batch c where  c.campaign_id=?) ");
	// sql.append(" ) tmp ");
	// sql.append(")");
	//
	// if (flag != null && flag.equals("on")) {
	// sql.append(" AND  PRMPHN = 'Y'");
	// }
	//
	// if (blackFlag != null && blackFlag.equals("on")) {
	// sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
	// }
	//
	// // sql.append(") x where 1=1 ");
	//
	// sql.append(" ) where no> ? and no <= ?");
	// // TODO Auto-generated method stub
	// List<Map<String, Object>> phoneList = jdbcTemplate.queryForList(
	// sql.toString(), new Object[] { groupCode, campaignId, now,
	// campaignId, begin, end });
	// return phoneList;
	// }

	/***
	 * 查出用户 和 手机总数
	 * 
	 * @Description: TODO
	 * @param blackFlag
	 * @param phoneFlag
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public Integer getUserCountForCampaign(String groupCode, String phoneFlag,
			String blackFlag, Long campaignId, Date now) {
		Campaign campaign = campaignDao.get(campaignId);
		Date campaignCrDate = campaign.getCreateDate();
		Date customerCrDate = campaign.getGroup().getCrtDate();

		StringBuilder sql = new StringBuilder("SELECT  count(*)  FROM (");
		// sql.append("SELECT * FROM (");
		sql.append("SELECT /*+ first_rows*/ PHN2, CONTACTID, ROWNUM AS NO FROM  iagent.phone p WHERE p.contactid in  ");
		sql.append("(");
		sql.append("select tmp.connectid from (");
		sql.append("select t3.customer_id connectid ");
		sql.append("  from Customer_Detail t3  ");
		sql.append(" where group_code = ?  and t3.crt_date >= ? and t3.crt_date <= ? ");
		sql.append("   and not exists (select 1 ");
		sql.append("          from campaign_receiver c  ");
		sql.append("         where c.customer_id = t3.customer_id and c.batch_code = t3.batch_code and c.campaign_id= ? and c.create_date >= ? and  c.create_date <= ?) ");
		sql.append("	and t3.crt_date<=? ");
		sql.append("union all ");
		sql.append("select t.connectid connectid ");
		sql.append("  from sms_send_detail t ");
		sql.append(" where t.smsstop_status = '1' ");
		sql.append("   and t.batchid in  ");

		sql.append(" (select c.batch_id from campaign_batch c where c.campaign_id=?) ");
		sql.append(" ) tmp ");
		sql.append(") ");

		if (phoneFlag != null && phoneFlag.equals("on")) {
			sql.append("AND  PRMPHN = 'Y' ");
		}

		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
		}

		// sql.append(") x where 1=1 ");

		sql.append(")");
		return jdbcTemplate.queryForInt(sql.toString(), new Object[] {
				groupCode, customerCrDate, now, campaignId, campaignCrDate,
				now, now, campaignId });
	}

	// public Integer getUserCountForCampaign(String groupCode, String
	// phoneFlag,
	// String blackFlag, Long campaignId, Date now) {
	// StringBuilder sql = new StringBuilder("SELECT  count(*)  FROM (");
	//
	// // sql.append("SELECT * FROM (");
	// sql.append("SELECT /*+ first_rows*/ PHN2, CONTACTID, ROWNUM AS NO FROM  iagent.phone p WHERE p.contactid in  ");
	// sql.append("(");
	// sql.append("select tmp.connectid from (");
	// sql.append("select t3.customer_id connectid ");
	// sql.append("  from Customer_Detail t3 ");
	// sql.append(" where group_code = ? ");
	// sql.append("   and not exists (select 1 ");
	// sql.append("          from campaign_receiver c ");
	// sql.append("         where c.customer_id = t3.customer_id and c.batch_code = t3.batch_code and c.campaign_id=?) ");
	// sql.append("	and t3.crt_date<=? ");
	// sql.append("union all ");
	// sql.append("select t.connectid connectid ");
	// sql.append("  from sms_send_detail t ");
	// sql.append(" where t.smsstop_status = '1' ");
	// sql.append("   and t.batchid in  ");
	//
	// sql.append(" (select c.batch_id from campaign_batch c where c.campaign_id=?) ");
	// sql.append(" ) tmp ");
	// sql.append(") ");
	//
	// if (phoneFlag != null && phoneFlag.equals("on")) {
	// sql.append("AND  PRMPHN = 'Y' ");
	// }
	//
	// if (blackFlag != null && blackFlag.equals("on")) {
	// sql.append(" and not exists (select 1 from sms_blacklist b where b.user_phone_num=p.phn2)");
	// }
	//
	// // sql.append(") x where 1=1 ");
	//
	// sql.append(")");
	// return jdbcTemplate.queryForInt(sql.toString(), new Object[] {
	// groupCode, campaignId, now, campaignId });
	// }

	/**
	 * 
	 * @Description: 查询发送营销短信潜客数量
	 * @param groupCode
	 * @param phoneFlag
	 * @param blackFlag
	 * @param campaignId
	 * @param now
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer getUserCountForCampaignPotential(String groupCode,
			String phoneFlag, String blackFlag, Long campaignId, Date now) {
		StringBuilder sql = new StringBuilder("SELECT  count(*)  FROM (");

		sql.append("SELECT  /*+ first_rows*/p.phone2 PHN2, p.potential_contact_id as CONTACTID, ROWNUM AS NO FROM  acoapp_marketing.potential_contact_phone p WHERE p.potential_contact_id in ");
		sql.append("(");
		sql.append("select tmp.connectid from (");
		sql.append("select t3.customer_id connectid ");
		sql.append("  from Customer_Detail t3 ");
		sql.append(" where group_code = ? ");
		sql.append("   and not exists (select 1 ");
		sql.append("          from campaign_receiver c ");
		sql.append("         where c.customer_id = t3.customer_id and c.batch_code = t3.batch_code and c.campaign_id=?) ");
		sql.append("	and t3.crt_date<=? ");
		sql.append("union all ");
		sql.append("select t.connectid connectid ");
		sql.append("  from sms_send_detail t ");
		sql.append(" where t.smsstop_status = '1' ");
		sql.append("   and t.batchid in  ");

		sql.append(" (select c.batch_id from campaign_batch c where c.campaign_id=?) ");
		sql.append(" ) tmp ");
		sql.append(") ");
		sql.append(" and p.phone_type_id='4' ");
		sql.append(" and p.phone2 is not null");
		if (phoneFlag != null && phoneFlag.equals("on")) {
			sql.append("AND  p.is_default = 'Y' ");
		}

		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and (p.is_black is null or p.is_black=0)");
		}

		sql.append(")");
		return jdbcTemplate.queryForInt(sql.toString(), new Object[] {
				groupCode, campaignId, now, campaignId });
	}

	/**
	 * 
	 * @Description: 查询潜客电话列表
	 * @param groupCode
	 * @param flag
	 * @param blackFlag
	 * @param begin
	 * @param end
	 * @param campaignId
	 * @param now
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> queryPhoneListForCampaignPotential(
			String groupCode, String flag, String blackFlag, Integer begin,
			Integer end, Long campaignId, Date now) {
		StringBuilder sql = new StringBuilder("SELECT PHN2, CONTACTID from (");

		sql.append("SELECT  /*+ first_rows*/p.phone2 PHN2, p.potential_contact_id as CONTACTID, ROWNUM AS NO FROM  acoapp_marketing.potential_contact_phone p WHERE p.potential_contact_id in ");
		sql.append("(");
		sql.append("select tmp.connectid from (");
		sql.append("select t3.customer_id connectid ");
		sql.append("  from Customer_Detail t3 ");
		sql.append(" where group_code = ? ");
		sql.append("   and not exists (select 1 ");
		sql.append("          from campaign_receiver c ");
		sql.append("         where c.customer_id = t3.customer_id and c.batch_code = t3.batch_code and c.campaign_id=?) ");
		sql.append("	and t3.crt_date<=? ");
		sql.append("union all ");
		sql.append("select t.connectid connectid ");
		sql.append("  from sms_send_detail t ");
		sql.append(" where t.smsstop_status = '1' ");
		sql.append("   and t.batchid in  ");

		sql.append(" (select c.batch_id from campaign_batch c where c.campaign_id=?) ");
		sql.append(" ) tmp ");
		sql.append(") ");
		sql.append(" and p.phone_type_id='4' ");
		sql.append(" and p.phone2 is not null");
		if (flag != null && flag.equals("on")) {
			sql.append("AND  p.is_default = 'Y' ");
		}

		if (blackFlag != null && blackFlag.equals("on")) {
			sql.append(" and (p.is_black is null or p.is_black=0)");
		}

		sql.append(" ) where no> ? and no <= ?");
		// TODO Auto-generated method stub
		List<Map<String, Object>> phoneList = jdbcTemplate.queryForList(
				sql.toString(), new Object[] { groupCode, campaignId, now,
						campaignId, begin, end });
		return phoneList;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getContact
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param contactid
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsBlackListDao#getContact(java.lang.
	 *      String)
	 */
	public List<Map<String, Object>> getContact(String contactid) {
		// TODO Auto-generated method stub
		String sql = "select contactid, name,sex from iagent.contact  where contactid=?";
		return jdbcTemplate.queryForList(sql, new Object[] { contactid });
	}

}
