package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.Address;

/**
 * 地址服务
 * User: gdj
 * Date: 13-4-22
 * Time: 下午4:23
 * To change this template use File | Settings | File Templates.
 */
public interface AddressService extends GenericService<Address, String> {
    Address getAddress(String addressId);
}
