package com.chinadrtv.taobao.biz;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderBizHandler {
    boolean input(List<TaobaoOrderConfig> taobaoOrderConfigList, Date startDate,Date endDate);
    boolean feedback(List<TaobaoOrderConfig> taobaoOrderConfigList);
}
