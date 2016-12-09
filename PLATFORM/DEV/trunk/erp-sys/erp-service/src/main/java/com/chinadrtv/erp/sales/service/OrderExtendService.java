package com.chinadrtv.erp.sales.service;

import java.util.List;

public interface OrderExtendService {

	/**
	 * 
	 * @param contactId
	 * @return
	 */
	List<String> listOrderIds(String contactId);
}
