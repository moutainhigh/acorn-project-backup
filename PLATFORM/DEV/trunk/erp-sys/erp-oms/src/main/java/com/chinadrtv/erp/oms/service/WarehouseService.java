package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.Warehouse;

import java.util.List;

/**
 * 仓库服务
 * User: gaodejian
 * Date: 13-3-8
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
public interface WarehouseService {
    Warehouse findById(Long warehosueId);
    List<Warehouse> getAllWarehouses();
}
