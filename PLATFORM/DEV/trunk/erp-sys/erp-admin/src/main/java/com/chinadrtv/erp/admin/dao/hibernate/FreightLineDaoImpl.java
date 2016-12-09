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
public class FreightLineDaoImpl extends GenericDaoHibernate<FreightLine, Long> implements FreightLineDao {

    public FreightLineDaoImpl() {
        super(FreightLine.class);
    }

    public List<FreightLine> getFreightLines(String companyId)
    {
        Session session = getSession();
        Query query = session.createQuery("from FreightLine c where c.company.companyId ='"+companyId+"'");
        return query.list();
    }

    public List<FreightLine> getLegFreightLines(Long legId)
    {
        Session session = getSession();
        Query query = session.createQuery("from FreightLine c where c.leg.id ="+legId);
        return query.list();
    }

    public List<FreightLine> getQuotaFreightLines(Long quotaId)
    {
        Session session = getSession();
        Query query = session.createQuery("from FreightLine c where c.quota.id ="+quotaId);
        return query.list();
    }

    public List<FreightLine> getVersionFreightLines(Long versionId)
    {
        Session session = getSession();
        Query query = session.createQuery("from FreightLine c where c.version.id ="+versionId);
        return query.list();
    }
}
