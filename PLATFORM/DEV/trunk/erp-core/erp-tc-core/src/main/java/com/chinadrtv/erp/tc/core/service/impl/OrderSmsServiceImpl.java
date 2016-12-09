package com.chinadrtv.erp.tc.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.OrderSms;
import com.chinadrtv.erp.tc.core.dao.OrderSmsDao;
import com.chinadrtv.erp.tc.core.service.OrderSmsService;

/**
 * 订单短信服务类
 * User: liyu
 * Date: 13-1-29
 * Time: 下午1:53
 */

@Service
public class OrderSmsServiceImpl extends GenericServiceImpl<OrderSms, String> implements OrderSmsService {

    @Autowired
    private OrderSmsDao orderSmsDao;

    @Override
    protected GenericDao<OrderSms, String> getGenericDao()
    {
        return orderSmsDao;
    }

    @Transactional
    public void sendMsg(String msg, String phone, String contactId, String orderId)  throws Exception
    {
        OrderSms orderSms = new OrderSms();

        orderSms.setType("即时");
        orderSms.setFrom("");
        orderSms.setFromEx("001");
        orderSms.setTo(phone);
        orderSms.setMessage(msg);
        orderSms.setFlag(0);
        orderSms.setCustomerid(contactId);
        orderSms.setOrderid(orderId);
        orderSms.setOperDt(new Date(System.currentTimeMillis()));

        orderSmsDao.saveOrUpdate(orderSms);

    }
}
