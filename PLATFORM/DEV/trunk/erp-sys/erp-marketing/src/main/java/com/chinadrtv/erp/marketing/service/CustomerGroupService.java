package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface CustomerGroupService {
	public CustomerGroup getCustomerGroupById(String groupCode);

	public void saveCustomerGroup(CustomerGroup customerGroup, String user,
			JobCronSet jobCronSet, CustomerSqlSource sqlSource);

	public void removeCustomerGroup(String groupId);

	public Map<String, Object> query(CustomerGroupDto customerGroup,
			DataGridModel dataModel);

	public String getNextVal();

	public List<CustomerGroup> queryList();

	public Long queryCount(String[] groupCode);

}
