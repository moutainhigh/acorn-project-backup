package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Ems;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface AddressExtService extends GenericService<AddressExt,String> {
    AddressExt findMatchAddressFromNames(String provinceName,String cityName,String countyName,String areaName,String address, String strContactId);
    AddressExt findAddressFromNames(String provinceName,String cityName,String countyName,String areaName,String address);
    void createAddressExt(AddressExt addressExt, Ems ems);
    AddressExt getAddressExt(String addressId);

    void createAddressExt(AddressExt addressExt, Address address);

}
