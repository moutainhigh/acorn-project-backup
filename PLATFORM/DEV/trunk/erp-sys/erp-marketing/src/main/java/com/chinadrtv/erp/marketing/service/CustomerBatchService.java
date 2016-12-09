package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.marketing.CustomerBatchDetail;
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
public interface CustomerBatchService {
	public Map<String, Object> queryBatch(String groupCode,
			DataGridModel dataModel);

	public void genBatchData(String groupCode);

	public String genBatchNum();

	public List<CustomerBatchDetail> querBatchDetailsBygroupCode(
			String groupCode);

	public Integer getCounts(String groupCode, String flag);

	public Map<String, Object> showData(String groupCode);
}
