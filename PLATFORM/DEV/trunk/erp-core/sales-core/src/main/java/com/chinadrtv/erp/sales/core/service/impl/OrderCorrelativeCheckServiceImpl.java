package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.service.CardChangeService;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单关联对象有效性检查
 * 订单关联的联系人、地址、电话有效性检查
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("OrderCorrelativeCheckServiceImpl")
public class OrderCorrelativeCheckServiceImpl implements OrderRuleCheckService {
    @Autowired
    private ContactService contactService;

    //@Autowired
    //private PhoneService phoneService;

    @Autowired
    private CardService cardService;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    @Qualifier("com.chinadrtv.erp.tc.core.service.impl.AddressExtServiceImpl")
    private AddressExtService addressExtService;

    @Autowired
    private CardChangeService cardChangeService;


    @Override
    public boolean checkOrder(Order order) {
        //关联联系人、电话、地址、信用卡是否有效
        Contact contact = contactService.get(order.getGetcontactid());
        if(!this.isValid(contact.getState()))
            return false;
        if(order.getAddress()!=null&&StringUtils.isNotEmpty(order.getAddress().getAddressId()))
        {
            order.setAddress(addressExtService.getAddressExt(order.getAddress().getAddressId()));
            if(!this.isValid(order.getAddress().getAuditState()))
                return false;
        }

        return cardChangeService.isOrderCardValid(order);
    }

    @Override
    public String getRuleName() {
        return "correlative valid check";
    }

    @Override
    public String getBPMType() {
        return "";
    }

    private boolean isValid(Integer state)
    {
        if(state==null)
            return true;
        if(state.intValue() == CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED)
        {
            return true;
        }

        return false;
    }
}
