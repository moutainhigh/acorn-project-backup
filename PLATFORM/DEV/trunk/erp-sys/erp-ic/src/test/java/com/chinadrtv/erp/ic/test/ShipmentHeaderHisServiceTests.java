package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.ic.dao.ShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.model.ScmOutNotice;
import com.chinadrtv.erp.ic.service.ShipmentHeaderHisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-16
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
public class ShipmentHeaderHisServiceTests extends ErpIcContextTests {

    @Autowired
    private ShipmentHeaderHisService simpleService;

    @Test
    public void recountItemAdjustment() {
        simpleService.processShipments("TEST");
    }
}
