package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.OrderSms;

/**
 * 短信发送服务
 * User: liyu
 * Date: 13-1-29
 * Time: 下午1:14
 */
public interface OrderSmsService extends GenericService<OrderSms, String> {

    void sendMsg(String msg, String phone, String contactId, String orderId)  throws Exception;
}
