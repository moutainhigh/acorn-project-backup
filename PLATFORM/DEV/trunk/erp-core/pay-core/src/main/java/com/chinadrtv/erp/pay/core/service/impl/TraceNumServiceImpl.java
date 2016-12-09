package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.pay.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.pay.core.service.TraceNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class TraceNumServiceImpl implements TraceNumService {
    @Autowired
    @Qualifier("MerchantsCreditcardTracenumServiceImpl")
    private MerchantsCreditcardTracenumService merchantsCreditcardTracenumService;

    public Integer getTraceNum() {
        String batchNum=this.getCurrentBatchNum();
        return fetchTraceNum(batchNum);
    }

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

        //到此说明重试也无法避免冲突，那么直接返回错误
        return null;
    }


    private SimpleDateFormat batchNumDateFormat=new SimpleDateFormat("yyMMdd");

    private synchronized String getCurrentBatchNum()
    {
        return batchNumDateFormat.format(new Date());
    }
}
