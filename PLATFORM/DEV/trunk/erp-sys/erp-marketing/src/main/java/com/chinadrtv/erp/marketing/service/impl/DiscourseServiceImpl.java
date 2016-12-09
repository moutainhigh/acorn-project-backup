/*
 * @(#)DiscourseServiceImpl.java 1.0 2013-4-8上午10:42:51
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.DiscourseDao;
import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.marketing.service.DiscourseService;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-8 上午10:42:51
 * 
 */
@Service
public class DiscourseServiceImpl implements DiscourseService {
	@Autowired
	private DiscourseDao discourseDao;

	/*
	 * (非 Javadoc) <p>Title: getDiscourseList</p> <p>Description: </p>
	 * 
	 * @param discourseDto
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#getDiscourseList
	 * (com.chinadrtv.erp.marketing.dto.DiscourseDto,
	 * com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> getDiscourseList(DiscourseDto discourseDto,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<DiscourseDto> list = discourseDao.query(discourseDto, dataModel);
		int count = discourseDao.queryCounts(discourseDto);
		result.put("total", count);
		result.put("rows", list);
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: svaeDiscourse</p> <p>Description: </p>
	 * 
	 * @param discourse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#svaeDiscourse(com
	 * .chinadrtv.erp.marketing.model.Discourse)
	 */
	public String svaeDiscourse(Discourse discourse) {
		// TODO Auto-generated method stub
		String temps = "";
		try {
			temps = discourseDao.saveDiscourse(discourse);
			return temps;
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	}

	/*
	 * 
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#getDiscourseById
	 * (java.lang.String)
	 */
	public Discourse getDiscourseById(Long id) {
		// TODO Auto-generated method stub
		if (id != null) {
			return discourseDao.getById(id);
		} else {
			return null;
		}
	}

	/*
	 * (非 Javadoc) <p>Title: updateDiscourse</p> <p>Description: </p>
	 * 
	 * @param discourse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#updateDiscourse(
	 * com.chinadrtv.erp.marketing.model.Discourse)
	 */
	public Boolean updateDiscourse(Discourse discourse) {
		// TODO Auto-generated method stub
		return discourseDao.update(discourse);
	}

	/*
	 * 
	 * 
	 * @param product
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#queryByProductCode
	 * (java.lang.String)
	 */
	public Map<String, Object> queryByProductCode(String product,
			String department) {
		// TODO Auto-generated method stub
		List<Discourse> list = discourseDao.queryByProductCode(product,
				department);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> value = new HashMap<String, Object>();
		if (list != null && !list.isEmpty()) {
			value.put("url", list.get(0).getDiscourseHtmlUrl());
			result.put("value", value);
			result.put("desc", "");
			result.put("code", "000");
			result.put("resultdt", list);
		} else {
			result.put("code", "100");
			result.put("desc", "没有找到相应的话术");
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: deleteDiscourse</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#deleteDiscourse(
	 * java.lang.Long)
	 */
	public void deleteDiscourse(String id) {
		// TODO Auto-generated method stub
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Discourse discourse = discourseDao.getById(Long.valueOf(ids[i]));
			discourse.setStatus("1");
			discourseDao.update(discourse);
		}
	}

	/*
	 * (非 Javadoc) <p>Title: queryByProduct</p> <p>Description: </p>
	 * 
	 * @param product
	 * 
	 * @param department
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.DiscourseService#queryByProduct(java
	 * .lang.String, java.lang.String)
	 */
	public List<Discourse> queryByProduct(String product, String department) {
		// TODO Auto-generated method stub
		return discourseDao.queryByProductCode(product, department);
	}

}
