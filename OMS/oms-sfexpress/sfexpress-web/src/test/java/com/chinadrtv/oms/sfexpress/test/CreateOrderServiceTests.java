package com.chinadrtv.oms.sfexpress.test;

import com.chinadrtv.oms.sfexpress.service.ShipmentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-21
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class CreateOrderServiceTests {
    @Autowired
    private ShipmentService createOrderServiceTest;

    @Test
    public void testService()
    {
         System.out.println("test begin..........");
         //createOrderServiceTest.createOrder();
         System.out.println("test end............");
    }
}
