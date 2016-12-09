/*
 * @(#)CouponDaoImpl.java 1.0 2013-8-12下午3:56:07
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.marketing.CouponCr;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CouponCrDao;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-12 下午3:56:07
 * 
 */
@Repository
public class CouponCrDaoImpl extends
		GenericDaoHibernate<CouponCr, java.lang.Long> implements Serializable,
		CouponCrDao {

	public CouponCrDaoImpl() {
		super(CouponCr.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CouponCr getCouponCrByCouponId(CouponCrDto coupon) {
		StringBuffer hql = new StringBuffer(
				"from CouponCr t where t.couponId =:couponId ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		Parameter couponId = new ParameterString("couponId",
				coupon.getCouponId());
		paras.put("couponId", couponId);
		List<CouponCr> list = findList(hql.toString(), paras);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CouponDao#query(com.chinadrtv.erp
	 *      .model.marketing.CouponCr,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public List<CouponCr> query(CouponCrDto coupon, DataGridModel dataModel) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer("from CouponCr c " + "where 1=1 ");
		Map<String, Parameter> params = genSql(sql, coupon);
		sql.append(" order by c.crdt desc");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List<CouponCr> objList = findPageList(sql.toString(), params);

		return objList;
	}

	public Map<String, Parameter> genSql(StringBuffer sql, CouponCrDto coupon) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (coupon != null) {
			if (coupon.getCampaignId() != null) {
				sql.append(" AND c.campaignId=:campaignId");
				Parameter campaignId = new ParameterLong("campaignId",
						coupon.getCampaignId());
				paras.put("campaignId", campaignId);
			}
			if (!StringUtil.isNullOrBank(coupon.getStatus())) {
				sql.append(" AND c.status=:status");
				Parameter status = new ParameterString("status",
						coupon.getStatus());
				paras.put("status", status);
			}
			if (!StringUtil.isNullOrBank(coupon.getStartdtTimes())) {
				sql.append(" AND c.startdt >= TO_DATE(:startdt, 'YYYY-MM-DD HH24:MI:SS')");
				Parameter startdt = null;
				try {
					startdt = new ParameterString("startdt",
							coupon.getStartdtTimes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				paras.put("startdt", startdt);
			}
			if (!StringUtil.isNullOrBank(coupon.getEndTimes())) {
				sql.append(" AND c.enddt <= TO_DATE(:enddt, 'YYYY-MM-DD HH24:MI:SS')");
				Parameter enddt = null;
				try {
					enddt = new ParameterString("enddt", coupon.getEndTimes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				paras.put("enddt", enddt);
			}
		}

		return paras;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryCount
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CouponDao#queryCount(com.chinadrtv
	 *      .erp.model.marketing.CouponCr)
	 */
	@Override
	public Integer queryCount(CouponCrDto coupon) {
		// TODO Auto-generated method stub

		StringBuffer hql = new StringBuffer(
				"select  count (c.id) from CouponCr c where 1=1 ");
		Map<String, Parameter> params = genSql(hql, coupon);
		try {
			return findPageCount(hql.toString(), params);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: saveCouponList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CouponDao#saveCouponList(java.util.List)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveCouponList(List<CouponCr> coupon) {
		// TODO Auto-generated method stub
		final List<CouponCr> coupons = coupon;
		String sql = "insert into IAGENT.COUPON_CR (ID,COUPON_ID,PHONE,CONTACT_ID,STARTDT,ENDDT,STATUS,TYPE,"
				+ " COUPON_VALUE,CRUSR,CRDT,DELIVER_STATUS,"
				+ "CAMPAIGN_ID,SMS_BATCHID,SMS_UUID,USE_MONEY,USE_DEPARTMENT) "
				+ "values(IAGENT.SEQ_COUPON_CR.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					String couponId = coupons.get(i).getCouponId();
					String phone = coupons.get(i).getPhone();
					String contactId = coupons.get(i).getContactId();
					Date startDate = coupons.get(i).getStartdt();
					Date endDate = coupons.get(i).getEnddt();
					String status = coupons.get(i).getStatus();
					String type = coupons.get(i).getType();
					String couponValue = coupons.get(i).getCouponValue();
					String crser = coupons.get(i).getCrusr();
					Date crdt = coupons.get(i).getCrdt();
					String deliverStatus = coupons.get(i).getDeliverStatus();
					Long campaginId = coupons.get(i).getCampaignId();
					String smsBatchId = coupons.get(i).getSmsBatchid();
					String smsUuid = coupons.get(i).getSmsUuid();
					String useMoney = coupons.get(i).getMoneyUse();
					String usedDeparment = coupons.get(i).getUsedDepartment();
					ps.setString(1, couponId);
					ps.setString(2, phone);
					ps.setString(3, contactId);
					ps.setTimestamp(4,
							new java.sql.Timestamp(startDate.getTime()));
					ps.setTimestamp(5,
							new java.sql.Timestamp(endDate.getTime()));
					ps.setString(6, status);
					ps.setString(7, type);
					ps.setString(8, couponValue);
					ps.setString(9, crser);
					ps.setTimestamp(10, new java.sql.Timestamp(crdt.getTime()));
					ps.setString(11, deliverStatus);
					ps.setLong(12, campaginId);
					ps.setString(13, smsBatchId);
					ps.setString(14, smsUuid);
					ps.setString(15, useMoney);
					ps.setString(16, usedDeparment);
				}

				public int getBatchSize() {
					return coupons.size();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: updateCouponCr
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param couponCr
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CouponCrDao#updateCouponCr(com.chinadrtv
	 *      .erp.model.marketing.CouponCr)
	 */
	@Override
	public Boolean updateCouponCr(CouponCr couponCr) {
		// TODO Auto-generated method stub
		String sql = "update iagent.coupon_cr set status = ? where coupon_id =?";
		int i = 0;
		i = jdbcTemplate.update(sql, new Object[] { couponCr.getStatus(),
				couponCr.getCouponId() });
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
}
