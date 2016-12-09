package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.BaseConfig;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 基本参数配置表DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 基本参数配置表DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface BaseConfigDao extends GenericDao<BaseConfig, java.lang.Long> {
	public List<BaseConfig> query(String code);

	public List<BaseConfig> queryByValue(String value);
}
