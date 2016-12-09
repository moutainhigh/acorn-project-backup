package com.chinadrtv.erp.ic.test;

import com.chinadrtv.erp.ic.dao.ScmCloseShipmentDao;
import com.chinadrtv.erp.ic.service.ScmCloseShipmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-20
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
public class ScmCloseShipmentServiceTests extends ErpIcContextTests {
    @Autowired
    private ScmCloseShipmentDao scmCloseShipmentDao;
    @Autowired
    private ScmCloseShipmentService scmCloseShipmentService;

    @Test
    public void closeTest()
    {
        System.out.println("test start ***");
        scmCloseShipmentService.insertScmCloseShipmentService();
    }


    @Test
    public void testDao()
    {
        System.out.println("获取数据：");
        System.out.println(scmCloseShipmentDao.getScmCloseShipment().size());

        System.out.println("回写数据情况：");
        System.out.println(scmCloseShipmentDao.updateScmCloseShipment(1l,new Date()));
    }

}
