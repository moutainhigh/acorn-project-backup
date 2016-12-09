package com.chinadrtv.gonghang.service.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.gonghang.service.OrderImportService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-24
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class IcbcServiceTest {

    @Autowired
    private OrderImportService orderImportService;

    @Test
    public void testOrderInputService() throws Exception
    {
        System.out.println("test begin..........");

        //orderImportService.createOrder();
        System.out.println("test end............");
    }
   
}
