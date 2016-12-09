package com.chinadrtv.postprice.iagent.service;

import com.chinadrtv.postprice.dal.iagent.model.LogisticsFeeRuleDetail;
import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PostPriceService {
    //PostPriceItem processByWeigth(PostPriceItem item, LogisticsFeeRuleDetail lfrd);
    //PostPriceItem processByPrice(PostPriceItem item, LogisticsFeeRuleDetail lfrd);
    void calcPostprice(List<PostPriceItem> postPriceItemList);
}
