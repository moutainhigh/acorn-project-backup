package com.chinadrtv.erp.marketing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.BaseConfigDao;
import com.chinadrtv.erp.marketing.service.BaseConfigService;
import com.chinadrtv.erp.model.marketing.BaseConfig;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-sql数据源-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-sql数据源-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("baseConfigService")
public class BaseConfigServiceImpl implements BaseConfigService {

	@Autowired
	private BaseConfigDao baseConfigDao;

	/*
	 * (非 Javadoc) <p>Title: query</p> <p>Description: </p>
	 * 
	 * @param code
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.BaseConfigService#query(java.lang
	 * .String)
	 */
	public List<BaseConfig> query(String code) {
		// TODO Auto-generated method stub
		return baseConfigDao.query(code);
	}

	/*
	 * (非 Javadoc) <p>Title: queryByValue</p> <p>Description: </p>
	 * 
	 * @param value
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.BaseConfigService#queryByValue(java
	 * .lang.String)
	 */
	public List<BaseConfig> queryByValue(String value) {
		// TODO Auto-generated method stub
		return baseConfigDao.queryByValue(value);
	}

}
