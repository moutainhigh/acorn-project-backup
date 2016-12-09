package com.chinadrtv.erp.customer.services.service;

import com.chinadrtv.erp.customer.services.dto.PhonesAndAddressDto;

import java.util.Map;

/**
 * 
 * @author xieguoqiang
 *
 */
public interface PhonesAndAddressService {
	/**
	 *
	 * @param phones
	 * @param addressDto
	 * @return
	 */
    Map savePhonesAndAddress(PhonesAndAddressDto phonesAndAddressDto);


}
