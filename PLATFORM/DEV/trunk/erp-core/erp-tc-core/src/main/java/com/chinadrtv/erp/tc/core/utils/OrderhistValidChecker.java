package com.chinadrtv.erp.tc.core.utils;

import java.math.BigDecimal;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * 订单有效性统一检查 (TC) 包括字段基本格式以及业务合法性.
 * User: 徐志凯
 * Date: 13-3-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderhistValidChecker {
    public static BigDecimal balancePrice=new BigDecimal("1");
    public static String checkOrderhist(Order orderhist)
    {
        if("0".equals(orderhist.getStatus()))
        {
            return null;
        }

         //基础检查
        if(orderhist.getCrusr()==null)
        {
            return "没有创建人";
        }
        if(orderhist.getStatus()==null)
        {
            return "没有设置状态";
        }
        if(orderhist.getAddress()==null)
        {
            return "没有地址信息";
        }
        if(orderhist.getOrderdets()==null||orderhist.getOrderdets().size()==0)
        {
            return "没有订单明细";
        }
        BigDecimal totalPrice=orderhist.getTotalprice();
        if(totalPrice==null)
        {
            totalPrice=BigDecimal.ZERO;
        }
        if(totalPrice.compareTo(BigDecimal.ZERO)<0)
        {
            return "订单总价小于零";
        }
        BigDecimal mailPrice=orderhist.getMailprice();
        if(mailPrice==null)
        {
            mailPrice=BigDecimal.ZERO;
        }
        if(mailPrice.compareTo(BigDecimal.ZERO)<0)
        {
            return "订单总运费小于零";
        }
        BigDecimal prodPrice=orderhist.getProdprice();
        if(prodPrice==null)
        {
            prodPrice=BigDecimal.ZERO;
        }
        if(prodPrice.compareTo(BigDecimal.ZERO)<0)
        {
            return "商品总价";
        }

        if(orderhist.getCrusr()==null)
        {
            return "没有创建人信息";
        }
        if(orderhist.getStatus()==null)
        {
            return "没有订单状态信息";
        }
        if(orderhist.getOrdertype()==null)
        {
            return "没有订单类型";
        }
        if(orderhist.getPaytype()==null)
        {
            return "没有付款方式";
        }

        BigDecimal orderdetPrice = BigDecimal.ZERO;

        for(OrderDetail orderdet:orderhist.getOrderdets())
        {
            BigDecimal orderdetUprice=orderdet.getUprice()!=null?orderdet.getUprice():new BigDecimal("0");
            BigDecimal orderdetUpnum=orderdet.getUpnum()!=null? new BigDecimal(orderdet.getUpnum()):new BigDecimal("0");
            BigDecimal orderdetSprice=orderdet.getSprice()!=null?orderdet.getSprice():new BigDecimal("0");
            BigDecimal orderdetSpnum=orderdet.getSpnum()!=null?new BigDecimal(orderdet.getSpnum()):new BigDecimal("0");

            if(orderdet.getStatus()==null)
            {
                return "没有明细状态信息";
            }

            if(orderdetSpnum.compareTo(BigDecimal.ZERO)<0||orderdetUpnum.compareTo(BigDecimal.ZERO)<0
                    ||orderdetSprice.compareTo(BigDecimal.ZERO)<0 || orderdetUprice.compareTo(BigDecimal.ZERO)<0)
            {
                return "订单明细里面的个数或者价格无效";
            }

            orderdetPrice=orderdetPrice.add(orderdetUprice.multiply(orderdetUpnum).add(orderdetSprice.multiply(orderdetSpnum)));
        }
        if(prodPrice.subtract(orderdetPrice).abs().compareTo(balancePrice)>0)
        {
            return "订单里面的价格与明细中的不符合";
        }

        //指定的承运商是否存在

        return null;
    }
}
