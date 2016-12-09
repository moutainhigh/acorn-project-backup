/*
 * @(#)KnowledgeFavoriteArticleService.java 1.0 2013-12-3下午2:17:20
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-3 下午2:17:20
 * 
 */
public interface KnowledgeFavoriteArticleService {
	/**
	 * 
	 * @Description: 添加收藏
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Map<String, Object> saveMyFavoriteArticle(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle);

	/***
	 * 
	 * @Description: 取消收藏
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> removeFavorite(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle);

	/**
	 * 
	 * @Description: 我收藏的文章
	 * @param user
	 * @return
	 * @return List<KnowledgeFavoriteArticle>
	 * @throws
	 */
	public Map<String, Object> MyFavoriteArticles(String user, String deptype);

	/**
	 * 
	 * @Description: 收藏文章top10
	 * @param dptype
	 * @return
	 * @return List<KnowledgeFavoriteArticle>
	 * @throws
	 */
	public Map<String, Object> hotFavoriteArticles(String dptype, String user);

	/**
	 * 
	 * @Description: 知识点分页
	 * @param knowledgeArticle
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	Map<String, Object> query(KnowledgeFavoriteArticle knowledgeArticle,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description:
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return KnowledgeFavoriteArticle
	 * @throws
	 */
	public KnowledgeFavoriteArticle getByArticleId(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle);

}
