package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.sales.dto.CustomerPhoneFrontDto;
import com.chinadrtv.erp.uc.dto.AddressDto;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ContactEditService {
    void createEditNameAndSexBatchAndFlow(String name, String sex, String customerId, String lastBatchId) throws MarketingException;

    Map createEditPhoneAndAddressBatchAndFlow(String customerId, String lastBatchId, List<CustomerPhoneFrontDto> newPhones, List<AddressDto> newAddresss) throws MarketingException;
}
