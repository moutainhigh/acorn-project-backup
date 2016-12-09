package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

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
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface CustomerSqlSourceService {
	public CustomerSqlSource getCustomerSqlSourceById(Long id);

	public Long saveCustomerSqlSource(CustomerSqlSource sqlSource,
			String groupCode, String user);

	public void removeCustomerSqlSource(Long id);

	public Map<String, Object> querySqlHis(String groupCode,
			DataGridModel dataModel);

	public Long querySQLCount(String sql);
}
