package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderChannel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-7
 * To change this template use File | Settings | File Templates.
 */
public interface OrderChannelDao extends GenericDao<OrderChannel,Long> {
    List<OrderChannel> getAllOrderChannel();
    List<Long> getChannelIdsFromOrderPayType(Long orderTypeId,Long payTypeId);
}
