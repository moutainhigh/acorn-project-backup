package com.chinadrtv.taobao.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
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
            List<TradeFeedback> feedbackList = dao.findFeedbacks("1000");
            for (TradeFeedback tradeFeedback:feedbackList)
            {
                System.out.print(tradeFeedback.getTradeId());
            }

            if(feedbackList.size()>0)
            {
                PreTrade preTrade=new PreTrade();
                preTrade.setTradeId(feedbackList.get(0).getTradeId());
                preTrade.setOpsTradeId(feedbackList.get(0).getTradeId());
                preTrade.setFeedbackDate(new Date());
                preTrade.setFeedbackStatusRemark("succ");
                preTrade.setFeedbackUser("xzk");
                preTrade.setFeedbackStatus("3");

                int count=dao.updateOrderFeedbackStatus(preTrade);

                PreTrade preTrade1=new PreTrade();
                preTrade1.setOpsTradeId("123");
                preTrade1.setFeedbackDate(new Date());
                preTrade1.setFeedbackStatusRemark("succ");
                preTrade1.setFeedbackUser("xzk");
                preTrade1.setFeedbackStatus("3");

                int count2=dao.updateOrderFeedbackStatus(preTrade1);
                System.out.println(count2);
            }
        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
