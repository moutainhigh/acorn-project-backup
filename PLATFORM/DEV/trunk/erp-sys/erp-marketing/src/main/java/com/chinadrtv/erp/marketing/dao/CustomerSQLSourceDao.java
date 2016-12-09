package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-sql数据源DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-sql数据源DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CustomerSQLSourceDao extends
		GenericDao<CustomerSqlSource, java.lang.Long> {

	public List<CustomerSqlSource> query(CustomerSqlSource sqlSource,
			DataGridModel dataModel);

	public Integer queryCount(CustomerSqlSource sqlSource);

	public Long querySQLCount(String sql);
}
