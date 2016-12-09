package com.chinadrtv.erp.sales.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.sales.dao.AddressExtendDao;

@Repository
public class AddressExendDaoImpl implements AddressExtendDao {
	
	private HibernateTemplate hibernateTemplate;

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
	
	@Override
	public Province getProvinceById(final String provinceId) {
		return hibernateTemplate.execute(new HibernateCallback<Province>() {
			@Override
			public Province doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Province.class);
				criteria.add(Restrictions.eq("provinceid", provinceId));
				return (Province) criteria.uniqueResult();
			}
		});
	}

	@Override
	public CityAll getCityById(final String cityId) {
		return hibernateTemplate.execute(new HibernateCallback<CityAll>() {
			@Override
			public CityAll doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(CityAll.class);
				criteria.add(Restrictions.eq("cityid", Integer.valueOf(cityId)));
				return (CityAll) criteria.uniqueResult();
			}
		});
	}

	@Override
	public CountyAll getCountyById(final String countyId) {
		return hibernateTemplate.execute(new HibernateCallback<CountyAll>() {
			@Override
			public CountyAll doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(CountyAll.class);
				criteria.add(Restrictions.eq("countyid", Integer.valueOf(countyId)));
				return (CountyAll) criteria.uniqueResult();
			}
		});
	}

	@Override
	public AreaAll getAreaById(final String areaId) {
		return hibernateTemplate.execute(new HibernateCallback<AreaAll>() {
			@Override
			public AreaAll doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(AreaAll.class);
				criteria.add(Restrictions.eq("areaid", Integer.valueOf(areaId)));
				return (AreaAll) criteria.uniqueResult();
			}
		});
	}

}
