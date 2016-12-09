package com.chinadrtv.erp.tc.core.utils;

import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检查订单地址有效性 (TC).
 * 后期是否直接在订单的pojo中添加相应方法？
 * User: 徐志凯
 * Date: 13-2-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public abstract class OrderAddressChecker {

    private static Logger logger  = LoggerFactory.getLogger(OrderAddressChecker.class);

    public static boolean isValidAddress(Order orderhist) {
        try{
        if (orderhist.getAddress() == null)
            return false;
            return isJustValidAddress(orderhist.getAddress());
        }catch (Exception exp)
        {
            if(orderhist!=null)
                logger.error("order address is invalid orderid:"+orderhist.getOrderid(), exp);
            else
                logger.error("order address is invalid - order is null");
            return false;
        }
    }

    public static boolean isJustValidAddress(AddressExt addressExt) {
        if (addressExt.getArea() == null || addressExt.getArea().getAreaid() == null || addressExt.getArea().getAreaid().intValue() < 0)
            return false;
        if (addressExt.getCity() == null || addressExt.getCity().getCityid() == null || addressExt.getArea().getCityid() < 0)
            return false;
        if (addressExt.getCounty() == null || addressExt.getCounty().getCountyid() == null || addressExt.getCounty().getCountyid().intValue() < 0)
            return false;
        if (addressExt.getProvince() == null || addressExt.getProvince().getProvinceid() == null)
            return false;

        return true;
    }
}
