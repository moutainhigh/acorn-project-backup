package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.oms.dao.ChannelDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-20
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
@Repository("channelDao")
public class ChannelDaoImpl extends GenericDaoHibernate<OrderChannel,Long> implements ChannelDao {
    public ChannelDaoImpl()
    {
        super(OrderChannel.class);
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<OrderChannel> getAllChannel() {
        Query query = getSession().createQuery(" from OrderChannel");

        return query.list();
    }

    /**
     * 根据channelid查询对象
     * @param id
     * @return
     */
    public OrderChannel getChannelByChannelId(Long id) {
        Query query = getSession().createQuery(" from OrderChannel a where a.id = :Id");
        query.setParameter("Id",id);
        return (OrderChannel)query.uniqueResult();
    }
}
