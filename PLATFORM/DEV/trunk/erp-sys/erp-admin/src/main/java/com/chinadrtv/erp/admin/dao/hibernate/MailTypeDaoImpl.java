package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MailTypeDaoImpl extends GenericDaoHibernate<MailType, String> implements MailTypeDao {

    public MailTypeDaoImpl() {
        super(MailType.class);
    }

    public MailType getNativeById(String id) {
        Session session = getSession();
        session.clear(); //清楚一级缓存(BaseType的Id数据不是唯一)
        return (MailType)session.get(MailType.class, id);
    }

    public List<MailType> getAllMailTypes()
    {
        Session session = getSession();
        Query query = session.createQuery("from MailType");
        return query.list();
    }
}
