package com.chinadrtv.amazon.common.dal.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class TradeFeedbackDaoTest{

    @Autowired
    private TradeFeedbackDao dao;

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            List<String> list=dao.findFeedbackResults("1000");
            System.out.println(list.size());
           /* List<TradeFeedback> feedbackList = dao.findFeedbacks("1000");
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
            }*/
        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
