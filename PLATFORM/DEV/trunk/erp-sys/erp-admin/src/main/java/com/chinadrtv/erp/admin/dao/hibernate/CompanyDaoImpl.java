package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.Company;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyDaoImpl extends GenericDaoHibernate<Company, String> implements CompanyDao {

    public CompanyDaoImpl() {
        super(Company.class);
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

    public void saveOrUpdate(Company company){
        getSession().saveOrUpdate(company);
    }
}
