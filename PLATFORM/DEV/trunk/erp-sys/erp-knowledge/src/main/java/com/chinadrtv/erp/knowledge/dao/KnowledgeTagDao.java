/*
 * @(#)KnowledgeTagDao.java 1.0 2014-1-10下午1:55:25
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.model.KnowledgetTag;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-10 下午1:55:25
 * 
 */
public interface KnowledgeTagDao extends
		GenericDao<KnowledgetTag, java.lang.Long> {

	public Integer queryCounts(KnowledgetTag KnowledgetTag);

	public List<KnowledgetTag> query(KnowledgetTag KnowledgetTag,
			DataGridModel dataModel);

	public List<KnowledgetTag> queryList(KnowledgetTag KnowledgetTag);

}
