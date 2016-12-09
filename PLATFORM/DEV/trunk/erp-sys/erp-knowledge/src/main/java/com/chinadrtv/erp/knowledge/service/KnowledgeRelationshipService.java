/*
 * @(#)KnowledgeRelationshipService.java 1.0 2014-1-8下午3:03:54
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import java.util.List;

import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午3:03:54
 * 
 */
public interface KnowledgeRelationshipService {
	/**
	 * 
	 * @Description: 保存标签
	 * @param knowledgeRelationship
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean saves(KnowledgeRelationship knowledgeRelationship);

	/**
	 * 
	 * @Description: 查看标签
	 * @param knowledgeRelationship
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public KnowledgeRelationship view(Long id);

	/**
	 * 
	 * @Description: 标签分页查看
	 * @param knowledgeRelationship
	 * @param dataGridModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	List<KnowledgeArticleDto> query(String ids, Long articleId);

	/**
	 * 
	 * @Description: 删除关联知识点
	 * @param id
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean delete(Long articleId, Long tagId);

	/**
	 * 
	 * @Description: 批量删除
	 * @param ids
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean delete(String ids);

}
