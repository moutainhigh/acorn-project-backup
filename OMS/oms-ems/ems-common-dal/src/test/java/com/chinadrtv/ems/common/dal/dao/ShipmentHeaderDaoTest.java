package com.chinadrtv.ems.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
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
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class ShipmentHeaderDaoTest {

    /*private ShipmentHeaderDao shipmentHeaderDao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml", "mybatis-config.xml"};

        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            shipmentHeaderDao =  appCont.getBean(ShipmentHeaderDao.class);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testQueryDataByLoginAccount() {
        String mailId = "ATV20130314006";
        List<ShipmentHeaderDO> slist = shipmentHeaderDao.queryForList(mailId);
        if (slist.size() == 0) {
            System.out.print("dao-error");
        } else {
            System.out.print(slist.size());
        }
    }*/
}
