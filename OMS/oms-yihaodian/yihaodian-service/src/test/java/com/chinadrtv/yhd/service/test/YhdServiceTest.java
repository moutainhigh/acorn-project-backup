package com.chinadrtv.yhd.service.test;
import com.chinadrtv.yhd.model.YhdOrderConfig;
import com.chinadrtv.yhd.service.YhdFeedbackService;
import com.chinadrtv.yhd.service.YhdOrderInputService;
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
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-24
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class YhdServiceTest {

    @Autowired
    private YhdOrderInputService yhdOrderInputService;

    @Autowired
    private YhdFeedbackService yhdFeedbackService;
    @Test
    public void testOrderInputService() throws Exception
    {
        System.out.println("test begin..........");

        List<YhdOrderConfig> yhdOrderConfigList = new ArrayList<YhdOrderConfig>();
        YhdOrderConfig c = new YhdOrderConfig();
        c.setUrl("http://openapi.yhd.com/app/api/rest/router?");
        c.setAppkey("10220014031900001033");
        c.setAppSecret("7396151465267dc81e35b8bd117ce697");
        c.setSessionKey("c85dedd52ca26fd222ca64f2a9cc8b0c");
        c.setCustomerId("1号商城奥雅旗舰店");
        c.setOrderState("ORDER_WAIT_SEND");
        c.setTmsCode("12345");
        c.setTradeType("240");
        c.setSourceId(2);
        yhdOrderConfigList.add(c);
        Date startDate,endDate;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDate = df.parse("2014-03-10 00:00:00");
        endDate = df.parse("2014-03-23 00:00:00");
        yhdOrderInputService.input(yhdOrderConfigList,startDate,endDate);
        System.out.println("test end............");
    }
    @Test
    public void testFeedbackService()throws Exception{
        System.out.println("test begin..........");

        List<YhdOrderConfig> yhdOrderConfigList = new ArrayList<YhdOrderConfig>();
        YhdOrderConfig c = new YhdOrderConfig();
        c.setUrl("http://openapi.yhd.com/app/api/rest/router?");
        c.setAppkey("10220014031900001033");
        c.setAppSecret("7396151465267dc81e35b8bd117ce697");
        c.setSessionKey("c85dedd52ca26fd222ca64f2a9cc8b0c");
        c.setCustomerId("1号商城奥雅旗舰店");
        c.setOrderState("ORDER_WAIT_SEND");
        c.setTmsCode("12345");
        c.setTradeType("240");
        c.setSourceId(2);
        yhdOrderConfigList.add(c);

         yhdFeedbackService.orderFeedback(yhdOrderConfigList);
        System.out.println("test end............");
    }
}
