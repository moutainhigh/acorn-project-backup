package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Warehouse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-8
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public interface WarehouseDao extends GenericDao<Warehouse, Long> {
    Warehouse findById(Long warehosueId);
    List<Warehouse> getAllWarehouses();
}
