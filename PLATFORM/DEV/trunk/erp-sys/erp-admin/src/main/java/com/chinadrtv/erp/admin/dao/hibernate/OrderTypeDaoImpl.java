package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.OrderTypeDao;
import com.chinadrtv.erp.admin.model.OrderType;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderTypeDaoImpl extends GenericDaoHibernate<OrderType, String> implements OrderTypeDao {

    public OrderTypeDaoImpl() {
        super(OrderType.class);
    }
    public List<OrderType> getAllOrderTypes()
    {
        Session session = getSession();
        Query query = session.createQuery("from OrderType");
        return query.list();
    }
}
