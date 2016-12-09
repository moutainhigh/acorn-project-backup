package com.chinadrtv.erp.marketing.service;

import java.util.List;

import com.chinadrtv.erp.model.marketing.BaseConfig;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 基本参数配置表-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 基本参数配置表-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface BaseConfigService {
	public List<BaseConfig> query(String code);

	public List<BaseConfig> queryByValue(String value);

}
