package com.chinadrtv.erp.customer.services.dto;

import java.io.Serializable;
import java.util.List;

public class PhonesAndAddressDto implements Serializable {

    private String contactId;

    private List<CustomerServicePhoneDto> phones;

    private CustomerServiceAddressDto customerServiceAddressDto;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public List<CustomerServicePhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<CustomerServicePhoneDto> phones) {
        this.phones = phones;
    }

    public CustomerServiceAddressDto getCustomerServiceAddressDto() {
        return customerServiceAddressDto;
    }

    public void setCustomerServiceAddressDto(CustomerServiceAddressDto customerServiceAddressDto) {
        this.customerServiceAddressDto = customerServiceAddressDto;
    }
}
