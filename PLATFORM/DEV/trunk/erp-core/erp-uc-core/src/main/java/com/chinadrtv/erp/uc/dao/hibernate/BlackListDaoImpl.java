package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.uc.dao.BlackListDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Repository
public class BlackListDaoImpl extends GenericDaoHibernateBase<BlackList, Long>
        implements BlackListDao {

    public BlackListDaoImpl() {
        super(BlackList.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @Override
    public boolean isSecondUserAdd(BlackList blackList) {
        String hql = "select b from BlackList b where b.phoneId=:phoneId and b.createUser<>:userId";
        Query query = this.getSession().createQuery(hql.toString());
        query.setLong("phoneId", blackList.getPhoneId());
        query.setString("userId", blackList.getCreateUser());
        List list = query.list();
        return list != null && list.size() > 0;

    }

    @Override
    public BlackList findByLeadInteractionId(String leadInteractionId) {
        String hql = "select b from BlackList b where b.leadInteractionId=:leadInteractionId";
        Query query = this.getSession().createQuery(hql.toString());
        query.setLong("leadInteractionId", Long.valueOf(leadInteractionId));
        List<BlackList> list = query.list();
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<BlackList> findByBlackPhoneId(Long blackPhoneId) {
        String hql = "select b from BlackList b where b.active = 1 and b.blackPhoneId=:blackPhoneId";
        Query query = this.getSession().createQuery(hql.toString());
        query.setLong("blackPhoneId", blackPhoneId);
        return query.list();
    }
}
