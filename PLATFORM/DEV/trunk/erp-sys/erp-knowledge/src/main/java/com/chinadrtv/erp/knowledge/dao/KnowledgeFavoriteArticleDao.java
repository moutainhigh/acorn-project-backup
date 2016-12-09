/*
 * @(#)KnowledgeFavoriteArticleDao.java 1.0 2013-12-3下午2:12:56
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-3 下午2:12:56
 * 
 */
public interface KnowledgeFavoriteArticleDao extends
		GenericDao<KnowledgeFavoriteArticle, java.lang.Long> {
	/**
	 * 
	 * @Description: 根据articleId 和收藏者查询
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return KnowledgeFavoriteArticle
	 * @throws
	 */
	public KnowledgeFavoriteArticle getFavoriteArticle(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle);

	/**
	 * 
	 * @Description: 我的收藏前10
	 * @return
	 * @return List<KnowledgeFavoriteArticle>
	 * @throws
	 */

	public List<KnowledgeFavoriteArticle> myFavoriteArticles(String user,
			String deptype);

	/**
	 * 
	 * @Description: 收藏的文章top 10
	 * @return
	 * @return List<KnowledgeFavoriteArticle>
	 * @throws
	 */

	public List<Map<String, Object>> hotFavoriteArticles(String dtype);

	/**
	 * 
	 * @Description: 查出总量
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer queryCounts(KnowledgeFavoriteArticle knowledgeFavoriteArticle);

	/**
	 * 
	 * @Description: 分页查询
	 * @param knowledgeFavoriteArticle
	 * @param dataModel
	 * @return
	 * @return List<KnowledgeArticle>
	 * @throws
	 */
	public List<KnowledgeFavoriteArticle> query(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle,
			DataGridModel dataModel);

}
