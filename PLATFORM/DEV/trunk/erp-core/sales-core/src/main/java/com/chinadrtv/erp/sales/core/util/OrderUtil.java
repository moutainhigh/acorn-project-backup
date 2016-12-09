package com.chinadrtv.erp.sales.core.util;

import com.chinadrtv.erp.model.agent.OrderDetail;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public abstract class OrderUtil {
    public static BigDecimal getProdPrice(OrderDetail orderDetail)
    {
        if(orderDetail.getSpnum()!=null&&orderDetail.getSpnum().intValue()>0
                &&orderDetail.getSprice()!=null)
        {
            return orderDetail.getSprice();
        }
        if(orderDetail.getUpnum()!=null&&orderDetail.getUpnum().intValue()>0
                &&orderDetail.getUprice()!=null)
            return orderDetail.getUprice();

        return BigDecimal.ZERO;
    }
}
