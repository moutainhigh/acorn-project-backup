/*
 * @(#)LuceneIndexServiceImpl.java 1.0 2013-12-24下午1:25:56
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.knowledge.dao.KnowledgeArticleDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.service.LuceneIndexService;
import com.chinadrtv.erp.knowledge.util.LuceneIndex;
import com.chinadrtv.erp.knowledge.util.PropertiesUtils;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-24 下午1:25:56
 * 
 */
@Service("luceneIndexService")
public class LuceneIndexServiceImpl implements LuceneIndexService {
	private static final Logger logger = LoggerFactory
			.getLogger(LuceneIndexServiceImpl.class);

	@Autowired
	private LuceneIndex luceneIndex;
	@Autowired
	private KnowledgeArticleDao knowledgeArticleDao;

	@Override
	public Boolean index(String depType, String searchType) {
		// TODO Auto-generated method stub
		Boolean result = true;
		String path = PropertiesUtils.getValue("inbound.Indexpath");
		if (depType.toUpperCase().equals("OUT")) {
			if (searchType.equals("1")) {
				path = PropertiesUtils.getValue("outboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("outboundD.Indexpath");
			}
		} else {
			if (searchType.equals("1")) {
				path = PropertiesUtils.getValue("inboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("inboundD.Indexpath");
			}
		}
		List<KnowledgeArticle> list = knowledgeArticleDao
				.getAKnowledgeArticles(depType, searchType);
		try {
			luceneIndex.createIndex(list, path);
			logger.info("批量创建索引成功 depType" + depType);
		} catch (Exception e) {
			logger.error("批量创建索引失败 depType" + depType + "===" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean singleIndex(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub
		Boolean result = true;
		String path = "";
		if (knowledgeArticle.getDepartType().equals("OUT")) {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("outboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("outboundD.Indexpath");
			}
		} else {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("inboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("inboundD.Indexpath");
			}
		}
		try {
			luceneIndex.update(knowledgeArticle, path);
			logger.info("单条创建索引成功 depType" + path);
		} catch (Exception e) {
			result = false;
			logger.error("单条创建索引失败" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public Boolean updateIndex(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub
		Boolean result = true;
		String path = "";
		if (knowledgeArticle.getDepartType().equals("OUT")) {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("outboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("outboundD.Indexpath");
			}
		} else {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("inboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("inboundD.Indexpath");
			}
		}
		try {
			luceneIndex.update(knowledgeArticle, path);
			logger.info("单条更新索引成功 depType" + path);
		} catch (Exception e) {
			result = false;
			logger.error("单条更新索引失败" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public Boolean deleteIndex(KnowledgeArticle knowledgeArticle) {
		// TODO Auto-generated method stub
		Boolean result = true;
		String path = "";
		if (knowledgeArticle.getDepartType().equals("OUT")) {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("outboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("outboundD.Indexpath");
			}
		} else {
			if (knowledgeArticle.getType().equals("1")) {
				path = PropertiesUtils.getValue("inboundP.Indexpath");
			} else {
				path = PropertiesUtils.getValue("inboundD.Indexpath");
			}
		}

		try {
			luceneIndex.delete(knowledgeArticle.getId(), path);
			logger.info("单条删除索引成功 depType" + path);
		} catch (Exception e) {
			result = false;
			logger.error("单条删除索引失败" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}

}
