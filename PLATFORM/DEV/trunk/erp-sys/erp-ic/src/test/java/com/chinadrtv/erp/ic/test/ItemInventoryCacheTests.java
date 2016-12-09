package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 库存缓存测试
 * User: gaodejian
 * Date: 13-2-27
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
public class ItemInventoryCacheTests extends ErpIcContextTests {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventoryCacheTests.class);
    @Autowired
    private ItemInventoryDao itemInventoryDao;

    @Test
    public void cacheUpdate() {

        String channel = "ACORN";
        String warehuuse = "1";
        String locationType = "1";
        Long productId = 85565L;
        ItemInventoryChannel itemInventoryChannel = itemInventoryDao.getCacheItemInventory(channel, warehuuse, locationType, productId);
        if (itemInventoryChannel != null)
        {
            try
            {
                //模拟业务执行
                Thread.sleep((int)(Math.random()*50) + 50);
            }
            catch (InterruptedException ex)
            {
                LOG.error(ex.getMessage());
            }
            itemInventoryDao.saveCacheItemInventory(channel, warehuuse, locationType, productId,itemInventoryChannel);
        }
    }

    @Test
    public void cacheUpdateWithThreads() throws InterruptedException {

        for(int i = 0; i < 1000; i++){
            Thread t = new Thread() {
                @Override
                public void run() {
                    //System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
                    LOG.info(Thread.currentThread().getName() + " 线程运行开始!");
                    cacheUpdate();
                    //System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
                    LOG.info(Thread.currentThread().getName() + " 线程运行结束!");
                }
            };
            t.start();
            Thread.sleep(50);
        }
        Thread.sleep(3000);
    }
}
