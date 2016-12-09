package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderChannel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-20
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelDao extends GenericDao<OrderChannel,Long> {
    //查询渠道
    public List<OrderChannel> getAllChannel();
    //根据id查询
    public OrderChannel getChannelByChannelId(Long id);
}
