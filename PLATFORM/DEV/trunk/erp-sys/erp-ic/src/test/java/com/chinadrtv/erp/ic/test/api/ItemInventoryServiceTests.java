package com.chinadrtv.erp.ic.test.api;

import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.ic.test.ErpIcContextTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 单元测试.
 * User: gaodejian
 * Date: 13-5-3
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class ItemInventoryServiceTests extends ErpIcContextTests {

    @Autowired
    private ItemInventoryService itemInventoryService;

    @Test
    public void getRealTimeStockTestsWithSKU() {

        String plucode = "102014010001";
        //String ncfreename = "银色";
        List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeStock(plucode);
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems){
            Assert.assertEquals(stockItem.getProductCode(), plucode);
        }
    }

    @Test
    public void getRealTimeStockTests1() {

        String nccode = "1020140100";
        String pluname = "诺基亚手机N8";
        String spellcode = "NJYSJN8";
        String ncfreename = "银色";
        List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeStock(nccode, pluname, spellcode, ncfreename);
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems){
            Assert.assertEquals(stockItem.getNcCode(), nccode);
        }
    }

    @Test
    public void getRealTimeStockTests2() {

        String nccode = "1020140100";
        List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeStock(nccode, "", "", "");
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems){
            Assert.assertEquals(stockItem.getNcCode(), nccode);
        }
    }

    @Test
    public void getRealTimeStockTests3() {

        String pluname = "诺基亚手机N8";
        List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeStock("", pluname, "", "");
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems) {
            Assert.assertEquals(stockItem.getProductName(), pluname);
        }
    }

    @Test
     public void getRealTimeStockTests4() {

        String nccode = "1020140100";
        List<RealTimeStockItem> stockItems = itemInventoryService.getCacheRealTimeStock(nccode, "", "", "");
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems){
            Assert.assertEquals(stockItem.getNcCode(), nccode);
        }
    }

    @Test
    public void getRealTimeStockTests5() {

        String nccode = "1020140100";
        List<RealTimeStockItem> stockItems = itemInventoryService.getCacheRealTimeStock(nccode, "", "", "蓝色");
        Assert.assertNotNull(stockItems);
        for(RealTimeStockItem stockItem : stockItems){
            Assert.assertEquals(stockItem.getNcCode(), nccode);
        }
    }
}
