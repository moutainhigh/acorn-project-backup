package com.chinadrtv.erp.customer.services.service;

import java.util.List;

import com.chinadrtv.erp.customer.services.dto.CustomerRequest;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;

/**
 * 
 * @author dengqianyong
 *
 */
public interface CustomerApiService {

	/**
	 * 
	 * @param req
	 * @return
	 */
	List<CustomerDto> findCustomers(CustomerRequest req);

	/**
	 * 
	 * @param customer
	 * @param addresses
	 * @param phones
	 * @return
	 * @throws ServiceTransactionException
	 */
	String saveCustomer(CustomerDto customer, List<AddressDto> addresses,
			List<Phone> phones) throws ServiceTransactionException;

	/**
	 * 
	 * @param province
	 * @return
	 */
	AddressDto createAddress(String province);

}
