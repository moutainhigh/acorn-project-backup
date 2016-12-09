package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.tc.core.dao.WarehouseDao;

@Repository("tcWarehouseDao")
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
