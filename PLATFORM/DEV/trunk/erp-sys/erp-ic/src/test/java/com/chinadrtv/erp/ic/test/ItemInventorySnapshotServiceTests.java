package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.ic.service.ItemInventorySnapshotService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 库存事务测试
 * User: gaodejian
 * Date: 13-1-31
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class ItemInventorySnapshotServiceTests extends ErpIcContextTests {

     @Autowired
     private ItemInventorySnapshotService itemInventorySnapshotService;

     @Test
     public void capture() {
         itemInventorySnapshotService.captureEx("TEST");
     }


}
