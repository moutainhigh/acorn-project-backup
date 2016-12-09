package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.ic.service.ItemInventoryAllocateService;
import com.chinadrtv.erp.model.inventory.AllocatedEvent;
import com.chinadrtv.erp.model.inventory.AllocatedEventItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-4
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public class ItemInventoryAllocateServiceTests extends ErpIcContextTests {
    @Autowired
    private ItemInventoryAllocateService itemInventoryAllocateService;

    @Test
    public void assignWithThreads()  throws InterruptedException
    {
        for(int i = 0; i< 100; i++){
            Thread t = new Thread() {
                @Override
                public void run()  {
                    System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
                    assign();
                    System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
                }
            };
            t.start();
            Thread.sleep(50);
        }
        Thread.sleep(3000);
    }

    @Test
    public void unassignWithThreads()  throws InterruptedException
    {
        for(int i = 0; i< 100; i++){
            Thread t = new Thread() {
                @Override
                public void run()  {
                    System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
                    unassign();
                    System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
                }
            };
            t.start();
            Thread.sleep(50);
        }
        Thread.sleep(3000);
    }

    @Test
    public void assign() {
        AllocatedEvent event = new AllocatedEvent();
        event.setChannel(ChannelConstants.Default);
        event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
        event.setSourceId(1L);
        event.setWarehouse("1");
        event.setUser("TEST2");
        event.setBusinessNo("2012041850907139");

        AllocatedEventItem item = new AllocatedEventItem();
        item.setLocationType("1");
        item.setProductId(90115L);
        item.setProductCode("106071590001");
        item.setQuantity(1.0);
        event.getEventItems().add(item);
        itemInventoryAllocateService.assign(event);

    }

    @Test
    public void unassign() {
        AllocatedEvent event = new AllocatedEvent();
        event.setChannel(ChannelConstants.Default);
        event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
        event.setSourceId(1L);
        event.setWarehouse("1");
        event.setUser("TEST3");
        event.setBusinessNo("2013041951090639");

        AllocatedEventItem item = new AllocatedEventItem();
        item.setLocationType("1");
        item.setProductId(90115L);
        item.setProductCode("106071590001");
        item.setQuantity(1.0);
        event.getEventItems().add(item);
        itemInventoryAllocateService.unassign(event);

    }
}
