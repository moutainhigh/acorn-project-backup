/*
 * @(#)CouponUseDaoImpl.java 1.0 2013-8-15下午3:03:50
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
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.marketing.CouponUse;
import com.chinadrtv.erp.smsapi.dao.CouponUseDao;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-15 下午3:03:50
 * 
 */
@Repository
public class CouponUseDaoImpl extends
		GenericDaoHibernate<CouponUse, java.lang.Long> implements Serializable,
		CouponUseDao {

	public CouponUseDaoImpl() {
		super(CouponUse.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * (非 Javadoc)
	 * <p>
	 * Title: saveCouponUseList
	 * </p>
	 * <p>
	 * Description:批量保存 couponUse
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CouponUseDao#saveCouponUseList(java.util
	 *      .List)
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void saveCouponUseList(List<CouponUse> couponUse) {
		// TODO Auto-generated method stub
		final List<CouponUse> coupons = couponUse;
		String sql = "insert into IAGENT.COUPON_USE (ID,CONTACT_ID,PHONE,COUPON_VALUE,CRUSR,CRDT,COUPON_TYPE,CAMPAIGN_ID,"
				+ " SMS_BATCHID,SMS_UUID,COUPON_ID) "
				+ "values(IAGENT.SEQ_COUPON_USE.nextval,?,?,?,?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					String contactId = coupons.get(i).getContactId();
					String phone = coupons.get(i).getPhone();
					String couponValue = coupons.get(i).getCouponValue();
					String crser = coupons.get(i).getCrusr();
					Date crdt = coupons.get(i).getCrdt();
					String couponType = coupons.get(i).getCouponType();
					Long campaginId = coupons.get(i).getCampaignId();
					String smsBatchId = coupons.get(i).getSmsBatchid();
					String smsUuid = coupons.get(i).getSmsUuid();
					String couponId = coupons.get(i).getCouponId();
					ps.setString(1, contactId);
					ps.setString(2, phone);
					ps.setString(3, couponValue);
					ps.setString(4, crser);
					ps.setTimestamp(5, new java.sql.Timestamp(crdt.getTime()));
					ps.setString(6, couponType);
					ps.setLong(7, campaginId);
					ps.setString(8, smsBatchId);
					ps.setString(9, smsUuid);
					ps.setString(10, couponId);
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
	 * Title: getCouponUseByCouponId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * @return
	 * @see com.chinadrtv.erp.smsapi.dao.CouponUseDao#getCouponUseByCouponId(com.chinadrtv.erp.smsapi.dto.CouponCrDto)
	 */
	public CouponUse getCouponUseByCouponId(CouponCrDto coupon) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(
				"from CouponUse t where t.couponId =:couponId ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		Parameter couponId = new ParameterString("couponId",
				coupon.getCouponId());
		paras.put("couponId", couponId);
		List<CouponUse> list = findList(hql.toString(), paras);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}

	}

	/*
	 * (非 Javadoc) <p>Title: updateCouponUse</p> <p>Description: </p>
	 * 
	 * @param couponUse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.CouponUseDao#updateCouponUse(com.chinadrtv
	 * .erp.model.marketing.CouponUse)
	 */
	@Override
	public Boolean updateCouponUse(CouponUse couponUse) {
		// TODO Auto-generated method stub

		String sql = "update iagent.coupon_use set type = ?  , use_phone = ? , use_contact_id = ? where coupon_id = ?";

		int i = jdbcTemplate.update(sql,
				new Object[] { couponUse.getType(), couponUse.getPhone(),
						couponUse.getUseContactId(), couponUse.getCouponId() });
		if (i > 0) {
			return true;

		} else {
			return false;

		}
	}
}
