package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.sales.core.util.OrderUtil;
import com.chinadrtv.erp.uc.util.ShoppingCartProductValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("SpecialPriceRuleCheckServiceImpl")
public class SpecialPriceRuleCheckServiceImpl implements OrderRuleCheckService {

    @Autowired
    private ShoppingCartProductValidate shoppingCartProductValidate;

    @Override
    public boolean checkOrder(Order order) {
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            if(!"3".equals(orderDetail.getSoldwith()))
            {
                //
                try{
                    //找到数量不是0的价格
                    //if((orderDetail.getSprice()==null||(orderDetail.getSprice()!=null&&orderDetail.getSprice().compareTo(BigDecimal.ZERO)==0))
                    //        &&(orderDetail.getUprice()==null||(orderDetail.getUprice()!=null&&orderDetail.getUprice().compareTo(BigDecimal.ZERO)==0)))
                    {
                        if(shoppingCartProductValidate.specialPriceValidate(orderDetail,order.getCrusr()))
                        //if(shoppingCartProductValidate.specialPriceValidate(orderDetail.getProdid(), OrderUtil.getProdPrice(orderDetail),order.getCrusr()))
                            return false;
                    }
                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public String getRuleName() {
        //TODO:
        return "gift check";
    }

    @Override
    public String getBPMType() {
        return String.valueOf(UserBpmTaskType.ORDER_CART_CHANGE.getIndex());
    }
}
