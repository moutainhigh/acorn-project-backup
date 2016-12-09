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
public class FreightVersionDaoImpl extends GenericDaoHibernate<FreightVersion, Long> implements FreightVersionDao {

    public FreightVersionDaoImpl() {
        super(FreightVersion.class);
    }

    public List<FreightVersion> getFreightVersions(String companyId)
    {
        Session session = getSession();
        Query query = session.createQuery("from FreightVersion c where c.company.companyId ='"+companyId+"'");
        return query.list();
    }
}
