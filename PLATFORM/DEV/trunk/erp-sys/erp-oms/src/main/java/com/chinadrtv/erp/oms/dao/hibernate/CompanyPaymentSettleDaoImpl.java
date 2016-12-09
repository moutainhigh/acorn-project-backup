package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.CompanyPaymentSettle;
import com.chinadrtv.erp.oms.dao.CompanyPaymentSettleDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 发运单-数据访问
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyPaymentSettleDaoImpl extends GenericDaoHibernate<CompanyPaymentSettle, Long> implements CompanyPaymentSettleDao {
    public CompanyPaymentSettleDaoImpl() {
        super(CompanyPaymentSettle.class);
    }

    public Long getCompanyPaymentSettleCount(Map<String, Object> params){

        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("select count(a.id) from CompanyPaymentSettle a");
            return Long.valueOf(q.list().get(0).toString());
        } else {
            return 0L;
        }
    }

    public List<CompanyPaymentSettle> getCompanyPaymentSettles(Map<String, Object> params, int index, int size){


        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("from CompanyPaymentSettle a");
            q.setFirstResult(index);
            q.setMaxResults(size);
            return q.list();
        }
        else{
            return new ArrayList<CompanyPaymentSettle>();
        }
    }
}
