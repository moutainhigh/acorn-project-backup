package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.PayTypeDao;
import com.chinadrtv.erp.admin.model.PayType;
import com.chinadrtv.erp.admin.service.PayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("PayTypeService")
public class PayTypeServiceImpl implements PayTypeService {
    @Autowired
    private PayTypeDao dao;

    public PayType findById(String id){
        return dao.get(id);
    }

    public List<PayType> getAllPayTypes() {
        return dao.getAllPayTypes();  //To change body of implemented methods use File | Settings | File Templates.
    }



}
