package com.chinadrtv.remindmail.common.dal.dao.test;

import com.chinadrtv.remindmail.common.dal.dao.MailDetailsDao;
import com.chinadrtv.remindmail.common.dal.model.MailDetails;
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

public class MailDetailsDaoTest {

    private MailDetailsDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml"/*, "mybatis-config.xml"*/};
        String beanName = "mailDetailsDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (MailDetailsDao) appCont.getBean(beanName);
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("companyId","124");
                param.put("startDate","2013-11-09 00:00:00");
                param.put("endDate","2013-11-20 00:00:00");

                List<MailDetails> list = dao.findMailDetailsByAppDate(param);

                if(list.size() > 0){
                    System.out.println(list.size());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
