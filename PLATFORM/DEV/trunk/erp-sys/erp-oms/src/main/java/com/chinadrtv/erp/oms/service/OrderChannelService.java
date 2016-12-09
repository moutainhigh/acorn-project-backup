package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.OrderPayType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
public interface OrderChannelService extends GenericService<OrderChannel,Long> {
    List<OrderChannel> getOrderChannelFromOrderPayType(OrderPayType orderPayType);
    boolean isExistOrderType(Long orderType);
    boolean isExistPayType(Long payType);
    
    /**
     * 获取所有渠道
    * @Description: 
    * @return
    * @return List<OrderChannel>
    * @throws
     */
    List<OrderChannel> getAllOrderChannel();
    OrderChannel findById(Long warehosueId);
}
