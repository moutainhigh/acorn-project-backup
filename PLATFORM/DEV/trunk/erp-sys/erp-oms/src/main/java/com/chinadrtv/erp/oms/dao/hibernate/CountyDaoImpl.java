package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.oms.dao.CountyDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 城市数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CountyDaoImpl extends GenericDaoHibernate<CountyAll, Long> implements CountyDao {

    public CountyDaoImpl() {
        super(CountyAll.class);
    }

    public List<CountyAll> getAllCounties()
    {
        Session session = getSession();
        Query query = session.createQuery("from CountyAll");
        return query.list();
    }

    public  List<CountyAll> getCounties(Long cityid)
    {
        Session session = getSession();
        Query query = session.createQuery("from CountyAll c where c.cityid = "+cityid);
        return query.list();
    }

}
