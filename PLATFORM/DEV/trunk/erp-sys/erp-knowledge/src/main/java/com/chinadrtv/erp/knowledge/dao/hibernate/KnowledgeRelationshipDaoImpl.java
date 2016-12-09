/*
 * @(#)KnowledgeRelationshipDaoImpl.java 1.0 2014-1-8下午2:24:08
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.knowledge.dao.KnowledgeRelationshipDao;
import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeRelationship;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-8 下午2:24:08
 * 
 */
@Repository
public class KnowledgeRelationshipDaoImpl extends
		GenericDaoHibernate<KnowledgeRelationship, java.lang.Long> implements
		KnowledgeRelationshipDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public KnowledgeRelationshipDaoImpl() {
		super(KnowledgeRelationship.class);
	}

	@Override
	public Integer queryCounts(KnowledgeRelationship knowledgeRelationship) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(*) from  KnowledgeRelationship k , KnowledgeArticle kl  where k1.status ='1' and k.articleId = k1.id and k.tagId= "
						+ knowledgeRelationship.getTagId());
		Map<String, Parameter> params = gensql(sql, knowledgeRelationship);
		Integer result = findPageCount(sql.toString(), params);
		return result;
	}

	@Override
	public List<KnowledgeArticleDto> query(String ids, Long articleId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select k2.title,k2.product_Name,k2.id,k2.status,k2.type  from ACOAPP_MARKETING.KNOWLEDGE_RELATIONSHIP  k  , ACOAPP_MARKETING.KNOWLEDGE_ARTICLE k2  where k2.status ='1' and k.article_Id = k2.id and k.tag_Id in ("
						+ ids + " )");
		sql.append(" and rownum <100  and k2.id != " + articleId);

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql
				.toString());
		KnowledgeArticleDto knowDto = null;
		List<KnowledgeArticleDto> listDtos = new ArrayList<KnowledgeArticleDto>();
		for (int i = 0; i < list.size(); i++) {
			knowDto = new KnowledgeArticleDto();
			if (list.get(i).get("id") != null) {
				knowDto.setId(Long.valueOf(list.get(i).get("id").toString()));
			}
			if (list.get(i).get("type") != null) {
				if (list.get(i).get("type").equals("1")) {
					knowDto.setTitle(list.get(i).get("product_Name").toString());
				} else {
					knowDto.setTitle(list.get(i).get("title").toString());
				}
			}
			listDtos.add(knowDto);
		}

		return listDtos;
	}

	public Map<String, Parameter> gensql(StringBuffer sql,
			KnowledgeRelationship knowledgeRelationships) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		return paras;

	}

	@Override
	public List<KnowledgeRelationship> queryList(
			KnowledgeRelationship knowledgeRelationship) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"from KnowledgeRelationship k where k.status = '1' ");
		if (knowledgeRelationship.getArticleId() != null) {
			sql.append(" and k.articleId = "
					+ knowledgeRelationship.getArticleId() + "");
		}
		if (knowledgeRelationship.getTagId() != null) {
			sql.append(" and k.tagId = " + knowledgeRelationship.getTagId()
					+ "");
		}

		return getHibernateTemplate().find(sql.toString());
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean deleteKnowledgeRelationship(Long artcileId, Long tagId) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {

			StringBuffer sql = new StringBuffer(
					"delete ACOAPP_MARKETING.KNOWLEDGE_RELATIONSHIP where 1=1   ");

			if (artcileId != null) {
				sql.append("and ARTICLE_ID = " + artcileId + "");
			}
			if (tagId != null) {
				sql.append("and TAG_ID = " + tagId + "");

			}
			jdbcTemplate.execute(sql.toString());
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return result;
	}

}
