package com.chinadrtv.ems.service;

import com.chinadrtv.ems.common.dal.model.ShipmentHeaderDO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class ShipmentHeaderBaseServiceTest {
  /*  private ApplicationContext appCont;

    @Before
    public void init() {

        String[] contextFileArr = { "classpath*:/spring/applicationContext*.xml",
                "classpath*:/mybatis-config.xml" };
        try {
            appCont = new ClassPathXmlApplicationContext(contextFileArr);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCustomerQuery(){

        ShipmentHeaderBaseService shipmentHeaderBaseService = (ShipmentHeaderBaseService) appCont
                .getBean("shipmentHeaderBaseService");

        if (shipmentHeaderBaseService == null) {
            System.out.println("null error");
        }

        System.out.println(shipmentHeaderBaseService.toString());
        List<ShipmentHeaderDO> sList =  shipmentHeaderBaseService.queryShipmentHeaderByMailId("ATV20130314006");

        System.out.println(sList.size());

    }*/
}
