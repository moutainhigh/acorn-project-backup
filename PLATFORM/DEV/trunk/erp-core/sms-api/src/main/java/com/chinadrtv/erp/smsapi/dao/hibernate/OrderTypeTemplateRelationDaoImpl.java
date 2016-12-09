/*
 * @(#)OrderTypeTemplateRelationDaoImpl.java 1.0 2013-5-24下午3:27:23
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.smsapi.dao.OrderTypeTemplateRelationDao;
import com.chinadrtv.erp.smsapi.model.OrderTypeTemplateRelation;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-24 下午3:27:23
 * 
 */
@Repository
public class OrderTypeTemplateRelationDaoImpl extends
		GenericDaoHibernate<OrderTypeTemplateRelation, java.lang.Long>
		implements Serializable, OrderTypeTemplateRelationDao {

	public OrderTypeTemplateRelationDaoImpl() {
		super(OrderTypeTemplateRelation.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	 * (非 Javadoc)
	 * <p>
	 * Title: getTemplateByOrderType
	 * </p>
	 * <p>
	 * Description:根据订单类型查询订单模板
	 * </p>
	 * 
	 * @param orderType
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.OrderTypeTemplateRelationDao#
	 *      getTemplateByOrderType(java.lang.String)
	 */
	public OrderTypeTemplateRelation getTemplateByOrderType(String orderType) {
		// TODO Auto-generated method stub
		String hql = "from OrderTypeTemplateRelation  where 1=1 and  orderType =:orderType";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString("orderType", orderType);
		List<OrderTypeTemplateRelation> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getchechCode
	 * </p>
	 * <p>
	 * Description: 生成验证码
	 * </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.OrderTypeTemplateRelationDao#getchechCode()
	 */
	public Long getchechCode() {
		// TODO Auto-generated method stub
		String sql = "SELECT trunc(dbms_random.value(1000000000, 9999999999)) from dual";
		Long checkcode = jdbcTemplate.queryForLong(sql);
		return checkcode;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: insertCode
	 * </p>
	 * <p>
	 * Description:插入 ordercheckcode 表
	 * </p>
	 * 
	 * @param checkCode
	 * 
	 * @param orderId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.OrderTypeTemplateRelationDao#insertCode(java.lang.String,
	 *      java.lang.String)
	 */
	public Boolean insertCode(String checkCode, String orderId) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO iagent.ordercheckcode (orderid,checkcode)  VALUES (?,?)";
		int flag = jdbcTemplate
				.update(sql, new Object[] { orderId, checkCode });
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}
}
