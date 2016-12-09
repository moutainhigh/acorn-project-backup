package com.chinadrtv.postprice.test;

import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.wms.service.ShipmentWeightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration(defaultRollback =false)
@Transactional("wmsTransactionManager")
public class WmsShipmentServiceTest {

    @Autowired
    private ShipmentWeightService shipmentWeightService;

    @Test
    public void testWeightService()
    {
        List<PostPriceItem> postPriceItemList=new ArrayList<PostPriceItem>();
        PostPriceItem priceItem=new PostPriceItem();
        priceItem.setOrderId("965106292");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        /*priceItem=new PostPriceItem();
        priceItem.setOrderId("17272639");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17256296");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17319779");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17323172");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17331923");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17343618");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17352513");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17353633");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17354196");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17355637");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17355977");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);

        priceItem=new PostPriceItem();
        priceItem.setOrderId("17356348");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem); */

        priceItem=new PostPriceItem();
        priceItem.setOrderId("989242244");
        priceItem.setOrderRefRevision(1);
        postPriceItemList.add(priceItem);


        shipmentWeightService.fetchOrderWeights(postPriceItemList);

        System.out.println(postPriceItemList.size());
    }
}
