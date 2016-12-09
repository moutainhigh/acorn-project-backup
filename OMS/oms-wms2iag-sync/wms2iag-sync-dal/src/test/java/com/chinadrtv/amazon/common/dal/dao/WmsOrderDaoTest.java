package com.chinadrtv.amazon.common.dal.dao;

import com.chinadrtv.wms2iag.sync.dal.iagent.dao.OrderExtDao;
import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.dal.wms.dao.WmsOrderExtDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration(defaultRollback =false)
@Transactional("iagentTransactionManager")
public class WmsOrderDaoTest {

    @Autowired
    private WmsOrderExtDao dao;

    @Autowired
    private OrderExtDao orderExtDao;

    //@Test
    public void testMok()  throws Exception
    {
        if(dao!=null)
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt=simpleDateFormat.parse("2013-07-01 10:00:00");
            List<OrderExt> list=dao.findOrders(dt);
            System.out.println(list.size());

        }
        else
        {
            System.out.println("it's error!");
        }
    }

    @Test
    public void testFind()
    {
        List<String> list=new ArrayList<String>();
        list.add("907315273");
        list.add("928902222");
        list.add("9801070261");

        List<OrderExt> orderList=orderExtDao.findOrderIds(list);
        System.out.println(orderList.size());
    }
}
