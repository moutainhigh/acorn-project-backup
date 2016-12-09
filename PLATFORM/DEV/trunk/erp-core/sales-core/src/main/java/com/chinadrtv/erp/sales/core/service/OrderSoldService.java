package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.sales.core.model.OrderSoldDto;

import java.util.List;

/**
 * 已售订单统计
 * User: gaodejian
 * Date: 13-7-5
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
public interface OrderSoldService {
    /**
     * 获取关注20条
     * @return
     */
    List<OrderSoldDto> getTop20Favorites(String userId);
    /**
     * 缓存失效
     */
    void updateTop20Favorites(String userId);
    /**
     * 获取销售最好20条
     * @return
     */
    List<OrderSoldDto> getTop20Sales(String userId);
    /**
     * 缓存失效
     */
    void updateTop20Sales(String userId);
}
