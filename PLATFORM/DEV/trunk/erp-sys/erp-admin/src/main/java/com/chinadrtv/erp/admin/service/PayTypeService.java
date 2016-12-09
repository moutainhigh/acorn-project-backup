package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.PayType;

import java.util.List;

/**
 * User: taoyawei
 * Date: 12-8-10
 */
public interface PayTypeService {
    PayType findById(String id);
    List<PayType> getAllPayTypes();
}
