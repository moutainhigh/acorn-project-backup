/*
 * @(#)KnowledgeRelationshipServiceImpl.java 1.0 2014-1-8下午3:04:28
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.dao.KnowledgeArticleDao;
import com.chinadrtv.erp.knowledge.dao.KnowledgeRelationshipDao;
import com.chinadrtv.erp.knowledge.dao.KnowledgeTagDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;
import com.chinadrtv.erp.knowledge.model.KnowledgetTag;
import com.chinadrtv.erp.knowledge.service.KnowledgeTagService;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午3:04:28
 * 
 */
@Service("knowledgeTagServiceImpl")
public class KnowledgeTagServiceImpl implements KnowledgeTagService {
	@Autowired
	private KnowledgeRelationshipDao knowledgeRelationshipDao;
	@Autowired
	private KnowledgeTagDao knowledgeTagDao;
	@Autowired
	private KnowledgeArticleDao knowledgeArticleDao;

	public Boolean saves(KnowledgetTag knowledgeRelationship) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {
			if (knowledgeRelationship.getId() != null) {
				knowledgeTagDao.merge(knowledgeRelationship);
			} else {
				knowledgeTagDao.saveOrUpdate(knowledgeRelationship);

			}
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public KnowledgetTag view(Long id) {
		// TODO Auto-generated method stub
		return knowledgeTagDao.get(id);
	}

	public Map<String, Object> query(KnowledgetTag knowledgeRelationship,
			DataGridModel dataGridModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<KnowledgetTag> list = knowledgeTagDao.query(knowledgeRelationship,
				dataGridModel);
		Integer total = knowledgeTagDao.queryCounts(knowledgeRelationship);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	public List<KnowledgetTag> list(KnowledgetTag knowledgetTag) {
		// TODO Auto-generated method stub
		return knowledgeTagDao.queryList(knowledgetTag);
	}

	public Map<String, Object> delete(String ids) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<KnowledgeRelationship> list = new ArrayList<KnowledgeRelationship>();
			KnowledgeArticle knowledgeArticle = null;
			String[] id = ids.split(",");
			// 批量删除 并删除掉 相关联的 知识点中的 信息
			for (int i = 0; i < id.length; i++) {
				KnowledgeRelationship kRelationship = new KnowledgeRelationship();
				kRelationship.setTagId(Long.valueOf(id[i]));
				list = knowledgeRelationshipDao.queryList(kRelationship);
				for (int j = 0; j < list.size(); j++) {
					knowledgeArticle = knowledgeArticleDao.get(list.get(j)
							.getArticleId());
					if (knowledgeArticle != null) {
						knowledgeArticle.setRelationshipIds(knowledgeArticle
								.getRelationshipIds().replace(id[i] + ",", ""));
						knowledgeArticle.setRelationshipIds(knowledgeArticle
								.getRelationshipIds().replace(id[i], ""));
						knowledgeArticleDao.saveOrUpdate(knowledgeArticle);
					}
				}
				knowledgeTagDao.remove(Long.valueOf(id[i]));
				knowledgeRelationshipDao.deleteKnowledgeRelationship(null,
						Long.valueOf(id[i]));
			}
			result.put("result", "删除成功");
		} catch (Exception e) {
			result.put("result", "删除失败");
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<KnowledgetTag> getListById(String ids) {
		// TODO Auto-generated method stub
		List<KnowledgetTag> list = new ArrayList<KnowledgetTag>();
		KnowledgetTag knowledgetTag = null;
		if (!StringUtil.isNullOrBank(ids)) {
			String[] id = ids.split(",");
			if (!StringUtil.isNullOrBank(ids)) {
				for (int i = 0; i < id.length; i++) {
					knowledgetTag = knowledgeTagDao.get(Long.valueOf(id[i]));
					list.add(knowledgetTag);
				}

			}
		}

		return list;
	}

}
