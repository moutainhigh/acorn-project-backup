/*
 * @(#)KnowledgeRelationshipService.java 1.0 2014-1-8下午3:03:54
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.model.KnowledgetTag;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午3:03:54
 * 
 */
public interface KnowledgeTagService {
	/**
	 * 
	 * @Description: 保存标签
	 * @param knowledgeRelationship
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean saves(KnowledgetTag knowledgeRelationship);

	/**
	 * 
	 * @Description: 查看标签
	 * @param knowledgeRelationship
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public KnowledgetTag view(Long id);

	/**
	 * 
	 * @Description: 标签分页查看
	 * @param knowledgeRelationship
	 * @param dataGridModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> query(KnowledgetTag knowledgetTag,
			DataGridModel dataGridModel);

	/***
	 * 
	 * @Description: 标签list
	 * @param knowledgetTag
	 * @return
	 * @return List<KnowledgetTag>
	 * @throws
	 */
	public List<KnowledgetTag> list(KnowledgetTag knowledgetTag);

	/**
	 * 
	 * @Description: 删除
	 * @param id
	 * @return void
	 * @throws
	 */
	public Map<String, Object> delete(String ids);

	/**
	 * 
	 * @Description: 查询KnowledgetTag
	 * @param ids
	 * @return
	 * @return List<KnowledgetTag>
	 * @throws
	 */
	public List<KnowledgetTag> getListById(String ids);

}
