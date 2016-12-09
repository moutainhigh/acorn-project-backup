/*
 * @(#)LuceneSearchServiceImpl.java 1.0 2013-12-26下午4:16:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.knowledge.service.LuceneSearchService;
import com.chinadrtv.erp.knowledge.util.DataModel;
import com.chinadrtv.erp.knowledge.util.LuceneSeach;
import com.chinadrtv.erp.knowledge.util.PropertiesUtils;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-26 下午4:16:30
 * 
 */
@Service("luceneSearchService")
public class LuceneSearchServiceImpl implements LuceneSearchService {
	@Autowired
	private LuceneSeach luceneSeach;

	@Override
	public Map<String, Object> list(String filed, DataModel dataModel,

	String depType, String searchType) {
		// TODO Auto-generated method stub
		String fields[] = { "productName", "content", "shortPinyin",
				"prodcutCode", "title" };
		String path = PropertiesUtils.getValue("inbound.Indexpath");
		if (depType.toUpperCase().equals("out")) {
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
		return luceneSeach.search(fields, filed, path, dataModel, searchType);
	}

}
