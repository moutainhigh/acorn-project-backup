package com.chinadrtv.erp.sales.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.dao.OrderExtendDao;

@Repository
public class OrderExtendDaoImpl extends GenericDaoHibernate<Order, Long>
		implements OrderExtendDao {

	public OrderExtendDaoImpl() {
		super(Order.class);
	}
	
	private static final String QUERY_SQL = 
			"select orderid from iagent.orderhist " +
			"where contactid = ? and crdt > add_months(sysdate, -3) and status <> 0";
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> listOrderIds(final String contactId) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) 
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(QUERY_SQL);
				query.setString(0, contactId);
				return query.list();
			}
		});
	}

}
