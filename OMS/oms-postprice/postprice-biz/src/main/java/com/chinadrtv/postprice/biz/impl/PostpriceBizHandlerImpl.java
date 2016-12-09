package com.chinadrtv.postprice.biz.impl;

import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.iagent.service.PostPriceFetchService;
import com.chinadrtv.postprice.iagent.service.PostPriceService;
import com.chinadrtv.postprice.biz.PostpriceBizHandler;
import com.chinadrtv.postprice.util.SplitUtil;
import com.chinadrtv.postprice.wms.service.ShipmentWeightService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PostpriceBizHandlerImpl implements PostpriceBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PostpriceBizHandlerImpl.class);

    private Integer batchSize=100;

    @Autowired
    private PostPriceFetchService postPriceFetchService;
    @Autowired
    private PostPriceService postPriceService;
    @Autowired
    private ShipmentWeightService shipmentWeightService;

    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Override
    public boolean calcOrderPostprice() {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            logger.info("begin post price calc");
            try
            {
            List<PostPriceItem> postPriceItemList = postPriceFetchService.fetchOrders();
            if(postPriceItemList==null||postPriceItemList.size()==0)
                return true;
            shipmentWeightService.fetchOrderWeights(postPriceItemList);
            //计算运费
            postPriceService.calcPostprice(postPriceItemList);
            //保存
            List<List<PostPriceItem>> allList= SplitUtil.split(postPriceItemList,batchSize);
            for(List<PostPriceItem> itemList:allList)
                postPriceFetchService.saveOrderPostPrice(itemList);
            }catch (Exception exp)
            {
                logger.error("post price calc error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun.set(false);
            }
            logger.info("end post price calc");
            return true;
        }
        else
        {
            return false;
        }
    }
}
