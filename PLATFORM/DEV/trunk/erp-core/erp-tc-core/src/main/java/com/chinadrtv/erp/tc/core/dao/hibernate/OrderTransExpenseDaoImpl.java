package com.chinadrtv.erp.tc.core.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dao.OrderTransExpenseDao;


/**
 * 订单运费计算DAO实现类
 * User: liyu
 * Date: 13-1-28
 * Time: 下午4:44
 * 创建订单运费.
 */
@Repository
public class OrderTransExpenseDaoImpl extends GenericDaoHibernateBase<Order, Long> implements OrderTransExpenseDao {

    public OrderTransExpenseDaoImpl() {
        super(Order.class);
    }

    /**
     * 获取订单运费
     *
     * @param orderhist 订单实体
     * @return 运费
     */
    public Double getOrderTransExpenseByOrder(Order orderhist) {
        //预留接口,返回一个超大的费用
        return 9999D;
    }

    /**
     * 获取订单运费，用于IAGENT调用
     *
     * @param ConditionStr 条件字符串
     * @return 运费
     */
    public Double getOrderTransExpenseByAgent(String ConditionStr) {
        Session session = getSession();

        Query q = getSession().createSQLQuery("select iagent.fun_getordertransexpense_ex(:str) from dual");
        q.setString("str", ConditionStr);
        return new Double(q.list().get(0).toString());
    }
    
    @Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
}
