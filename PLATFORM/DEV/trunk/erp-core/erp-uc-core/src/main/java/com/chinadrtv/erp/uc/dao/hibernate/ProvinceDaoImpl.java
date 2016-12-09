package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.uc.dao.ProvinceDao;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:24:13
 * 
 */
@Repository
public class ProvinceDaoImpl extends GenericDaoHibernate<Province, String> implements ProvinceDao {

	public ProvinceDaoImpl() {
		super(Province.class);
	}

	public List<Province> getAllProvinces() {
		Session session = getSession();
		Query query = session.createQuery("from Province p  where p.provinceid != '32'");
		return query.list();
	}
}
