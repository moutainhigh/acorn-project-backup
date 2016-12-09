package com.chinadrtv.erp.pay.test;

import com.chinadrtv.erp.pay.core.model.CredentialsType;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.test.SpringTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PayTest extends SpringTest {
    @Test
    public void testStr() throws Exception
    {
        OnlinePayment onlinePayment=new OnlinePayment();
        onlinePayment.setPaytype("Merchants");
        onlinePayment.setAmount(new BigDecimal("123.4"));
        onlinePayment.setCardNo("123456789");
        onlinePayment.setCredentialsNo("45678");
        onlinePayment.setExpiryDate(new Date());
        onlinePayment.setCredentialsType(CredentialsType.PASSORT);
        onlinePayment.setExtraCode("333");
        onlinePayment.setMobile("1381381302");
        onlinePayment.setOrderId("2980808080");
        onlinePayment.setStageNum(3);

        ObjectMapper objectMapper= new ObjectMapper();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        objectMapper.setDateFormat(simpleDateFormat);
        String strData=objectMapper.writeValueAsString(onlinePayment);
        System.out.println(strData);
    }

}
