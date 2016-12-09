package com.chinadrtv.erp.sales.job;

import com.chinadrtv.erp.sales.core.service.OrderSoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-22
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
public class OrderSoldJob {

    private static final Logger logger = LoggerFactory.getLogger(OrderSoldJob.class);

    private OrderSoldService orderSoldService;

    public void executeInternal( ) {
        logger.warn("Top 20 sales executing");
        List list = orderSoldService.getTop20Sales("0");
        logger.warn("Top 20 sales executed[" + list.size() +"]");
    }

    public OrderSoldService getOrderSoldService() {
        return orderSoldService;
    }

    public void setOrderSoldService(OrderSoldService orderSoldService) {
        this.orderSoldService = orderSoldService;
    }
}
