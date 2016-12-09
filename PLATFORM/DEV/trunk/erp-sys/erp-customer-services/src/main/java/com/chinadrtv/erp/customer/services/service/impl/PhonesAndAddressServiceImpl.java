package com.chinadrtv.erp.customer.services.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.customer.services.dao.CustomerAddressDao;
import com.chinadrtv.erp.customer.services.dto.AddressApiDto;
import com.chinadrtv.erp.customer.services.dto.CustomerServiceAddressDto;
import com.chinadrtv.erp.customer.services.dto.CustomerServicePhoneDto;
import com.chinadrtv.erp.customer.services.dto.PhonesAndAddressDto;
import com.chinadrtv.erp.customer.services.service.PhonesAndAddressService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.PhoneService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xieguoqiang on 14-5-15.
 */
@Service
public class PhonesAndAddressServiceImpl implements PhonesAndAddressService {
    @Autowired
    private AddressService addressService;
    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Override
    public Map savePhonesAndAddress(PhonesAndAddressDto phonesAndAddressDto) {
        Map<String, Object> result = new HashMap<String, Object>();
        String contactId = phonesAndAddressDto.getContactId();
        List<CustomerServicePhoneDto> phones = phonesAndAddressDto.getPhones();
        CustomerServiceAddressDto customerServiceAddressDto = phonesAndAddressDto.getCustomerServiceAddressDto();
        AddressApiDto addressApiDto = null;
        if (customerServiceAddressDto != null && StringUtils.isNotBlank(customerServiceAddressDto.getProvinceName())
                && StringUtils.isNotBlank(customerServiceAddressDto.getCityName())
                && StringUtils.isNotBlank(customerServiceAddressDto.getCountyName())
                && StringUtils.isNotBlank(customerServiceAddressDto.getAreaName())) {
            try {
                addressApiDto = customerAddressDao.findAddressInfo(customerServiceAddressDto.getProvinceName(), customerServiceAddressDto.getCityName(), customerServiceAddressDto.getCountyName(), customerServiceAddressDto.getAreaName());
            } catch (Exception e) {
                result.put("result", "FAIL");
                result.put("msg", "四级地址不合法，处理失败。" + e.getMessage());
                return result;
            }
            DataGridModel dataGridModel = new DataGridModel();
            dataGridModel.setRows(10000);
            List<AddressDto> pageList = (List) addressService.queryAddressPageList(dataGridModel, contactId).get("rows");
            for (AddressDto addressDto : pageList) {
                if (addressApiDto.getProvinceId().equals(addressDto.getState()) &&
                        addressApiDto.getCityId().equals(addressDto.getCityId()) &&
                        addressApiDto.getCountyId().equals(addressDto.getCountyId()) &&
                        addressApiDto.getAreaId().equals(addressDto.getAreaid()) &&
                        customerServiceAddressDto.getAddressDetail().equals(addressDto.getAddressDesc())) {
                    result.put("result", "SUCCESS");
                    result.put("msg", "客户信息更新成功。");
                    result.put("addressId", addressDto.getAddressid());
                    break;
                }
            }
            if (!"SUCCESS".equals(result.get("result"))) {
                if (StringUtils.isNotBlank(customerServiceAddressDto.getAddressId())) {
                    AddressDto addressDto = null;
                    try {
                        addressDto = addressService.queryAddress(customerServiceAddressDto.getAddressId());
                    } catch (Exception e) {
                        result.put("result", "FAIL");
                        result.put("msg", "根据地址ID获取地址失败，" + e.getMessage());
                        return result;
                    }
                    addressDto.setState(addressApiDto.getProvinceId());
                    addressDto.setCityId(addressApiDto.getCityId());
                    addressDto.setCountyId(addressApiDto.getCountyId());
                    addressDto.setAreaid(addressApiDto.getAreaId());
                    if (StringUtils.isNotBlank(customerServiceAddressDto.getPostCode()))
                        addressDto.setZip(customerServiceAddressDto.getPostCode());
                    if (StringUtils.isNotBlank(customerServiceAddressDto.getAddressDetail()))
                        addressDto.setAddress(customerServiceAddressDto.getAddressDetail());
                    try {
                        addressService.updateAddress(addressDto);
                        result.put("result", "SUCCESS");
                        result.put("msg", "客户信息更新成功。");
                        result.put("addressId", addressDto.getAddressid());
                    } catch (Exception e) {
                        result.put("result", "FAIL");
                        result.put("msg", "更新地址失败，" + e.getMessage());
                        return result;
                    }
                } else {
                    AddressDto dto = new AddressDto();
                    dto.setContactid(contactId);
                    dto.setState(addressApiDto.getProvinceId().toString());
                    dto.setProvinceName(addressApiDto.getProvinceName());
                    dto.setCityId(addressApiDto.getCityId());
                    dto.setCityName(addressApiDto.getCityName());
                    dto.setCountyId(addressApiDto.getCountyId());
                    dto.setCountyName(addressApiDto.getCountyName());
                    dto.setAreaid(addressApiDto.getAreaId());
                    dto.setAreaName(addressApiDto.getAreaName());
                    dto.setCountySpellid(addressApiDto.getSpellId());
                    dto.setZip(addressApiDto.getZip());
                    dto.setStatus(addressApiDto.getAuditState().toString());
                    dto.setAddress(customerServiceAddressDto.getAddressDetail());
                    dto.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
                    try {
                        Address address = addressService.addAddress(dto);
                        result.put("result", "SUCCESS");
                        result.put("msg", "客户信息更新成功。");
                        result.put("addressId", address.getAddressid());
                    } catch (Exception e) {
                        result.put("result", "FAIL");
                        result.put("msg", "新增地址失败，" + e.getMessage());
                        return result;
                    }
                }
            }
        }

        for (CustomerServicePhoneDto phoneDto : phones) {
            Phone phone = new Phone();
            phone.setPhn1(phoneDto.getPhn1());
            phone.setPhn2(phoneDto.getPhn2());
            phone.setPhn3(phoneDto.getPhn3());
            phone.setPhonetypid(phoneDto.getPhoneType());
            phone.setContactid(contactId);
            phone.setPrmphn("N");
            phone.setState(4);
            if (StringUtils.isNotBlank(phone.getPhn2())) {
                Phone phoneInDB = phoneService.findByPhoneFullNumAndContactId(phone.getPhn1(), phone.getPhn2(), phone.getPhn3(), contactId);
                if (phoneInDB.getPhoneid()!=null && "Y".equals(phoneDto.getPrmphn())) phoneService.setPrimePhone(contactId, phoneInDB.getPhoneid().toString());
                if (phoneInDB.getPhoneid()!=null && "B".equals(phoneDto.getPrmphn())) phoneService.setBackupPhone(contactId, phoneInDB.getPhoneid().toString());
                if (phoneInDB.getPhoneid() == null) phone = phoneService.addPhoneWithCkeckSelfExistForService(phone);
            }
            if (phone.getPhoneid() != null && "Y".equals(phoneDto.getPrmphn())) {
                phoneService.setPrimePhone(contactId, phone.getPhoneid().toString());
            }
            if (phone.getPhoneid() != null && "B".equals(phoneDto.getPrmphn())) {
                phoneService.setBackupPhone(contactId, phone.getPhoneid().toString());
            }
        }
        result.put("result", "SUCCESS");
        result.put("msg", "客户信息更新成功。");
        return result;
    }
}
