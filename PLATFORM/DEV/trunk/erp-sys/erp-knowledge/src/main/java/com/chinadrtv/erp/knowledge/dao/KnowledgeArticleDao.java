/*
 * @(#)KnowledgeArticle.java 1.0 2013-11-6下午1:37:51
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-6 下午1:37:51
 * 
 */
public interface KnowledgeArticleDao extends
		GenericDao<KnowledgeArticle, java.lang.Long> {
	/**
	 * 
	 * @Description: 根据产品编码和部门类型获得知识点信息
	 * @param knowledgeArticle
	 * @return
	 * @return KnowledgeArticle
	 * @throws
	 */
	public KnowledgeArticle getByProductCode(KnowledgeArticle knowledgeArticle);

	public Long getSeq();

	public Integer queryCounts(KnowledgeArticle knowledgeArticle);

	public List<KnowledgeArticle> query(KnowledgeArticle knowledgeArticle,
			DataGridModel dataModel);

	public List<KnowledgeArticle> queryList(Long cid, String depType);

	public Boolean isExist(Long cid);

	public List<KnowledgeArticle> getNewAdds(Long nums, String dtype);

	public KnowledgeArticle getByarticleId(String dept, Long articled);

	public List<KnowledgeArticle> getAKnowledgeArticles(String dept,
			String searchType);

}
