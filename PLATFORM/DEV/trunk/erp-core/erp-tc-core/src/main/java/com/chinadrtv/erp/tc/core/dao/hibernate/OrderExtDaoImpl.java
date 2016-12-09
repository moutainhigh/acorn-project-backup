package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.OrderExt;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dao.OrderExtDao;
import com.chinadrtv.erp.tc.core.dao.WarehouseDao;

@Repository
public class OrderExtDaoImpl extends GenericDaoHibernate<OrderExt, java.lang.String> implements OrderExtDao{

	@Autowired
    @Qualifier("tcWarehouseDao")
	private WarehouseDao warehouseDao;
	
	public OrderExtDaoImpl() {
	    super(OrderExt.class);
	}

	/* (none Javadoc)
	* <p>Title: insertOrUpdateByOrderhist</p>
	* <p>Description: </p>
	* @param orderhist
	* @param warehouseId
	* @see com.chinadrtv.erp.shipment.dao.OrderdetDao#insertOrUpdateByOrderhist(com.chinadrtv.erp.model.agent.Orderhist, java.lang.String)
	*/ 
	public void insertOrUpdateByOrderhist(Order orderhist, String warehouseId) {
		String hql = "select od from OrderExt od where od.orderId=:orderid";
		OrderExt oe = this.find(hql, new ParameterString("orderid", orderhist.getOrderid()));
		
		if(null!=oe){
			Warehouse wh = warehouseDao.get(new Long(warehouseId));
			oe.setUpDate(new Date());
			oe.setWarehouseIdExt(new Integer(warehouseId));
			oe.setWarehouseNameExt(wh.getWarehouseName());
			this.saveOrUpdate(oe);
		}
		
	}


    public void saveOrUpdateFrom(final OrderExt orderExt)
    {
        //如果存在，就直接更新 WAREHOUSEUID_EXT和 WAREHOUSENAMEEXT，如果不存在，那么直接插入
        this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                OrderExt orderExtOrg =(OrderExt)session.get(OrderExt.class,orderExt.getOrderId());
                if(orderExtOrg==null)
                {
                    session.save(orderExt);
                }
                else
                {
                    orderExtOrg.setUpDate(new Date());
                    orderExtOrg.setWarehouseIdExt(orderExt.getWarehouseIdExt());
                    orderExtOrg.setWarehouseNameExt(orderExt.getWarehouseNameExt());
                }
                return null;
            }
        });
    }

}
