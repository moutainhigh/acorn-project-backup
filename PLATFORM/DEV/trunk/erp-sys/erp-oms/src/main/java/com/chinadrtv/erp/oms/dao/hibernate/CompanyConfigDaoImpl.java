package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.CompanyConfig;
import com.chinadrtv.erp.oms.dao.CompanyConfigDao;

@Repository
public class CompanyConfigDaoImpl extends GenericDaoHibernate<CompanyConfig, java.lang.String> implements CompanyConfigDao{

	public CompanyConfigDaoImpl() {
	    super(CompanyConfig.class);
	}


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.dao.CompanyDao#getCompanyByID(java.lang.String)
	 */
	public CompanyConfig getCompanyConfigByID(String id) {
		  Session session = getSession();
		  String sqlString = "from CompanyConfig a where  a.companyId = :companyId";
		  Query query = session.createQuery(sqlString);
		  query.setString("companyId", id);
		  List list = query.list();
		    return list.size()>0? (CompanyConfig)list.get(0):null;
	}


	

}
