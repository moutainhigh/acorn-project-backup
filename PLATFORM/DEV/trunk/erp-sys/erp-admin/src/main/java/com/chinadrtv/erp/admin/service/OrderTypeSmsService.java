package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.OrderTypeSmsSend;

import java.util.Date;
import java.util.List;

/**
 * User: taoyawei
 * Date: 12-11-13
 */
public interface OrderTypeSmsService {

    void addOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend);

    int getOrderTypeSmsSendCountByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                          String usid,String paytype,Date beginDate, Date endDate);

    List<OrderTypeSmsSend> searchPaginatedOrderTypeSmsSendByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                                                    String usid,String paytype,Date beginDate, Date endDate, int startIndex, Integer numPerPage);

    void saveOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend);
    void delOrderTypeSmsLog(Integer id);
}
