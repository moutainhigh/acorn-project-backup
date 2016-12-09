package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-5-2
 * Time: 下午12:47
 * To change this template use File | Settings | File Templates.
 */
public class MemCachedTest extends ErpIcContextTests {
    @Test
    public void getKey() {
        try {
            MemcachedClient  mcc = new MemcachedClient(new InetSocketAddress("192.168.96.20", 11211));
            ItemInventoryChannel ic = (ItemInventoryChannel)mcc.get("com.chinadrtv.erp.ic.dao.hibernate.ItemInventoryDaoImpl:ACORN/1/1/114242") ;
            System.out.println(ic);
            String v = (String)mcc.get("com.chinadrtv.erp.oms.model.Company:6") ;
            System.out.println(v)  ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
