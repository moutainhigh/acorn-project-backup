package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.OmsBackorder;
import java.util.List;

public interface OmsBackorderService {
    Long getBackorderCount(String orderId, String productId, String status, String startDate, String endDate);
    List<OmsBackorder> getAllBackorders(String orderId, String productId, String status, String startDate, String endDate, int index, int size);
    void saveXlsFile(String filePath);
    void removeBackorder(Long id);
    void deferBackorder(Long id, Double days);
}
