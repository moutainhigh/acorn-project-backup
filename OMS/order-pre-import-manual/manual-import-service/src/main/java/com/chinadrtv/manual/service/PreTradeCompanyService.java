package com.chinadrtv.manual.service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-5
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public interface PreTradeCompanyService {
    /**
     * 根据订单类型获取订单来源
     * @param tradeType  订单类型
     * @return 订单来源ID
     */
    long getPreTradeSourceId(String tradeType);
}
