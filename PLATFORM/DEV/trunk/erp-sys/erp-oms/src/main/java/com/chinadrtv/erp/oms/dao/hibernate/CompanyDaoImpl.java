package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.oms.dao.CompanyDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CompanyDaoImpl
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:51:34
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class CompanyDaoImpl extends GenericDaoHibernate<Company, java.lang.String> implements CompanyDao{

	public CompanyDaoImpl() {
	    super(Company.class);
	}

	
	    public List<Company> getAllCompany()
    {
        Session session = getSession();
        Query query = session.createQuery("from Company");
        return query.list();
    }
    public List<Company> getAllCompany(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Company");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }

    public List<Company> getWarehouseCompanies(Long warehouseId){
        Session session = getSession();
        Query query = session.createQuery("from Company a where a.warehouseId = :warehouseId");
        query.setParameter("warehouseId", warehouseId);
        return query.list();
    }
	
	
    public void saveOrUpdate(Company company){
        getSession().saveOrUpdate(company);
    }


	public String getCompanyNameByID(String id) {
		
		Session session = getSession();
		StringBuilder sb = new StringBuilder();
		sb.append("from Company a  where 1=1  ");
		Query q = initQuery(id,sb);
		if(q.list().size()>0){
			return ((Company)q.list().get(0)).getName();
		}else{
			return "";
		}
		 
	}
	
	public Map<String,String> getAllCompanytoMap() {
		  Session session = getSession();
		  String sqlString = "select a.companyid ,a.name from company a ";
		     //以SQL语句创建SQLQuery对象
		     List list = session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		     Map<String,String> map = new HashMap<String ,String>();
		    for(int i =0 ;i<list.size();i++){
		    	Map mapp = (Map)list.get(i);
		    	map.put((String)mapp.get("COMPANYID"), (String)mapp.get("NAME"));
		    }

		     
		     
		    return map;
		     
	}
	
	private Query initQuery(String id,StringBuilder sb) {
	  if (id != null && !id.equals("")) {
            sb.append(" and  a.companyid  = :id ");
        }
	  
	    Query q = getSession().createQuery(sb.toString());
        
        if (id != null && !id.equals("")) {
            q.setString("id", id);
        }
        
        return q;
	  
	}
	
	 public Company getCompanyById(String companyId) {
	        Session session = getSession();
	        return (Company)session.get(Company.class, companyId);
	    }

	    public List<Company> getAllCompanies()
	    {
	        Session session = getSession();
	        Query query = session.createQuery("from Company");
	        return query.list();
	    }
	    public List<Company> getAllCompanies(int index, int size)
	    {
	        Session session = getSession();
	        Query query = session.createQuery("from Company");
	        query.setFirstResult(index);
	        query.setMaxResults(size);
	        return query.list();
	    }

	    public List<Company> getAllCompanies(String companyName, int index, int size)
	    {
	        Session session = getSession();
	        String hql = "from Company c where c.companyName like :companyName";
	        Query q = session.createQuery(hql);
	        q.setString("companyName", "%"+companyName+"%");
	        q.setFirstResult(index);
	        q.setMaxResults(size);
	        return q.list();
	    }

	    public List<Company> getAllCompanies(String companyName, String companyType, String mailType, int index, int size)
	    {
	        Session session = getSession();
	        StringBuilder sb = new StringBuilder();
	        sb.append("from Company c where c.companyName like :companyName ");
	        sb.append("and (nvl(:companyType,'none') = 'none' or c.companyType=:companyType) ");
	        sb.append("and (nvl(:mailType,'none') = 'none' or c.mailType=:mailType) ");
	        sb.append("order by c.companyId asc");
	        Query q = session.createQuery(sb.toString());
	        q.setString("companyName", "%"+companyName+"%");
	        q.setString("companyType", companyType);
	        q.setString("mailType", mailType);
	        q.setFirstResult(index);
	        q.setMaxResults(size);
	        return q.list();
	    }

	    public int getCompanyCount()
	    {
	        Session session = getSession();
	        Query query = session.createQuery("select count(c.companyId) from Company c");
	        return Integer.valueOf(query.list().get(0).toString());
	    }

	    public int getCompanyCount(String companyName, String companyType, String mailType)
	    {
	        Session session = getSession();
	        StringBuilder sb = new StringBuilder();
	        sb.append("select count(c.companyId) from Company c where c.companyName like :companyName ");
	        sb.append("and (nvl(:companyType,'none') = 'none' or c.companyType=:companyType) ");
	        sb.append("and (nvl(:mailType,'none') = 'none' or c.mailType=:mailType) ");
	        Query q = session.createQuery(sb.toString());
	        q.setString("companyName", "%"+companyName+"%");
	        q.setString("companyType", companyType);
	        q.setString("mailType", mailType);
	        return Integer.valueOf(q.list().get(0).toString());
	    }

	   
	    public List<Company> getAllCompaniesForManual()
	    {
	        Session session = getSession();
	        Query query = session.createQuery("from Company c where exists (select 1 from CompanyConfig cc where cc.manualActing='Y' and cc.companyId=c.companyid)");
	        return query.list();
	    }


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

		

}
