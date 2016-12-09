package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PreItemInventory;

import java.util.List;

/**
 * 结算单数据访问接口
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface PreItemInventoryDao extends GenericDao<PreItemInventory,Long> {
    Long getInventoryItemCount(String sourceId, String skuId, String outSkuId, String startDate, String endDate);
    List<PreItemInventory> getAllInventoryItems(String sourceId, String skuId, String outSkuId, String startDate, String endDate, int index, int size);
}
