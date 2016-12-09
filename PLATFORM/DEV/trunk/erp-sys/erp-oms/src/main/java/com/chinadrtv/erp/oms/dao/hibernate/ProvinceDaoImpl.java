package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dao.ProvinceDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 省份数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProvinceDaoImpl extends GenericDaoHibernate<Province, String> implements ProvinceDao {

    public ProvinceDaoImpl() {
        super(Province.class);
    }
    public List<Province> getAllProvinces()
    {
        Session session = getSession();
        Query query = session.createQuery("from Province");
        return query.list();
    }
}
