/**
 * 
 */
package com.chinadrtv.erp.customer.services.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.customer.services.dao.CustomerAddressDao;
import com.chinadrtv.erp.customer.services.dto.AddressApiDto;
import com.chinadrtv.erp.customer.services.dto.CustomerRequest;
import com.chinadrtv.erp.customer.services.service.CustomerApiService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.ContactService;

/**
 * @author dengqianyong
 *
 */
@Service
public class CustomerApiServiceImpl implements CustomerApiService {

	@Resource
	private ContactService contactService;
	
	@Resource
	private CustomerAddressDao customerAddressDao;
	
	private static final String ADDRESS_DESC = "大都会赠险，无需地址";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> findCustomers(CustomerRequest req) {
		CustomerBaseSearchDto query = new CustomerBaseSearchDto();
		query.setName(req.getName());
		query.setPhone(req.getPhone());
		DataGridModel gridModel = new DataGridModel();
		gridModel.setStartRow(0);
		gridModel.setRows(Integer.MAX_VALUE);
		Map<String, Object> contacts = contactService.findByBaseCondition(query, gridModel);
		return (List<CustomerDto>) contacts.get("rows");
	}
	
	@Override
	public String saveCustomer(CustomerDto customer,
			List<AddressDto> addresses, List<Phone> phones) throws ServiceTransactionException {
		Map<String, Object> map = contactService.saveCustomer(customer,
				addresses, phones);
		return (String) map.get("contactId");
	}

	@Override
	public AddressDto createAddress(String province) {
		AddressDto dto = new AddressDto();
		
		AddressApiDto apiDto = customerAddressDao.findAddressInfo(province);
		if (apiDto != null) {
			dto.setState(apiDto.getProvinceId().toString());
			dto.setProvinceName(apiDto.getProvinceName());
			dto.setCityId(apiDto.getCityId());
			dto.setCityName(apiDto.getCityName());
			dto.setCountyId(apiDto.getCountyId());
			dto.setCountyName(apiDto.getCountyName());
			dto.setAreaid(apiDto.getAreaId());
			dto.setAreaName(apiDto.getAreaName());
			dto.setCountySpellid(apiDto.getSpellId());
			dto.setZip(apiDto.getZip());
			dto.setStatus(apiDto.getAuditState().toString());
			dto.setAddressDesc(ADDRESS_DESC);
			dto.setAddress(ADDRESS_DESC);
			dto.setIsdefault(AddressConstant.CONTACT_MAIN_ADDRESS);
		}
		return dto;
	}
}
