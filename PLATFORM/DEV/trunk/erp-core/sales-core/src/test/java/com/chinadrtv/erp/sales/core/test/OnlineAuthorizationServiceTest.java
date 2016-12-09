package com.chinadrtv.erp.sales.core.test;

import com.chinadrtv.erp.sales.core.Merchants.model.Response;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorization;
import com.chinadrtv.erp.sales.core.service.CreditcardOnlineAuthorizationService;
import com.chinadrtv.erp.sales.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.tc.core.dao.NamesDao;
import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.sales.core.Merchants.service.OnlineAuthorization;
import com.chinadrtv.erp.sales.core.Merchants.service.impl.OnlineAuthorizationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@TransactionConfiguration( defaultRollback = false)
public class OnlineAuthorizationServiceTest extends SpringTest {

    @Autowired
    private NamesDao namesDao;

    @Autowired
    private CreditcardOnlineAuthorizationService creditcardOnlineAuthorizationService;

    @Test
    public void test1() throws Exception
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date dt=simpleDateFormat.parse("2023-01-01");
        CreditcardOnlineAuthorization creditcardOnlineAuthorization=new CreditcardOnlineAuthorization();
        creditcardOnlineAuthorization.setAmount(new BigDecimal(100));
        creditcardOnlineAuthorization.setCardNo("5525349300670201");
        creditcardOnlineAuthorization.setExpiryDate(dt);
        creditcardOnlineAuthorization.setStageNum(1);

        creditcardOnlineAuthorizationService.hirePurchase(creditcardOnlineAuthorization);
        //creditcardOnlineAuthorizationService.hirePurchaseReturn();
        /*Names traceNumInfo=new Names();
        traceNumInfo.setDsc("1");
        traceNumInfo.setTdsc("xxxyy");
        traceNumInfo.setValid("-1");
        NamesId namesId = new NamesId("TEST", "1");
        traceNumInfo.setId(namesId);

        int num=namesDao.execUpdate("update Names n set n.dsc=:dsc where n.id.tid=:tid and n.id.id=:id and n.dsc=:oldDsc and n.tdsc=:tdsc",
                new ParameterString("dsc","2"), new ParameterString("tid","TEST"),new ParameterString("id","1"),new ParameterString("oldDsc","1"),new ParameterString("tdsc","xxxy"));
         */
        //Integer num=this.fetchTraceNum("130528");
        //System.out.println(num);
        /*try
        {
            namesDao.addNames(traceNumInfo);
        }catch (Exception exp)
        {
            if(exp instanceof DataIntegrityViolationException)
            {
                System.out.println(exp.getMessage());
            }
            throw exp;
        } */
    }

    @Autowired
    private MerchantsCreditcardTracenumService merchantsCreditcardTracenumService;

    private Integer fetchTraceNum(String currentBatchNum)
    {
        //首先获取，如果获取不到(或者有并发插入)，等待一段时间重试
        for(int i=0;i<3;i++)
        {
            try
            {
                Integer traceNum = merchantsCreditcardTracenumService.calcTraceNum(currentBatchNum);
                if(traceNum!=null)
                {
                    return traceNum;
                }
                Thread.sleep(1000);
            }
            catch (Exception exp)
            {
                //如果是重复插入异常，那么忽略
                if(exp instanceof DataIntegrityViolationException)
                {

                }
                else
                {
                    //TODO:异常处理
                }
            }
        }

        //TODO:到此说明重试也无法避免冲突，那么直接返回错误
        return null;
    }

}
