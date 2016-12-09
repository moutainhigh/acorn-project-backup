package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.oms.dao.WarehouseDao;
import com.chinadrtv.erp.oms.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库服务
 * User: gaodejian
 * Date: 13-3-8
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 */
@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseDao warehouseDao;

    public Warehouse findById(Long warehosueId){
        return warehouseDao.findById(warehosueId);
    }

    public List<Warehouse> getAllWarehouses(){
        return warehouseDao.getAllWarehouses();
    }
}
