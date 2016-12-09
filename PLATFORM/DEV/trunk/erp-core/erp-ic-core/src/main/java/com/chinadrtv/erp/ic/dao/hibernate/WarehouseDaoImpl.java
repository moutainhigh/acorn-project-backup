package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.ic.dao.WarehouseDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDaoImpl extends GenericDaoHibernate<Warehouse, Long> implements WarehouseDao{

	public WarehouseDaoImpl() {
	    super(Warehouse.class);
	}

    public List<Warehouse> getAllWarehouses()
    {
        Session session = getSession();
        Query query = session.createQuery("from Warehouse");
        return query.list();
    }
}
