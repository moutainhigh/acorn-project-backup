package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.oms.dao.PlubasInfoDao;

/**
 * 产品档案
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01 To change this template use
 * File | Settings | File Templates.
 */
@Repository
public class PlubasInfoDaoImpl extends
		GenericDaoHibernateBase<PlubasInfo, Long> implements PlubasInfoDao {

	public PlubasInfoDaoImpl() {
		super(PlubasInfo.class);
	}

	public PlubasInfo getPlubasInfo(String plucode) {
		Session session = getSession();
		Query q = session
				.createQuery("from PlubasInfo a where a.plucode = :plucode");
		q.setString("plucode", plucode);
		List result = q.list();
		return result.size() > 0 ? (PlubasInfo) result.get(0) : null;
	}

	public PlubasInfo getPlubasInfoByplid(Long ruid) {
		Session session = getSession();
		Query q = session.createQuery("from PlubasInfo a where a.ruid = :ruid");
		q.setLong("ruid", ruid);
		List result = q.list();
		return result.size() > 0 ? (PlubasInfo) result.get(0) : null;

	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

}
