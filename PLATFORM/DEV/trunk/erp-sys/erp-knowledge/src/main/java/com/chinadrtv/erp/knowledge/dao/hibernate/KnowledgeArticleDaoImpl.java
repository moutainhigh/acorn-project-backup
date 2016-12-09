/*
 * @(#)KnowledgeArticleDaoImpl.java 1.0 2013-11-6下午1:45:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.knowledge.dao.KnowledgeArticleDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-6 下午1:45:30
 * 
 */
@Repository
public class KnowledgeArticleDaoImpl extends
		GenericDaoHibernate<KnowledgeArticle, java.lang.Long> implements
		KnowledgeArticleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public KnowledgeArticleDaoImpl() {
		super(KnowledgeArticle.class);
	}

	@Override
	public KnowledgeArticle getByProductCode(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub
		String hql = "from KnowledgeArticle o where o.productCode='"
				+ knowledgeArticle.getProductCode() + "' and o.departType='"
				+ knowledgeArticle.getDepartType() + "'";

		List<KnowledgeArticle> list = getHibernateTemplate().find(hql);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long getSeq() {
		// TODO Auto-generated method stub
		String sql = "select acoapp_marketing.SEQ_KNOWLEDGE_ARTICLE.nextval from dual";
		return jdbcTemplate.queryForLong(sql);
	}

	@Override
	public Integer queryCounts(KnowledgeArticle knowledgeArticle) {

		StringBuffer sql = new StringBuffer(
				"select count(k.id) from KnowledgeArticle k where k.status='1' ");
		// TODO Auto-generated method stub
		Map<String, Parameter> params = gensql(sql, knowledgeArticle);

		Integer result = findPageCount(sql.toString(), params);
		return result;
	}

	@Override
	public List<KnowledgeArticle> query(KnowledgeArticle knowledgeArticle,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				" from KnowledgeArticle k where k.status='1' ");
		Map<String, Parameter> params = gensql(sql, knowledgeArticle);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by k.createDate desc ");
		List<KnowledgeArticle> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Map<String, Parameter> gensql(StringBuffer sql,
			KnowledgeArticle knowledgeArticle) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(knowledgeArticle.getDepartType())) {
			sql.append(" AND k.departType=:departType");
			Parameter departType = new ParameterString("departType",
					knowledgeArticle.getDepartType().toString());
			paras.put("departType", departType);
		}
		if (knowledgeArticle.getCategoryId() != null) {
			sql.append(" AND k.categoryId=:categoryId");
			Parameter categoryId = new ParameterLong("categoryId",
					knowledgeArticle.getCategoryId());
			paras.put("categoryId", categoryId);
		}
		if (!StringUtil.isNullOrBank(knowledgeArticle.getProductCode())) {
			sql.append(" AND ( k.productCode  like:productCode");
			Parameter productCode = new ParameterString("productCode", "%"
					+ knowledgeArticle.getProductCode().toString() + "%");
			paras.put("productCode", productCode);
		}
		if (!StringUtil.isNullOrBank(knowledgeArticle.getShortPinyin())) {
			sql.append(" or k.shortPinyin like:shortPinyin");
			Parameter shortPinyin = new ParameterString("shortPinyin", "%"
					+ knowledgeArticle.getShortPinyin().toString()
							.toUpperCase() + "%");
			paras.put("shortPinyin", shortPinyin);
		}
		if (!StringUtil.isNullOrBank(knowledgeArticle.getProductName())) {
			sql.append(" or k.productName like:productName");
			Parameter productName = new ParameterString("productName", "%"
					+ knowledgeArticle.getProductName().toString() + "%");
			paras.put("productName", productName);
		}
		if (!StringUtil.isNullOrBank(knowledgeArticle.getTitle())) {
			sql.append(" or k.title like:title )");
			Parameter title = new ParameterString("title", "%"
					+ knowledgeArticle.getTitle().toString() + "%");
			paras.put("title", title);
		}
		return paras;

	}

	@Override
	public List<KnowledgeArticle> queryList(Long cid, String depType) {
		// TODO Auto-generated method stub
		String sql = "from KnowledgeArticle k where 1=1 and k.categoryId="
				+ cid + " and k.status='1' and k.departType ='" + depType + "'";
		List<KnowledgeArticle> list = getHibernateTemplate().find(sql);
		return list;
	}

	@Override
	public Boolean isExist(Long cid) {
		// TODO Auto-generated method stub
		String hql = "from KnowledgeArticle k where k.status='1' and  k.categoryId="
				+ cid;
		Boolean flag = false;
		List list = getHibernateTemplate().find(hql);
		if (list != null && !list.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public List<KnowledgeArticle> getNewAdds(Long nums, String dtype) {
		// TODO Auto-generated method stub
		String sql = " select * from (select k.*  from acoapp_marketing.Knowledge_Article k where k.status='1' and k.depart_Type = '"
				+ dtype
				+ "'  order by k.create_Date desc ) where rownum <="
				+ nums;
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		org.hibernate.Query query = session.createSQLQuery(sql).addEntity(
				KnowledgeArticle.class);
		List<KnowledgeArticle> list = query.list();

		return list;
	}

	public KnowledgeArticle getByarticleId(String dept, Long articled) {
		String sql = "from KnowledgeArticle k where k.status='1' and k.departType = ?  and k.id = ?";
		List<KnowledgeArticle> list = getHibernateTemplate().find(sql,
				new Object[] { dept, articled });

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}

	}

	public List<KnowledgeArticle> getAKnowledgeArticles(String dept,
			String searchType) {
		String sql = "from KnowledgeArticle k where k.status='1' and k.departType = ?  and k.type = ?";
		List<KnowledgeArticle> list = getHibernateTemplate().find(sql,
				new Object[] { dept.toUpperCase(), searchType });
		return list;

	}
}
