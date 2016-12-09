/*
 * @(#)KnowledgeTagDaoImpl.java 1.0 2014-1-10下午1:56:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.knowledge.dao.KnowledgeTagDao;
import com.chinadrtv.erp.knowledge.model.KnowledgetTag;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-10 下午1:56:19
 * 
 */
@Repository
public class KnowledgeTagDaoImpl extends
		GenericDaoHibernate<KnowledgetTag, java.lang.Long> implements
		KnowledgeTagDao {
	public KnowledgeTagDaoImpl() {
		super(KnowledgetTag.class);
	}

	public Integer queryCounts(KnowledgetTag knowledgeTag) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(*) from  KnowledgetTag k where k.status = '1'  ");
		Map<String, Parameter> params = gensql(sql, knowledgeTag);
		Integer result = findPageCount(sql.toString(), params);
		return result;
	}

	public List<KnowledgetTag> query(KnowledgetTag knowledgeTag,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"from KnowledgetTag  k where k.status ='1'");
		Map<String, Parameter> params = gensql(sql, knowledgeTag);
		sql.append("order by k.id");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);

		List<KnowledgetTag> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Map<String, Parameter> gensql(StringBuffer sql,
			KnowledgetTag knowledgeRelationships) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(knowledgeRelationships.getName())) {
			sql.append(" AND k.name=:name");
			Parameter name = new ParameterString("name",
					knowledgeRelationships.getName());
			paras.put("name", name);
		}
		return paras;

	}

	public List<KnowledgetTag> queryList(KnowledgetTag KnowledgetTag) {
		// TODO Auto-generated method stub
		String sql = "from KnowledgetTag k where k.status ='1' ";
		Session session = getSession();
		Query query = session.createQuery(sql);

		return query.list();

	}

}
