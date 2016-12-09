package com.chinadrtv.postprice.test;

import com.chinadrtv.postprice.dal.iagent.dao.LogisticsFeeRuleDetailDao;
import com.chinadrtv.postprice.dal.iagent.model.LogisticsFeeRuleDetail;
import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.iagent.service.PostPriceFetchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
@Transactional("iagentTransactionManager")
public class PostpriceServiceTest {

    @Autowired
    private LogisticsFeeRuleDetailDao logisticsFeeRuleDetailDao;

    @Autowired
    private PostPriceFetchService postPriceFetchService;

    //@Test
    public void testRuleDetail()
    {
        PostPriceItem priceItem=new PostPriceItem();
        priceItem.setWeight(new BigDecimal("10"));
        priceItem.setAccountStatusId("5");
        priceItem.setEntityId(262L);
        priceItem.setOrderId("928409242");
        priceItem.setOrderRefHisId(1L);
        priceItem.setOrderRefRevision(1);
        priceItem.setTotlePrice(new BigDecimal("100"));
        LogisticsFeeRuleDetail logisticsFeeRuleDetail= logisticsFeeRuleDetailDao.matchLogisticsFeeRuleDetail(priceItem);
        if(logisticsFeeRuleDetail!=null)
        {
            System.out.println(logisticsFeeRuleDetail.getId());
        }
    }

    @Test
    public void testReadService()
    {
        List<PostPriceItem> postPriceItemList =  postPriceFetchService.fetchOrders();
        System.out.println(postPriceItemList.size());
    }

}
