/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterDate;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.UsrTaskRecommendDao;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 推荐产品规则记录</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:24:24
 * 
 */
@Repository
public class UsrTaskRecommendDaoImpl extends
		GenericDaoHibernate<UsrTaskRecommend, java.lang.Long> implements
		UsrTaskRecommendDao {

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
	public UsrTaskRecommendDaoImpl() {
		super(UsrTaskRecommend.class);
	}

	/**
	 * 更新实际推荐产品id
	 */
	public int updateProductId(Integer id, String productId, String usr) {
		String hql = "update UsrTaskRecommend set prouduct_id=:productId,up_user=:upUser,up_date=:upDate "
				+ "where id=:id";
		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter product = new ParameterString("productId", productId);
		Parameter upUsr = new ParameterString("upUser", usr);
		Parameter upDate = new ParameterDate("upDate", new Date());
		Parameter idPara = new ParameterInteger("id", id);
		params.put("product", product);
		params.put("upUsr", upUsr);
		params.put("upDate", upDate);
		params.put("idPara", idPara);

		return this.execUpdate(hql, params);
	}
	
	public UsrTaskRecommend getRecommendByInstId(String instId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (select A.*, rownum rn from ( select * from ")
		  .append(Constants.MARKETING_SCHEMA)
		  .append(".usrtask_recommend ur where ur.process_insid= :instId")
		  .append(" ) A where rownum<=:rows ) where rn >:page");
		SQLQuery sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setString("instId", instId);
		sqlQuery.setInteger("page", 0);
		sqlQuery.setInteger("rows", 1);
		UsrTaskRecommend usrTaskRecommend = (UsrTaskRecommend)sqlQuery.addEntity(UsrTaskRecommend.class).uniqueResult();
		return usrTaskRecommend;
	}

}
