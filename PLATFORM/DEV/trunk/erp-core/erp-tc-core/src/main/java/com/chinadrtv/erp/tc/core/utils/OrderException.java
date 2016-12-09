package com.chinadrtv.erp.tc.core.utils;

import com.chinadrtv.erp.tc.core.model.OrderReturnCode;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-1-29
 */
public class OrderException extends RuntimeException {
    public OrderException(OrderReturnCode orderReturnCode)
    {
        super(orderReturnCode!=null?orderReturnCode.getDesc():null);
        this.orderReturnCode=orderReturnCode;
    }

    public OrderReturnCode getOrderReturnCode() {
        return orderReturnCode;
    }

    public void setOrderReturnCode(OrderReturnCode orderReturnCode) {
        this.orderReturnCode = orderReturnCode;
    }

    private OrderReturnCode orderReturnCode;

}
