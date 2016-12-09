package com.chinadrtv.postprice.iagent.service.impl;

import com.chinadrtv.postprice.dal.iagent.dao.ShipmentPriceDao;
import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.iagent.service.PostPriceFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PostPriceFetchServiceImpl implements PostPriceFetchService {

    @Value("${env_postprice_fetch_size}")
    private Integer batchSize;

    /*@Value("env_postprice_fetch_size")
    public void setBatchSize(String size)
    {
        batchSize=Integer.parseInt(size);
    }*/


    @Autowired
    private ShipmentPriceDao shipmentPriceDao;

    @Override
    public List<PostPriceItem> fetchOrders() {
        return shipmentPriceDao.findPostPriceShipments(batchSize);
    }

    @Override
    public void saveOrderPostPrice(List<PostPriceItem> postPriceItemList) {
        for(PostPriceItem postPriceItem:postPriceItemList)
            shipmentPriceDao.updatePostPrice(postPriceItem);
    }
}
