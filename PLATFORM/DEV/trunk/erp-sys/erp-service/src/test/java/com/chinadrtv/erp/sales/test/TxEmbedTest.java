package com.chinadrtv.erp.sales.test;

import com.chinadrtv.erp.sales.core.service.OrderChangeService;
import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-8-27
 * Time: 下午1:32
 * To add class comments
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext-dao.xml",
                                  "classpath:/applicationContext-resources.xml",
                                  "classpath:/applicationContext-service.xml"})

@TransactionConfiguration
@Transactional
public class TxEmbedTest {

    @Autowired
    private OrderChangeTestService orderChangeTestService;

    @Test
    public void testContext(){
        orderChangeTestService.getOrderChange(501051L);
    }

    @Test
    public void testGet(){
        Date d1 = new Date() ;
        orderChangeTestService.getOrderChangeEmbed(501051L);
        Date d2 = new Date() ;
        System.out.println(d2.getTime() - d1.getTime());
    }

    @Test
    public void testRequired(){
        Date d1 = new Date() ;
        orderChangeTestService.requiredOrderChangeEmbed(501051L);
        Date d2 = new Date() ;
        System.out.println(d2.getTime() - d1.getTime());
    }

    @Test
    public void testGet1(){
        Date d1 = new Date() ;
        orderChangeTestService.getOrderChangeEmbed(501051L);
        Date d2 = new Date() ;
        System.out.println(d2.getTime() - d1.getTime());
    }

    @Test
    public void testRequired1(){
        Date d1 = new Date() ;
        orderChangeTestService.requiredOrderChangeEmbed(501051L);
        Date d2 = new Date() ;
        System.out.println(d2.getTime() - d1.getTime());
    }

    @Test
    public void testGet2(){
        Date d1 = new Date() ;
        orderChangeTestService.getOrderChangeEmbed(501051L);
        Date d2 = new Date() ;
        System.out.println(d2.getTime() - d1.getTime());
    }
}
