package com.chinadrtv.chama.common.dal.dao;

import com.chinadrtv.chama.dal.dao.TradeFeedbackDao;
import com.chinadrtv.chama.dal.model.TradeFeedback;
import com.chinadrtv.model.oms.PreTrade;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with
 * User: 刘宽
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

public class TradeFeedbackDaoTest {

    private TradeFeedbackDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml", "mybatis-config.xml"};
        String beanName = "tradFeedbackDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (TradeFeedbackDao) appCont.getBean(beanName);
            System.out.print("");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            List<String> ordertype = new ArrayList<String>();
            ordertype.add("100");
            ordertype.add("103");
            try{
                List<TradeFeedback> feedbackList = dao.findByOpsId(ordertype);
                for (TradeFeedback tradeFeedback:feedbackList)
                {
                    System.out.print(tradeFeedback.getOpsTradeId());
                }
                if(feedbackList.size()>0)
                {
                    PreTrade preTrade=new PreTrade();
                    preTrade.setTradeId(feedbackList.get(0).getOpsTradeId());
                    preTrade.setOpsTradeId(feedbackList.get(0).getOpsTradeId());
                    preTrade.setFeedbackDate(new Date());
                    preTrade.setFeedbackStatusRemark("succ");
                    preTrade.setFeedbackUser("liukuan");
                    preTrade.setFeedbackStatus("2");

                    int count=dao.updateOrderFeedbackStatus(preTrade);


                    System.out.println(count);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
