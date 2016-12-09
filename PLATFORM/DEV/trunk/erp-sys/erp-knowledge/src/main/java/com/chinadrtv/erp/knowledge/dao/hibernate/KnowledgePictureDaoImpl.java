/*
 * @(#)KnowledgePictureDaoImpl.java 1.0 2013-11-8下午2:20:28
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.knowledge.dao.KnowledgePictureDao;
import com.chinadrtv.erp.knowledge.model.KnowledgePicture;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-8 下午2:20:28
 * 
 */
@Repository
public class KnowledgePictureDaoImpl extends
		GenericDaoHibernate<KnowledgePicture, java.lang.Long> implements
		KnowledgePictureDao {

	public KnowledgePictureDaoImpl() {
		super(KnowledgePicture.class);
	}

}
