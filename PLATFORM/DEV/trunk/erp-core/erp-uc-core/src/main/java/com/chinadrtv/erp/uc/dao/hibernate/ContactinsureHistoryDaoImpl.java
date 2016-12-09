package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.Contactinsure;
import com.chinadrtv.erp.model.agent.ContactinsureHistory;
import com.chinadrtv.erp.uc.dao.ContactinsureDao;
import com.chinadrtv.erp.uc.dao.ContactinsureHistoryDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 14-6-12
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
@Repository("contactinsureHistoryDao")
public class ContactinsureHistoryDaoImpl extends GenericDaoHibernateBase<ContactinsureHistory, String> implements ContactinsureHistoryDao {
    public ContactinsureHistoryDaoImpl() {
        super(ContactinsureHistory.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
