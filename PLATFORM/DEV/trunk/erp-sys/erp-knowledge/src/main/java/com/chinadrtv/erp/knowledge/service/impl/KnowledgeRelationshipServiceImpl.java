/*
 * @(#)KnowledgeRelationshipServiceImpl.java 1.0 2014-1-8下午3:04:28
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.knowledge.dao.KnowledgeRelationshipDao;
import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;
import com.chinadrtv.erp.knowledge.service.KnowledgeRelationshipService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午3:04:28
 * 
 */
@Service("knowledgeRelationshipService")
public class KnowledgeRelationshipServiceImpl implements
		KnowledgeRelationshipService {
	@Autowired
	private KnowledgeRelationshipDao knowledgeRelationshipDao;

	public Boolean saves(KnowledgeRelationship knowledgeRelationship) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {
			List<KnowledgeRelationship> list = knowledgeRelationshipDao
					.queryList(knowledgeRelationship);
			if (list == null || list.isEmpty()) {
				knowledgeRelationshipDao.saveOrUpdate(knowledgeRelationship);
			}
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public Boolean delete(Long articleId, Long tagId) {
		Boolean result = false;
		try {
			knowledgeRelationshipDao.deleteKnowledgeRelationship(articleId,
					tagId);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;

	}

	public KnowledgeRelationship view(Long id) {
		// TODO Auto-generated method stub
		return knowledgeRelationshipDao.get(id);
	}

	public List<KnowledgeArticleDto> query(String ids, Long articleId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<KnowledgeArticleDto> list = knowledgeRelationshipDao.query(ids,
				articleId);
		return list;
	}

	@Override
	public Boolean delete(String ids) {
		// TODO Auto-generated method stub
		String[] id = ids.split(",");

		return null;
	}

}
