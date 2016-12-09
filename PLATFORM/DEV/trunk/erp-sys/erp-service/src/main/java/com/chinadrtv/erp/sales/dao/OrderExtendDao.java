package com.chinadrtv.erp.sales.dao;

import java.util.List;

public interface OrderExtendDao {

	/**
	 * 
	 * @param contactId
	 * @return
	 */
	List<String> listOrderIds(String contactId);
}
