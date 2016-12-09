/*
 * @(#)CouponConfigDaoImpl.java 1.0 2013-8-23下午3:15:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CouponConfigDao;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午3:15:42
 * 
 */
@Repository
public class CouponConfigDaoImpl extends
		GenericDaoHibernate<CouponConfig, java.lang.Long> implements
		Serializable, CouponConfigDao {

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
	public CouponConfigDaoImpl() {
		super(CouponConfig.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: query</p> <p>Description: </p>
	 * 
	 * @param couponConfig
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.CouponConfigDao#query(com.chinadrtv.erp.
	 * model.marketing.CouponConfig,
	 * com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public List<CouponConfig> query(CouponConfig couponConfig,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub

		StringBuffer hql = new StringBuffer(
				"from CouponConfig t where t.status = '1' ");
		Map<String, Parameter> paras = genSql(couponConfig, hql);

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		paras.put("page", page);
		paras.put("rows", rows);
		hql.append(" order by t.crdt desc");

		List<CouponConfig> list = findPageList(hql.toString(), paras);
		return list;
	}

	public Map<String, Parameter> genSql(CouponConfig couponConfig,
			StringBuffer sql) {
		Map<String, Parameter> result = new HashMap<String, Parameter>();

		if (couponConfig.getCampaignId() != null) {
			sql.append(" and t.campaignId =:campaignId");
			Parameter campaignId = new ParameterLong("campaignId",
					couponConfig.getCampaignId());
			result.put("campaignId", campaignId);
		}
		if (!StringUtil.isNullOrBank(couponConfig.getSmsSendType())) {
			sql.append(" and t.smsSendType =:smsSendType");
			Parameter smsSendType = new ParameterString("smsSendType",
					couponConfig.getSmsSendType());
			result.put("smsSendType", smsSendType);
		}
		if (!StringUtil.isNullOrBank(couponConfig.getCouponType())) {
			sql.append(" and t.couponType =:couponType");
			Parameter couponType = new ParameterString("couponType",
					couponConfig.getCouponType());
			result.put("couponType", couponType);
		}
		if (!StringUtil.isNullOrBank(couponConfig.getCouponConfigName())) {
			sql.append(" and t.couponConfigName =:couponConfigName");
			Parameter getCouponConfigName = new ParameterString(
					"couponConfigName", couponConfig.getCouponConfigName());
			result.put("couponConfigName", getCouponConfigName);
		}

		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryCount</p> <p>Description: </p>
	 * 
	 * @param couponConfig
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.dao.CouponConfigDao#queryCount(com.chinadrtv
	 * .erp.model.marketing.CouponConfig)
	 */
	@Override
	public Integer queryCount(CouponConfig couponConfig) {
		// TODO Auto-generated method stub

		StringBuffer hql = new StringBuffer(
				"select count(t.id) from  CouponConfig t where t.status = '1' ");
		Map<String, Parameter> paras = genSql(couponConfig, hql);

		return findPageCount(hql.toString(), paras);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: updateStatus
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CouponConfigDao#updateStatus(java.lang.Long)
	 */
	@Override
	public Boolean updateStatus(Long id) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		String sql = "update iagent.coupon_config set status = '0' where id=  "
				+ id;
		try {
			jdbcTemplate.execute(sql);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * *(非 Javadoc)
	 * <p>
	 * Title: queryList
	 * </p>
	 * <p>
	 * Description:根据部门查询所有单条couponconfig
	 * </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CouponConfigDao#queryList()
	 */
	@Override
	public List<CouponConfig> queryList(String deparment) {
		// TODO Auto-generated method stub
		String sql = "select t  from CouponConfig  t where t.status = '1' and smsSendType = '1' and t.useDeparment like '%"
				+ deparment + "%' and t.enddt > sysdate";
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		List<CouponConfig> list = findList(sql, paras);
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}
}
