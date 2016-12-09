package com.chinadrtv.amazon.service.test;

import com.chinadrtv.amazon.model.TradeResultInfo;
import com.chinadrtv.amazon.model.TradeResultList;
import com.chinadrtv.amazon.service.OrderFeedbackFetchService;
import com.chinadrtv.amazon.service.OrderFeedbackService;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.OrderFetchService;
import com.chinadrtv.amazon.service.OrderImportService;
import com.chinadrtv.model.oms.PreTrade;
import org.apache.commons.lang.time.DateUtils;
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
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration//( defaultRollback = false)
@Transactional
public class OrderImportServiceTest {
    @Autowired
    private OrderFetchService orderFetchService;

    @Autowired
    private OrderImportService orderImportService;

    @Autowired
    private OrderFeedbackService orderFeedbackService;

    //@Test
    public void testFetch()  throws Exception
    {
        AmazonOrderConfig amazonOrderConfig=new AmazonOrderConfig();
        amazonOrderConfig.setConfigstr("https://mws.amazonservices.com.cn/Orders/2011-01-01");
        amazonOrderConfig.setAccessKeyId("AKIAJYV5WAU6N4JJTKUQ");
        amazonOrderConfig.setSecretAccessKey("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");
        amazonOrderConfig.setApplicationName("datasync");
        amazonOrderConfig.setApplicationVersion("1.0");
        amazonOrderConfig.setSellerId("A2LMERIUFWLP7M");
        amazonOrderConfig.setMarketplaceId("AAHKV2X7AFYLW");
        amazonOrderConfig.setCustomerId("\u5353\u8d8a\u4e9a\u9a6c\u900a");
        amazonOrderConfig.setTmsCode("107");
        amazonOrderConfig.setTradeType("123");
        amazonOrderConfig.setTradeFrom("AMAZON");
        amazonOrderConfig.setTradeTypeName("MFN");
        amazonOrderConfig.setSourceId(4L);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate=simpleDateFormat.parse("2013-11-20 00:00:00");
        Date endDate=simpleDateFormat.parse("2013-11-20 05:00:00");
       //orderFetchService.getOrders(amazonOrderConfig,startDate,endDate);

        List<AmazonOrderConfig> amazonOrderConfigList=new ArrayList<AmazonOrderConfig>();
        amazonOrderConfigList.add(amazonOrderConfig);
        orderImportService.importOrders(amazonOrderConfigList,startDate,endDate);
    }
    //@Test
    public void testTransform()
    {
        AmazonOrderConfig amazonOrderConfig=new AmazonOrderConfig();
        amazonOrderConfig.setConfigstr("https://mws.amazonservices.com.cn/Orders/2011-01-01");
        amazonOrderConfig.setAccessKeyId("AKIAJYV5WAU6N4JJTKUQ");
        amazonOrderConfig.setSecretAccessKey("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");
        amazonOrderConfig.setApplicationName("datasync");
        amazonOrderConfig.setApplicationVersion("1.0");
        amazonOrderConfig.setSellerId("A2LMERIUFWLP7M");
        amazonOrderConfig.setMarketplaceId("AAHKV2X7AFYLW");
        amazonOrderConfig.setCustomerId("\u5353\u8d8a\u4e9a\u9a6c\u900a");
        amazonOrderConfig.setTmsCode("107");
        amazonOrderConfig.setTradeType("123");
        amazonOrderConfig.setTradeFrom("AMAZON");
        amazonOrderConfig.setTradeTypeName("MFN");
        amazonOrderConfig.setSourceId(4L);

        List<AmazonOrderConfig> amazonOrderConfigList=new ArrayList<AmazonOrderConfig>();
        amazonOrderConfigList.add(amazonOrderConfig);
        orderImportService.importOrders(amazonOrderConfigList, DateUtils.addDays(new Date(), -1),DateUtils.addMinutes(new Date(),-5));
        //List<PreTradeDto> preTradeDtoList =orderFetchService.getOrders(amazonOrderConfig, DateUtils.addDays(new Date(),-1),DateUtils.addMinutes(new Date(),-5));
        //System.out.println(preTradeDtoList.size());
    }

    //@Test
    public void testFeedback()
    {
        AmazonOrderConfig amazonOrderConfig=new AmazonOrderConfig();
        amazonOrderConfig.setConfigstr("https://mws.amazonservices.com.cn/Orders/2011-01-01");
        amazonOrderConfig.setAccessKeyId("AKIAJYV5WAU6N4JJTKUQ");
        amazonOrderConfig.setSecretAccessKey("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");
        amazonOrderConfig.setApplicationName("datasync");
        amazonOrderConfig.setApplicationVersion("1.0");
        amazonOrderConfig.setSellerId("A2LMERIUFWLP7M");
        amazonOrderConfig.setMarketplaceId("AAHKV2X7AFYLW");
        amazonOrderConfig.setCustomerId("\u5353\u8d8a\u4e9a\u9a6c\u900a");
        amazonOrderConfig.setTmsCode("107");
        amazonOrderConfig.setTradeType("123");
        amazonOrderConfig.setTradeFrom("AMAZON");
        amazonOrderConfig.setTradeTypeName("MFN");
        amazonOrderConfig.setSourceId(4L);
        amazonOrderConfig.setFeedbackUrl("https://mws.amazonservices.com.cn");

        TradeResultList tradeResultList=orderFeedbackService.getTradeResultInfo(amazonOrderConfig, "1824312897");
        if(tradeResultList!=null)
        {
            for(TradeResultInfo tradeResultInfo:tradeResultList.getTradeResultInfos())
            {
                System.out.println(tradeResultInfo.getMessageId());
            }
        }
    }

    @Autowired
    private OrderFeedbackFetchService orderFeedbackFetchService;
    @Test
    public void testJustFeedback()
    {
        PreTrade preTrade = new PreTrade();
        preTrade.setFeedbackStatus("4");
        String errMsg = "提交失败，原因：test";
        preTrade.setFeedbackStatusRemark(errMsg);
        preTrade.setFeedbackUser("esb-order-feedback-amazon");
        preTrade.setFeedbackDate(new Date());
        preTrade.setFeedbackSubmissionId("123");
        preTrade.setFeedbackMessageId("333");

        orderFeedbackFetchService.updateOrderFeedbackResultStatus(preTrade);
    }
}
