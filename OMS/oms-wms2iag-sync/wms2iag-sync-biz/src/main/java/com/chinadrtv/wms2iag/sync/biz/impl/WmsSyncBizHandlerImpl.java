package com.chinadrtv.wms2iag.sync.biz.impl;

import com.chinadrtv.wms2iag.sync.biz.TimeLogger;
import com.chinadrtv.wms2iag.sync.biz.WmsSyncBizHandler;
import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.iagent.service.OrderSyncService;
import com.chinadrtv.wms2iag.sync.wms.service.OrderReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WmsSyncBizHandlerImpl implements WmsSyncBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WmsSyncBizHandlerImpl.class);

    @Autowired
    private OrderSyncService orderSyncService;
    @Autowired
    private OrderReadService orderReadService;

    @Value("${env_sync_batch_size}")
    private Integer syncBatchSize;

    @Autowired
    @Qualifier("timeLogger")
    private TimeLogger timeLogger;

    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Override
    public boolean wmsSync(Date startDate) {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            logger.info("begin wms sync");
            try
            {
                //startDate=simpleDateFormat.parse("2013-07-01 10:00:00");
                List<OrderExt> orderExtList = orderReadService.readOrders(startDate);
                if(orderExtList!=null&&orderExtList.size()>0)
                {
                    Date lastTime=startDate;
                    //分批同步
                    List<List<OrderExt>> allList=splitList(orderExtList);
                    for(List<OrderExt> itemList:allList)
                    {
                        orderSyncService.syncOrders(itemList);
                        Date dtTmp=itemList.get(itemList.size()-1).getUpDate();
                        if(lastTime.compareTo(dtTmp)<0)
                        {
                            lastTime=dtTmp;
                        }
                        //记录时间
                        timeLogger.WriteToFile(lastTime);
                    }
                }
            }
            catch (Exception exp)
            {
                logger.error("record last time error:",exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun.set(false);
                logger.info("end wms sync");
            }
            return true;
        }
        else
        {
            logger.error("wms sync is running!!!");
            return false;
        }
    }

    private List<List<OrderExt>> splitList(List<OrderExt> list)
    {
        List<List<OrderExt>> listList=new ArrayList<List<OrderExt>>();

        int count=list.size() / syncBatchSize ;
        int lessCount=list.size()%syncBatchSize;

        for(int i=0;i<count;i++)
        {
            List itemList=new ArrayList();
            for(int j=0;j<syncBatchSize;j++)
            {
                itemList.add(list.get(i*syncBatchSize+j));
            }
            listList.add(itemList);

        }
        if(lessCount>0)
        {
            List itemList=new ArrayList();
            for(int j=count*syncBatchSize;j<list.size();j++)
            {
                itemList.add(list.get(j));
            }
            listList.add(itemList);
        }

        return listList;
    }
}
