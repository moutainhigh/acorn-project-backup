/**
 *
 */
package com.chinadrtv.erp.customer.services.rest;

import com.chinadrtv.erp.customer.services.dto.CustomerRequest;
import com.chinadrtv.erp.customer.services.dto.PhonesAndAddressDto;
import com.chinadrtv.erp.customer.services.service.CustomerApiService;
import com.chinadrtv.erp.customer.services.service.PhonesAndAddressService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.ContactService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dengqianyong
 */
@Controller
@RequestMapping("/customer")
public class CustomerRest {

    private static final String PHONE_TYPE = "4";
    private static final Integer PHONE_STATE = 4;
    private static final String PRIMARY_PHONE = "Y";

    @Resource
    private CustomerApiService customerApiService;

    @Autowired
    private PhonesAndAddressService phonesAndAddressService;

    @Autowired
    private ContactService contactService;

    @ResponseBody
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Map<String, Object> findCustomerId(@RequestBody CustomerRequest req) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<CustomerDto> customers = customerApiService.findCustomers(req);

        // find one contact at least
        if (customers != null && customers.size() > 0) {
            if (customers.size() > 1) {
                // to sort the collection, order by create date descending
                Collections.sort(customers, new Comparator<CustomerDto>() {
                    @Override
                    public int compare(CustomerDto source, CustomerDto target) {
                        long sourceId = Long.valueOf(source.getContactId());
                        long targetId = Long.valueOf(target.getContactId());
                        return sourceId == targetId ? 0 : (targetId > sourceId ? 1 : -1);
                    }
                });
            }
            result.put("stat", "1");
            result.put("id", customers.get(0).getContactId());
            return result;
        }

        // not found any contact, create and return contact id

        CustomerDto customerDto = crateCustomer(req);
        List<AddressDto> addressDtos = createAddress(req);
        List<Phone> phones = createPhone(req);

        try {
            result.put("stat", "1");
            result.put("id", customerApiService.saveCustomer(customerDto, addressDtos, phones));
        } catch (ServiceTransactionException e) {
            result.put("stat", "0");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // create new customer
    private CustomerDto crateCustomer(CustomerRequest req) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(req.getName());
        return customerDto;
    }

    // create new address
    private List<AddressDto> createAddress(CustomerRequest req) {
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        AddressDto addressDto = customerApiService.createAddress(req.getProvince());
        addressDtos.add(addressDto);
        return addressDtos;
    }

    // create new phone
    private List<Phone> createPhone(CustomerRequest req) {
        List<Phone> phones = new ArrayList<Phone>();
        Phone phone = new Phone();
        phone.setPhn2(req.getPhone());
        phone.setPhoneNum(req.getPhone());
        phone.setPhonetypid(PHONE_TYPE);
        phone.setPrmphn(PRIMARY_PHONE);
        phone.setState(PHONE_STATE);
        phones.add(phone);
        return phones;
    }

    @ResponseBody
    @RequestMapping(value = "/savePhonesAndAddress", method = RequestMethod.POST)
    public Map<String, Object> savePhonesAndAddress(@RequestBody PhonesAndAddressDto req) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtils.isBlank(req.getContactId())) {
            result.put("result", "FAIL");
            result.put("msg", "客户ID为空，不能修改或新增客户电话和地址。");
            return result;
        }

        try {
            contactService.get(req.getContactId());
        } catch (Exception e) {
            result.put("result", "FAIL");
            result.put("msg", "根据ID查询客户出错，不能修改或新增客户电话和地址。");
            return result;
        }

        return phonesAndAddressService.savePhonesAndAddress(req);
    }
}
