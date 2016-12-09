/*
 * @(#)KnowledgeFavoriteArticleServiceImpl.java 1.0 2013-12-3下午2:18:59
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.dao.KnowledgeArticleDao;
import com.chinadrtv.erp.knowledge.dao.KnowledgeFavoriteArticleDao;
import com.chinadrtv.erp.knowledge.dto.HotFavoriteDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;
import com.chinadrtv.erp.knowledge.service.KnowledgeFavoriteArticleService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-3 下午2:18:59
 * 
 */
@Service("knowledgeFavoriteArticleService")
public class KnowledgeFavoriteArticleServiceImpl implements
		KnowledgeFavoriteArticleService {
	@Autowired
	private KnowledgeFavoriteArticleDao knowledgeFavoriteArticleDao;
	@Autowired
	private KnowledgeArticleDao knowledgeArticleDao;
	private static final Logger logger = LoggerFactory
			.getLogger(KnowledgeFavoriteArticleServiceImpl.class);

	@Override
	public Map<String, Object> saveMyFavoriteArticle(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			KnowledgeFavoriteArticle knowledgeFavoriteArticle2 = knowledgeFavoriteArticleDao
					.getFavoriteArticle(knowledgeFavoriteArticle);

			if (knowledgeFavoriteArticle2 != null) {
				result.put("result", 2);
			} else {
				knowledgeFavoriteArticleDao
						.saveOrUpdate(knowledgeFavoriteArticle);
				result.put("result", 1);
			}
			return result;
		} catch (Exception e) {
			result.put("result", 0);
			logger.error("添加收藏失败" + e.getMessage());
			e.printStackTrace();
			return result;
			// TODO: handle exception
		}
	}

	@Override
	public Map<String, Object> removeFavorite(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			KnowledgeFavoriteArticle knowledgeFavoriteArticle2 = knowledgeFavoriteArticleDao
					.getFavoriteArticle(knowledgeFavoriteArticle);
			knowledgeFavoriteArticle2.setStatus("2");
			knowledgeFavoriteArticleDao.merge(knowledgeFavoriteArticle2);
			result.put("result", "1");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("取消添加失败");
			result.put("result", "2");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> MyFavoriteArticles(String user, String deptype) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows",
				knowledgeFavoriteArticleDao.myFavoriteArticles(user, deptype));
		result.put("total", "10");
		return result;
	}

	@Override
	public Map<String, Object> hotFavoriteArticles(String dptype, String user) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = knowledgeFavoriteArticleDao
				.hotFavoriteArticles(dptype);
		List<HotFavoriteDto> listHot = new ArrayList<HotFavoriteDto>();
		Long times = 0l;
		Long articleId = 0l;
		Long categoryId = 0l;
		KnowledgeFavoriteArticle knowledgeFavoriteArticle = new KnowledgeFavoriteArticle();
		knowledgeFavoriteArticle.setDeptType(dptype);
		knowledgeFavoriteArticle.setCreateUser(user);
		for (int i = 0; i < list.size(); i++) {
			articleId = Long.valueOf(list.get(i).get("article_id").toString());
			knowledgeFavoriteArticle.setArticleId(articleId);
			KnowledgeFavoriteArticle knowledgeFavoriteArticle2 = knowledgeFavoriteArticleDao
					.getFavoriteArticle(knowledgeFavoriteArticle);
			times = Long.valueOf(list.get(i).get("times").toString());
			categoryId = Long
					.valueOf(list.get(i).get("category_id").toString());
			HotFavoriteDto hDto = new HotFavoriteDto();
			KnowledgeArticle knowledgeArticle = knowledgeArticleDao
					.getByarticleId(dptype, articleId);
			hDto.setCategoryId(categoryId);
			hDto.setArticleId(articleId);
			hDto.setTimes(times);
			hDto.setType(knowledgeArticle.getType());
			hDto.setCategoryName(knowledgeArticle.getKnowledgeCategory()
					.getName());
			if (knowledgeFavoriteArticle2 != null) {
				hDto.setStatus(knowledgeFavoriteArticle2.getStatus());
			} else {
				hDto.setStatus("2");
			}
			if (knowledgeArticle.getType().equals("1")) {
				hDto.setTitle(knowledgeArticle.getProductName());
			} else {
				hDto.setTitle(knowledgeArticle.getTitle());
			}
			listHot.add(hDto);
		}

		result.put("rows", listHot);
		result.put("total", "10");
		return result;
	}

	@Override
	public Map<String, Object> query(KnowledgeFavoriteArticle knowledgeArticle,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<KnowledgeFavoriteArticle> list = knowledgeFavoriteArticleDao
				.query(knowledgeArticle, dataModel);
		Integer total = knowledgeFavoriteArticleDao
				.queryCounts(knowledgeArticle);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public KnowledgeFavoriteArticle getByArticleId(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle) {
		// TODO Auto-generated method stub
		return knowledgeFavoriteArticleDao
				.getFavoriteArticle(knowledgeFavoriteArticle);
	}
}
