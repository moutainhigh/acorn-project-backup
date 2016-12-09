/*
 * @(#)KnowledgeRelationshipDao.java 1.0 2014-1-8下午2:22:34
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午2:22:34
 * 
 */
public interface KnowledgeRelationshipDao extends
		GenericDao<KnowledgeRelationship, java.lang.Long> {

	public Integer queryCounts(KnowledgeRelationship knowledgeRelationship);

	public List<KnowledgeArticleDto> query(String ids, Long articleId);

	/**
	 * 
	 * @Description: 查出文章相关联的标签
	 * @param knowledgeRelationship
	 * @return
	 * @return List<KnowledgeRelationship>
	 * @throws
	 */
	public List<KnowledgeRelationship> queryList(
			KnowledgeRelationship knowledgeRelationship);

	public Boolean deleteKnowledgeRelationship(Long artcileId, Long tagId);

}
