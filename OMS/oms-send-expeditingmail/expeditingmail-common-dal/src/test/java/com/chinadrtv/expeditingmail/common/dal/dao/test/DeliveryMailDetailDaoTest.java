package com.chinadrtv.expeditingmail.common.dal.dao.test;

import com.chinadrtv.expeditingmail.common.dal.dao.DeliveryMailDetailDao;
import com.chinadrtv.expeditingmail.common.dal.model.DeliveryMailDetail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (JD).
 * User: 刘宽
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

public class DeliveryMailDetailDaoTest {

    private DeliveryMailDetailDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml"/*, "mybatis-config.xml"*/};
        String beanName = "deliveryMailDetailDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (DeliveryMailDetailDao) appCont.getBean(beanName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            try{
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("p_companyid",6);
                param.put("p_flag",1);
                param.put("p_return",null);
                param.put("p_cur",null);
                dao.execOmsProDeliveryOvertime(param);

                int result = Integer.parseInt(param.get("p_return").toString());
                List<DeliveryMailDetail> inventoryList = (List<DeliveryMailDetail>)param.get("p_cur");
                System.out.print("value1="+param.get("p_return"));
                System.out.print("value2="+param.get("p_cur"));


                //修改状态
                /*int i = dao.updateDeliveryMailDetail("36");
                System.out.println("result = "+i);*/
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        else
        {
            System.out.println("it's error!");
        }
    }

}
