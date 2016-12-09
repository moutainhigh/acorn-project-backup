package com.chinadrtv.yhd.common.dal.dao.test;
import com.chinadrtv.yhd.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.yhd.common.dal.model.TradeFeedback;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Ignore
public class TradeFeedbackDaoTest {

    private TradeFeedbackDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml", "mybatis-config.xml"};
        String beanName = "tradFeedbackDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (TradeFeedbackDao) appCont.getBean(beanName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {



        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
