/*
 * @(#)KnowledgeArticleService.java 1.0 2013-11-6下午1:55:13
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-6 下午1:55:13
 * 
 */
public interface KnowledgeArticleService {
	/**
	 * 
	 * @Description: 保存知识点
	 * @param knowledgeArticle
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Map<String, Object> saveKnowledgeArticle(
			KnowledgeArticle knowledgeArticle, HttpServletRequest request,
			String groupType, String userId);

	/**
	 * 
	 * @Description: 逻辑删除知识点
	 * @param knowledgeArticle
	 * @return
	 * @return KnowledgeArticle
	 * @throws
	 */
	public Boolean deleteKnowledgeArticle(KnowledgeArticle knowledgeArticle);

	/**
	 * 
	 * @Description: 根据产品编码和部门类型获得知识点信息
	 * @param knowledgeArticle
	 * @return
	 * @return KnowledgeArticle
	 * @throws
	 */
	public KnowledgeArticle getByProductCode(KnowledgeArticle knowledgeArticle);

	/**
	 * 根据id 查询
	 * 
	 * @Description: TODO
	 * @param Id
	 * @return
	 * @return KnowledgeArticle
	 * @throws
	 */
	public KnowledgeArticle getById(Long Id);

	/**
	 * 
	 * @Description:目录树
	 * @param cid
	 * @param depType
	 * @return
	 * @return List<KnowledgeArticle>
	 * @throws
	 */
	public List<KnowledgeArticle> getTrees(Long cid, String depType);

	/**
	 * 
	 * @Description: 知识点分页
	 * @param knowledgeArticle
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	Map<String, Object> query(KnowledgeArticle knowledgeArticle,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description: 目录下是否有有效知识点
	 * @param cid
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean isExist(Long cid);

	/**
	 * 
	 * @Description: 查看最新热门
	 * @param nums
	 * @return
	 * @return List<KnowledgeArticle>
	 * @throws
	 */
	public List<KnowledgeArticle> getNewAdds(Long nums, String dtype);
}
