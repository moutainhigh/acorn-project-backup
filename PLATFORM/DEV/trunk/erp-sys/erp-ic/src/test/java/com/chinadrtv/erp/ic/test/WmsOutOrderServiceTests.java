package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.ic.dao.ScmUploadReceiptHeaderHisDao;
import com.chinadrtv.erp.ic.dao.ScmUploadShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.dao.WmsShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.service.WmsOutOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public class WmsOutOrderServiceTests extends ErpIcContextTests {
    //wms出库测试
    @Autowired
    private ScmUploadShipmentHeaderHisDao scmUploadShipmentHeaderHisDao;
    @Autowired
    private WmsShipmentHeaderHisDao wmsShipmentHeaderHisDao;
    @Autowired
    private WmsOutOrderService wmsOutOrderService;

    @Test
    public void ts()
    {
        System.out.println("获取Scm信息");
        System.out.println(wmsShipmentHeaderHisDao.getUnhandledShipments().size());
        // scmUploadShipmentHeaderHisDao.getUnhandledShipments();

        //System.out.println(scmUploadShipmentHeaderHisDao.getUnhandledShipments().size());
        // String a = scmUploadShipmentHeaderHisDao.getUnhandledShipments().get(0).getWarehouse();


    }
    @Test
    public void testService()
    {
        wmsOutOrderService.shipmentsWMS("liu");
    }
}
