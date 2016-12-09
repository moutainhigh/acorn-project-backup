package com.chinadrtv.erp.pay.test;

import com.chinadrtv.erp.core.dao.hibernate.Qualified;
import com.chinadrtv.erp.pay.core.icbc.model.ICBCResPay;
import com.chinadrtv.erp.pay.core.model.CredentialsType;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import com.chinadrtv.erp.pay.core.service.impl.MerchantsCreditcardServiceImpl;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
public class PayServiceTest{

    //@Override
    public void setDataSource(DataSource dataSource)
    {

    }

    @Autowired
    @Qualifier("ICBCCreditcardServiceImpl")
    private OnlinePayAdapterService icbcPayAdapterService;

    //@Test
    public void testMok() throws Exception
    {
        if(icbcPayAdapterService==null)
            System.out.println("xxx");
        else
        {
            /*String str="fds|fdsf||11||";
            String token=new String("\\"+"|");
            String[] datas=str.split(token);
            if(datas.length>3)
                return; */
            System.out.println("yyy");
            Payment payment=new Payment();
            payment.setAmount(new BigDecimal("900"));
            payment.setCardNo("5502138999999725");
            payment.setCredentialsNo("440869760825672");
            payment.setCredentialsType(CredentialsType.IDENTITYCARD);
            payment.setExtraCode("774");
            payment.setMobile("13621914511");

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date expireDate= simpleDateFormat.parse("2017-04-01");
            payment.setExpiryDate(expireDate);
            //payment.setExtraCode("");
            payment.setOrderId("222");
            payment.setStageNum(3);
            PayResult payResult=icbcPayAdapterService.pay(payment);
            if(payResult.isSucc())
                System.out.println(payResult.getRefNum());
            else
                System.out.println(payResult.getErrorCode()+" --- "+payResult.getErrorMsg());
        }
    }

    @Test
    public void test1() throws Exception
    {
        /*ICBCResPay icbcResPay =new ICBCResPay();
        icbcResPay.unmarshal("RES12|0|??Ч???????|13|1101605|07042501|||20130901||||||||");
        System.out.println(icbcResPay.getRefNum());*/

    }
}
