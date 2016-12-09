package com.chinadrtv.erp.uc.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(value = "/contact/findById", method = RequestMethod.POST)
    public Contact findById(@RequestParam(required = true) String contactId) throws Exception {
        return contactService.get(contactId);
    }

    @RequestMapping(value = "/contact/findByOrderId", method = RequestMethod.POST)
    public Contact findByOrderId(@RequestParam(required = true) String orderId) {
        return contactService.findByOrderId(orderId);
    }

    @RequestMapping(value = "/contact/findByShipmentId", method = RequestMethod.POST)
    public Contact findByShipmentId(@RequestParam(required = true) String shipmentId) {
        return contactService.findByShipmentId(shipmentId);
    }

    @RequestMapping(value = "/contact/findByAddress", method = RequestMethod.POST)
    public List<CustomerDto> findByAddress(@RequestParam(required = true) String phone,
                                           @RequestParam(required = true) String name,
                                           @RequestParam(required = false) String provinceId,
                                           @RequestParam(required = false) String cityId,
                                           @RequestParam(required = false) String countyId,
                                           @RequestParam(required = false) String areaId,
                                           @RequestParam(required = false) int page,
                                           @RequestParam(required = false) int rows) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
       /* Map<String, Object> resultMap = contactService.findByBaseCondition(phone, name, provinceId, cityId, countyId, areaId, dataGridModel);
        return (List<CustomerDto>) resultMap.get("rows");*/
        return null;
    }

    @RequestMapping(value = "/contact/setPrimePhone", method = RequestMethod.POST)
    public Boolean setPrimePhone(@RequestParam(required = true) String contactId, @RequestParam(required = true) String phoneId) {
        try {
            phoneService.setPrimePhone(contactId, phoneId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
