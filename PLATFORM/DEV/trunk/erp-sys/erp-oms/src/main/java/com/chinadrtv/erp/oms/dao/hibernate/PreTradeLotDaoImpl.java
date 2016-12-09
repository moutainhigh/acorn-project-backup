package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.oms.dao.*;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Date;
import java.util.List;

/**
 * PreTradeLotDaoImpl
 * 
 * @author haoleitao
 *
 */
@Repository
public class PreTradeLotDaoImpl extends GenericDaoHibernate<PreTradeLot, Long> implements PreTradeLotDao{

	public PreTradeLotDaoImpl() {
	    super(PreTradeLot.class);
	}
	
	
	public PreTradeLot getPreTradeLotById(String preTradeLotId) {
        Session session = getSession();
        return (PreTradeLot)session.get(PreTradeLot.class, preTradeLotId);
    }
	
	
	    public List<PreTradeLot> getAllPreTradeLot()
    {
        Session session = getSession();
        Query query = session.createQuery("from PreTradeLot");
        return query.list();
    }
    public List<PreTradeLot> getAllPreTradeLot(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from PreTradeLot");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(PreTradeLot preTradeLot){
        getSession().saveOrUpdate(preTradeLot);
    }
	

}
