package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.oms.dao.*;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Date;
import java.util.List;

@Repository
public class OrderdetDaoImpl extends GenericDaoHibernate<Orderdet, java.lang.String> implements OrderdetDao{

	public OrderdetDaoImpl() {
	    super(Orderdet.class);
	}
	
	
	public Orderdet getOrderdetById(String orderdetId) {
        Session session = getSession();
        return (Orderdet)session.get(Orderdet.class, orderdetId);
    }
	
	
	    public List<Orderdet> getAllOrderdet()
    {
        Session session = getSession();
        Query query = session.createQuery("from Orderdet");
        return query.list();
    }
    public List<Orderdet> getAllOrderdet(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Orderdet");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(Orderdet orderdet){
        getSession().saveOrUpdate(orderdet);
    }


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.dao.OrderdetDao#getAllOrderdet(java.lang.String)
	 */
	public List<Orderdet> getAllOrderdet(String orderid) {
		Session session = getSession();
        Query query = session.createQuery("from Orderdet a  where a.orderid = :orderid ");
        query.setString("orderid", orderid);
        return query.list();
	}
	

}
