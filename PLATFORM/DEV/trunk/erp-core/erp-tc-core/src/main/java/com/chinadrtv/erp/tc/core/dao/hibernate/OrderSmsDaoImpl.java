package com.chinadrtv.erp.tc.core.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.OrderSms;
import com.chinadrtv.erp.tc.core.dao.OrderSmsDao;

/**
 * 短信DAO实现类
 * User: liyu
 * Date: 13-1-29
 * Time: 下午2:14
 */
@Repository
public class OrderSmsDaoImpl extends GenericDaoHibernateBase<OrderSms, String> implements OrderSmsDao {

    public OrderSmsDaoImpl()
    {
        super(OrderSms.class);
    }

    public void saveOrUpdate(OrderSms orderSms){
        getSession().saveOrUpdate(orderSms);
    }
    
    @Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
}