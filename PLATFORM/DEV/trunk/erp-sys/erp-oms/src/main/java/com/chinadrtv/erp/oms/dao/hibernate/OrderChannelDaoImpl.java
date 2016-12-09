package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.oms.dao.OrderChannelDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单渠道
 * User: gaodejian
 * Date: 13-3-25
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderChannelDaoImpl extends GenericDaoHibernate<OrderChannel,Long> implements OrderChannelDao {
    public OrderChannelDaoImpl()
    {
        super(OrderChannel.class);
    }

    public List<OrderChannel> getAllOrderChannel()
    {
        List<OrderChannel> orderChannelList=this.getAll();
        if(orderChannelList!=null)  {
            return orderChannelList;
        }
        return new ArrayList<OrderChannel>();
    }
}
