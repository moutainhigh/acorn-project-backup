package com.chinadrtv.erp.tc.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Warehouse;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-8
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public interface WarehouseDao extends GenericDao<Warehouse,Long>{
    List<Warehouse> getAllWarehouses();
}
