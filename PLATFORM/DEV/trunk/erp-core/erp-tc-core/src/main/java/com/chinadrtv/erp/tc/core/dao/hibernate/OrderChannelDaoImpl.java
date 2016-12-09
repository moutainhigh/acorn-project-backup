package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.OrderPayType;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.OrderChannelDao;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-7
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderChannelDaoImpl extends GenericDaoHibernateBase<OrderChannel,Long> implements OrderChannelDao {
    public OrderChannelDaoImpl()
    {
        super(OrderChannel.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @ReadThroughAssignCache(namespace= CacheNames.CACHE_ORDERCHANNEL, assignedKey = CacheNames.All, expiration=3000)
    public List<OrderChannel> getAllOrderChannel()
    {
        List<OrderChannel> orderChannelList=this.getAll();
        if(orderChannelList==null)
            return new ArrayList<OrderChannel>();
        else
            return orderChannelList;
    }

    @ReadThroughSingleCache(namespace= CacheNames.CACHE_ORDERCHANNEL, expiration=3000)
    public List<Long> getChannelIdsFromOrderPayType(@ParameterValueKeyProvider Long orderTypeId,@ParameterValueKeyProvider( order = 1) Long payTypeId)
    {
        List<Long> channelIdList=new ArrayList<Long>();
        Query query = this.getSession().createQuery("from OrderPayType where orderTypeId=:orderTypeId and payTypeId=:payTypeId");
        query.setParameter("orderTypeId",orderTypeId);
        query.setParameter("payTypeId", payTypeId);
        List<OrderPayType> orderPayTypeList=query.list();
        if(orderPayTypeList!=null)
        {
            for(OrderPayType orderPayType: orderPayTypeList)
            {
                if(orderPayType.getOrderChannel()!=null&&orderPayType.getOrderChannel().getId()!=null)
                {
                    if(!channelIdList.contains(orderPayType.getOrderChannel().getId()))
                    {
                        channelIdList.add(orderPayType.getOrderChannel().getId());
                    }
                }
            }
        }
        return channelIdList;
    }
}
