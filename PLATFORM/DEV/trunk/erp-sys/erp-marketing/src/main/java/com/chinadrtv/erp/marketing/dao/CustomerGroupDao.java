package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CustomerGroupDao extends
		GenericDao<CustomerGroup, java.lang.String> {

	public List<CustomerGroup> query(CustomerGroupDto customerGroup,
			DataGridModel dataModel);

	public Integer queryCount(CustomerGroupDto customerGroup);

	public Long getSeqNextValue();

	public List<CustomerGroup> queryList();
}
