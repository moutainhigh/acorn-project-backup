package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OmsBackorder;

import java.util.List;

/**
 * 结算单数据访问接口
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface OmsBackorderDao extends GenericDao<OmsBackorder,Long> {
    Long getBackorderCount(String orderId, String productId, String status, String startDate, String endDate);
    List<OmsBackorder> getAllBackorders(String orderId, String productId, String status, String startDate, String endDate, int index, int size);
    void deferBackorder(Long id, Double days);
}
