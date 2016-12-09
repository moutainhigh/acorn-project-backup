package com.chinadrtv.erp.oms.service.impl;

import java.util.List;
import com.chinadrtv.erp.model.OmsBackorder;
import com.chinadrtv.erp.oms.dao.OmsBackorderDao;
import com.chinadrtv.erp.oms.service.OmsBackorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("omsBackorderService")
public class OmsBackorderServiceImpl implements OmsBackorderService {

    @Autowired
    private OmsBackorderDao omsBackorderDao;


    public Long getBackorderCount(String orderId, String productId, String status, String startDate, String endDate) {
        return omsBackorderDao.getBackorderCount(orderId, productId, status, startDate, endDate);
    }

    public List<OmsBackorder> getAllBackorders(String orderId, String productId, String status, String startDate, String endDate, int index, int size) {
        return omsBackorderDao.getAllBackorders(orderId, productId, status, startDate, endDate, index, size);
    }

    public void saveXlsFile(String filePath){

    }

    public void removeBackorder(Long id){
        omsBackorderDao.remove(id);
    }

    public void deferBackorder(Long id, Double days){
        omsBackorderDao.deferBackorder(id, days);
    }
}
