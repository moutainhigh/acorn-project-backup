package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderChannel;

import java.util.List;

/**
 * 订单渠道
 * User: gaodejian
 * Date: 13-3-25
 * To change this template use File | Settings | File Templates.
 */
public interface OrderChannelDao extends GenericDao<OrderChannel,Long> {
    List<OrderChannel> getAllOrderChannel();
}
