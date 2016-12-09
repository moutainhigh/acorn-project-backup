package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;
import com.chinadrtv.erp.pay.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.core.dao.NamesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("MerchantsCreditcardTracenumServiceImpl")
public class MerchantsCreditcardTracenumServiceImpl implements MerchantsCreditcardTracenumService {
    @Value("${com.chinadrtv.erp.sales.core.traceNumTID}")
    private String traceNumTID;
    @Value("${com.chinadrtv.erp.sales.core.traceNumID}")
    private String traceNumID;

    @Autowired
    private NamesDao namesDao;

    @Transactional( propagation = Propagation.REQUIRES_NEW)
    public Integer calcTraceNum(String currentBatchNum)
    {
        //names表，tdsc存放日期、dsc存放计数器
        //首先取出数据，如果没有，那么新增(新增的同时，注意并发)
        //如果存在， a.如果是当天的，那么加锁，更新+1  b.重置计数和日期
        Names traceNumInfo = namesDao.getNamesByTidAndId(traceNumTID, traceNumID);
        if(traceNumInfo == null)
        {
            traceNumInfo=new Names();
            traceNumInfo.setDsc("1");
            traceNumInfo.setTdsc(currentBatchNum);
            traceNumInfo.setValid("-1");
            NamesId namesId = new NamesId(traceNumTID, traceNumID);
            traceNumInfo.setId(namesId);
            namesDao.addNames(traceNumInfo);
            return 1;
        }
        else
        {
            //解决多个服务器时间不同步问题(需要判断日期大小，而不能简单的比较字符串)
            Date localDate=getDateFromString(currentBatchNum);
            Date dbDate=getDateFromString(traceNumInfo.getTdsc());
            if(dbDate.compareTo(localDate)>=0)
            {
                Integer traceNum=Integer.parseInt(traceNumInfo.getDsc());
                Integer newTraceNum=traceNum+1;
                int num=namesDao.execUpdate("update Names n set n.dsc=:dsc where n.id.tid=:tid and n.id.id=:id and n.dsc=:oldDsc and n.tdsc=:tdsc",
                        new ParameterString("dsc",newTraceNum.toString()), new ParameterString("tid",traceNumTID),new ParameterString("id",traceNumID),new ParameterString("oldDsc",traceNum.toString()),new ParameterString("tdsc",traceNumInfo.getTdsc()));
                if(num==0)
                {
                    //说明已经发生改变，需要重新读取
                    return null;
                }
                return newTraceNum;
            }
            else
            {
                int num=namesDao.execUpdate("update Names n set n.dsc=:dsc,n.tdsc=:tdsc where n.id.tid=:tid and n.id.id=:id and n.dsc=:oldDsc and n.tdsc=:oldTdsc",
                        new ParameterString("dsc","1"), new ParameterString("tid",traceNumTID),new ParameterString("id",traceNumID),new ParameterString("oldDsc",traceNumInfo.getDsc()),new ParameterString("tdsc",currentBatchNum), new ParameterString("oldTdsc",traceNumInfo.getTdsc()));
                if(num==0)
                {
                    //说明已经发生改变，需要重新读取
                    return null;
                }
                return 1;
            }
        }
    }

    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMdd");
    private Date getDateFromString(String strDate)
    {
        try
        {
            return simpleDateFormat.parse(strDate);
        }catch (Exception exp)
        {

        }
        return null;
    }
}
