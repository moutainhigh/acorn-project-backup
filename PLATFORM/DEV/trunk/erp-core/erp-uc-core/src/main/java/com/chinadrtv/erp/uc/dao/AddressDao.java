/*
 * @(#)AddressDao.java 1.0 2013-2-19下午4:01:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Address;


/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-2-19 下午4:01:09 
 * 
 */
public interface AddressDao extends GenericDao<Address, String>{

	/**
	 * <p>获取sequence</p>
	 * @return java.lang.String
	 */
	public String getSequence();
	
	
	/**
	 * <p>获取非主地址的contact sequence</p>
	 * @return java.lang.String
	 */
	public String getContactSecondarySeq();

	/**
	 * <p>查询客户主地址</p>
	 * @param contactId
	 * @return Address
	 */
	public Address getContactMainAddress(String contactId);


	/**
	 * <p>获取联系人的Address</p>
	 * @param contactId
	 * @return
	 */
	public Address getAddressByContactId(String contactId);


	/**
	 * <p></p>
	 * @param currMainAddress
	 */
	public void updateAddress(Address currMainAddress);

    /**
     * 地址加入黑名单
     * @param addressId
     */
//    void addToBlackList(String addressId);

    /**
     * 退出黑名单
     * @param addressId
     */
//    void removeFromBlackList(String addressId);
}
