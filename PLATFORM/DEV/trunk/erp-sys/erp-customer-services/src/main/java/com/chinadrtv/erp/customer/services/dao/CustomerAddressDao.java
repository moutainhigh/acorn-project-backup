package com.chinadrtv.erp.customer.services.dao;

import com.chinadrtv.erp.customer.services.dto.AddressApiDto;

/**
 * 
 * @author dengqianyong
 *
 */
public interface CustomerAddressDao {

	/**
	 * 
	 * @param provinceName
	 * @return
	 */
	AddressApiDto findAddressInfo(String provinceName);

    /**
     *
     * @param provinceName
     * @return
     */
    AddressApiDto findAddressInfo(String provinceName, String cityName, String countyName, String areaName);

}
