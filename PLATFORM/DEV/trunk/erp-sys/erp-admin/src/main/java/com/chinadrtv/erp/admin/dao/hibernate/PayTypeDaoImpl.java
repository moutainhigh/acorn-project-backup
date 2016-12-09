package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.PayTypeDao;
import com.chinadrtv.erp.admin.model.PayType;
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
public class PayTypeDaoImpl extends GenericDaoHibernate<PayType, String> implements PayTypeDao {

    public PayTypeDaoImpl() {
        super(PayType.class);
    }
    public List<PayType> getAllPayTypes()
    {
        Session session = getSession();
        Query query = session.createQuery("from PayType");
        return query.list();
    }
}
