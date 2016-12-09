package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ProductGrpLimitDao;
import com.chinadrtv.erp.model.inventory.ScmCloseShipment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品档案
 * User: gdj
 * Date: 13-5-14
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProductGrpLimitDaoImpl implements ProductGrpLimitDao {

    private HibernateTemplate hibernateTemplate;

    public Boolean isValidPrice(String grpId, String prodId, Double lpPrice){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if(session != null){
            Query q = session
                    .createQuery("from ProductGrpLimit a where a.grpId = :grpId and a.prodId=:prodId and a.lpPrice<=:lpPrice " +
                            " and a.status='-1' and a.startDate <= current_timestamp() and a.endDate >= current_timestamp() ");
            q.setParameter("grpId", grpId);
            q.setParameter("prodId", prodId);
            q.setParameter("lpPrice", lpPrice);
            return q.list().size() > 0;
        }
        return false;
    }

    public Double getLpPrice(String grpId, String prodId){
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if(session != null){
            Query q = session
                    .createQuery("select a.lpPrice from ProductGrpLimit a where a.grpId = :grpId and a.prodId=:prodId" +
                            " and a.status='-1' and a.startDate <= current_timestamp() and a.endDate >= current_timestamp()");
            q.setParameter("grpId", grpId);
            q.setParameter("prodId", prodId);
            List list = q.list();
            return list.size() > 0 ? (Double)list.get(0) : null;
        }
        return null;
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
}
