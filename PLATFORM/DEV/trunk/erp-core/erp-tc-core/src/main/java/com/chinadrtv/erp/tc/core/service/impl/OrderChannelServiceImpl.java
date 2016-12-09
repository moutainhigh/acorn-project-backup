package com.chinadrtv.erp.tc.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.OrderPayType;
import com.chinadrtv.erp.tc.core.dao.OrderChannelDao;
import com.chinadrtv.erp.tc.core.service.OrderChannelService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderChannelServiceImpl extends GenericServiceImpl<OrderChannel,Long> implements OrderChannelService {

    @Autowired
    private OrderChannelDao orderChannelDao;

    @Override
    protected GenericDao<OrderChannel,Long> getGenericDao(){
        return orderChannelDao;
    }

    public  List<OrderChannel> getOrderChannelFromOrderPayType(OrderPayType orderPayType)
    {
        List<OrderChannel> orderChannelList = orderChannelDao.getAllOrderChannel();
        List<OrderChannel> matchOrderChannelList=new ArrayList<OrderChannel>();
        for(OrderChannel orderChannel : orderChannelList)
        {
            for(OrderPayType orderPayTypeItem: orderChannel.getOrderPayTypes())
                if(orderPayTypeItem.getOrderTypeId().equals(orderPayType.getOrderTypeId())
                        &&orderPayTypeItem.getPayTypeId().equals(orderPayType.getPayTypeId()))
                {
                    matchOrderChannelList.add(orderChannel);
                    break;
                }
        }
        return matchOrderChannelList;
    }

    public boolean isExistOrderType(Long orderType)
    {
        List<OrderChannel> orderChannelList = orderChannelDao.getAllOrderChannel();
        for(OrderChannel orderChannel:orderChannelList)
        {
            for(OrderPayType orderPayType:orderChannel.getOrderPayTypes())
            {
                if(orderType.equals(orderPayType.getOrderTypeId()))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isExistPayType(Long payType)
    {
        List<OrderChannel> orderChannelList = orderChannelDao.getAllOrderChannel();
        for(OrderChannel orderChannel:orderChannelList)
        {
            for(OrderPayType orderPayType:orderChannel.getOrderPayTypes())
            {
                if(payType.equals(orderPayType.getPayTypeId()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Long> getChannelIdsFromOrderPayType(Long orderTypeId, Long payTypeId)
    {
        //是根据 a: orderTypeId和payTypeId来缓存，还是根据 b:channelId来缓存？？？
        //目前根据方案a来缓存
        return orderChannelDao.getChannelIdsFromOrderPayType(orderTypeId,payTypeId);
    }
}
