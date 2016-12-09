package com.chinadrtv.jingdong.service.test;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderImportJDService;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.delivery.LogisticsCompanies;
import com.jd.open.api.sdk.domain.delivery.LogisticsCompany;
import com.jd.open.api.sdk.request.delivery.DeliveryLogisticsGetRequest;
import com.jd.open.api.sdk.response.delivery.DeliveryLogisticsGetResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with (JD).
 * User: 刘宽
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderImportJDServiceTest {
   /* private ApplicationContext appCont;

    @Before
    public void init() {

        String[] contextFileArr = { "classpath*:/spring/applicationContext*.xml",
                "classpath*:/mybatis-config.xml" };
        try {
            appCont = new ClassPathXmlApplicationContext(contextFileArr);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testTransform()
    {
        Date startDate,endDate;
        endDate = new Date();
        startDate = getAddDay(endDate,Calendar.DATE,-1);
        OrderImportJDService importTransformService=appCont.getBean(OrderImportJDService.class);
        if(importTransformService != null){
            JingdongOrderConfig jingdongOrderConfig=new JingdongOrderConfig();
            jingdongOrderConfig.setUrl("http://gw.api.360buy.com/routerjson");
            jingdongOrderConfig.setAppkey("8B1B84B95F6E7EE3DF40D8869B7CF597");
            jingdongOrderConfig.setAppSecret("bb9d81da9eb743a39adafc6f71284fc8");
            jingdongOrderConfig.setSessionKey("759860f2-3585-433d-b5ab-560105a2606b");
            jingdongOrderConfig.setCustomerId("\u4e92\u8054\u7f51\u4eac\u4e1cSOP");
            jingdongOrderConfig.setTradeType("111");
            jingdongOrderConfig.setTmsCode("110");
            jingdongOrderConfig.setTmsName("JINGDONG");
            jingdongOrderConfig.setOrderState("WAIT_SELLER_STOCK_OUT");
            jingdongOrderConfig.setPage(1);
            jingdongOrderConfig.setPageSize(100);

            List<JingdongOrderConfig>  jd = new ArrayList<JingdongOrderConfig>();
            jd.add(jingdongOrderConfig);

            importTransformService.importPreOrdersInfo(jd,startDate,endDate);
            System.out.println("end");
        }
    }
    private Date getAddDay(Date d, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(field, amount);
        return cal.getTime();
    }  */

    @Test
    public void testMok() throws Exception
    {
        JingdongOrderConfig jdorderconfig=new JingdongOrderConfig();
        jdorderconfig.setUrl("http://gw.api.360buy.com/routerjson");
        jdorderconfig.setAppkey("C159FD262FF5D1067796B5F0D99724DC");
        jdorderconfig.setAppSecret("284bc3d62b8a475285b92c502c822c22");
        jdorderconfig.setSessionKey("1ad7dede-f3d1-4c5b-adee-cd7fb6a98753");

        JdClient client = new DefaultJdClient(jdorderconfig.getUrl(), jdorderconfig.getSessionKey(), jdorderconfig.getAppkey(), jdorderconfig.getAppSecret());
        DeliveryLogisticsGetRequest request=new DeliveryLogisticsGetRequest();
        DeliveryLogisticsGetResponse response=client.execute(request);
        LogisticsCompanies logisticsCompanies=response.getLogisticsCompanies();
        List<LogisticsCompany> logisticsCompanyList= logisticsCompanies.getLogisticsList();
        for(LogisticsCompany logisticsCompany:logisticsCompanyList)
        {
            System.out.println(logisticsCompany.getLogisticsId()+"-name-"+logisticsCompany.getLogisticsName()+"-"+logisticsCompany.getSequence()+"-"+logisticsCompany.getLogisticsRemark());
        }
    }
}
