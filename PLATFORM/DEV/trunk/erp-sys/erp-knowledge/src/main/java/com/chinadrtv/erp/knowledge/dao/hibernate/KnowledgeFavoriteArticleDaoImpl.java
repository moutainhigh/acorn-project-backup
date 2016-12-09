/*
 * @(#)KnowledgeFavoriteArticleDaoImpl.java 1.0 2013-12-3下午2:15:18
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
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.knowledge.dao.KnowledgeFavoriteArticleDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-3 下午2:15:18
 * 
 */
@Repository
public class KnowledgeFavoriteArticleDaoImpl extends
		GenericDaoHibernate<KnowledgeFavoriteArticle, java.lang.Long> implements
		KnowledgeFavoriteArticleDao {

	public KnowledgeFavoriteArticleDaoImpl() {
		super(KnowledgeFavoriteArticle.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public KnowledgeFavoriteArticle getFavoriteArticle(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle) {
		// TODO Auto-generated method stub

		String sql = "from KnowledgeFavoriteArticle k where k.articleId = ? and k.createUser = ? and k.status='1'  and k.deptType = ? ";

		List<KnowledgeFavoriteArticle> list = getHibernateTemplate().find(
				sql,
				new Object[] { knowledgeFavoriteArticle.getArticleId(),
						knowledgeFavoriteArticle.getCreateUser(),
						knowledgeFavoriteArticle.getDeptType() });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<KnowledgeFavoriteArticle> myFavoriteArticles(String user,
			String deptype) {
		// TODO Auto-generated method stub
		String sql = "select *  from ( select k.*  from acoapp_marketing.Knowledge_Favorite_Article k,acoapp_marketing.Knowledge_Article k1    where k.article_Id=k1.id  and k.status='1' and k1.status='1' and k.create_User = '"
				+ user
				+ "' and k.Depart_Type = '"
				+ deptype
				+ "' and k1.depart_Type = '"
				+ deptype
				+ "'   order by k.create_Date desc) where rownum<=10";

		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		org.hibernate.Query query = session.createSQLQuery(sql).addEntity(
				KnowledgeFavoriteArticle.class);
		List<KnowledgeFavoriteArticle> list = query.list();
		return list;
	}

	@Override
	public List<Map<String, Object>> hotFavoriteArticles(String dtype) {
		// TODO Auto-generated method stub
		String sql = "  select article_id ,category_id,times from ("
				+ "select t.article_id , t.category_id ,count(t.article_id) as times  from acoapp_marketing.knowledge_favorite_article t , acoapp_marketing.KNOWLEDGE_ARTICLE t1"
				+ " where   " + "  t.depart_type = ? "
				+ " and t1.depart_type = ? " + " and t.article_id = t1.id"
				+ " and t1.status='1' " + " and t.status='1' "
				+ " group by t.article_id , t.category_id order by times desc"
				+ "  ) where rownum <=10";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new Object[] { dtype, dtype });
		return list;
	}

	@Override
	public Integer queryCounts(KnowledgeFavoriteArticle knowledgeFavoriteArticle) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer(
				" select count (k.id) from KnowledgeFavoriteArticle k,KnowledgeArticle k1  where k.status = '1' and k1.status ='1'  and k.articleId=k1.id  ");
		Map<String, Parameter> params = gensql(sql, knowledgeFavoriteArticle);
		Integer result = findPageCount(sql.toString(), params);
		return result;
	}

	private Map<String, Parameter> gensql(StringBuffer sql,
			KnowledgeFavoriteArticle knowledgeFavoriteArticle) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		if (!StringUtil.isNullOrBank(knowledgeFavoriteArticle.getDeptType())) {
			sql.append(" AND k.deptType=:deptType");
			Parameter deptType = new ParameterString("deptType",
					knowledgeFavoriteArticle.getDeptType().toString());
			paras.put("deptType", deptType);
		}
		if (!StringUtil.isNullOrBank(knowledgeFavoriteArticle.getCreateUser())) {
			sql.append(" AND k.createUser=:createUser");
			Parameter createUser = new ParameterString("createUser",
					knowledgeFavoriteArticle.getCreateUser().toString());
			paras.put("createUser", createUser);
		}

		return paras;

	}

	@Override
	public List<KnowledgeFavoriteArticle> query(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer(
				" from KnowledgeFavoriteArticle k ,KnowledgeArticle k1  where k.status='1'  and k1.status = '1' and k.articleId=k1.id ");
		Map<String, Parameter> params = gensql(sql, knowledgeFavoriteArticle);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by k.createDate desc ");
		List<KnowledgeFavoriteArticle> objList = findPageList(sql.toString(),
				params);
		return objList;
	}
}
