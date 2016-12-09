package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.uc.dao.CityDao;

/**
 * 城市数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CityDaoImpl extends GenericDaoHibernate<CityAll, Integer> implements CityDao {

	public CityDaoImpl() {
		super(CityAll.class);
	}

	public List<CityAll> getAllCities() {
		Session session = getSession();
		session.clear();
		Query query = session.createQuery("from CityAll where  cityid>0");
		return query.list();
	}

	public List<CityAll> getCities(String provinceId) {
		Session session = getSession();
		Query query = session.createQuery("from CityAll c where c.provid = '" + provinceId + "'");
		return query.list();
	}

    @Override
    public CityAll getByAreaCode(String areacode) {
        Session session = getSession();
        Query query = session.createQuery("from CityAll c where c.areacode = :areacode");
        query.setString("areacode", areacode);
        List<CityAll> list = query.list();
        return list.size()>0?list.get(0):new CityAll();
    }

}
