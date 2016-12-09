package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ScmUploadShipmentHeaderHis;

import java.util.List;

/**
 * @author liukuan
 * @version 1.0
 * @since 2013-2-6 上午9:43:38
 * 
 */
public interface ScmUploadShipmentHeaderHisDao extends GenericDao<ScmUploadShipmentHeaderHis,Long> {
    //获取数据
    List<ScmUploadShipmentHeaderHis> getUnhandledShipments();
    void batchLog(ScmUploadShipmentHeaderHis shipment);
}
