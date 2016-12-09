/*
 * @(#)LuceneIndexService.java 1.0 2013-12-23下午2:47:35
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-23 下午2:47:35
 * 
 */
public interface LuceneIndexService {
	/**
	 * 
	 * @Description: 根据部门类型批量创建索引
	 * @param depType
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean index(String depType, String searchType);

	/**
	 * 
	 * @Description: 新增单个索引
	 * @param knowledgeArticleService
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean singleIndex(KnowledgeArticle knowledgeArticle);

	/**
	 * 
	 * @Description: 更新索引
	 * @param knowledgeArticleService
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean updateIndex(KnowledgeArticle knowledgeArticle);

	/***
	 * 
	 * @Description: 删除索引
	 * @param knowledgeArticle
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean deleteIndex(KnowledgeArticle knowledgeArticle);
}
