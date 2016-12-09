package com.chinadrtv.order.download.dal.iagent.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.order.download.dal.iagent.dao.OrderhistDao;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-1-24
 */
@Repository("orderhistDaoEs37")
public class OrderhistDaoImpl extends GenericDaoHibernateBase<Order, Long> implements OrderhistDao {

    public OrderhistDaoImpl() {
        super(Order.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /*
    * <p>Title: 根据业务订单号获取订单</p>
    * <p>Description: </p>
    * @param orderId
    * @return
    * @see com.chinadrtv.erp.tc.dao.OrderhistDao#getOrderHistByOrderid(java.lang.String)
     */
    public Order getOrderHistByOrderid(String orderId) {
        String hql = "select oh from com.chinadrtv.erp.model.agent.Order oh where oh.orderid=:orderid";
        Parameter<String> orderid = new ParameterString("orderid", orderId);
        Order orderhist = this.find(hql, orderid);
        return orderhist;
    }

    public String getProdType(OrderDetail orderdet){
        StringBuilder sb = new StringBuilder();
        sb.append(" select c.dsc from iagent.producttype c ");
        sb.append(" where c.recid = :ProductType ");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());
        sqlQuery.setParameter("ProductType", orderdet.getProducttype());

        String prodType = "-1";

        if (sqlQuery.list().size() > 0) {

            prodType = sqlQuery.list().get(0)  == null ? "-1" : sqlQuery.list().get(0).toString();

            if ((prodType == null) || (prodType.equals("")))
            {
                prodType = "-1";
            }
        }
        else{
            prodType = "-1";
        }
        return  prodType;
    }

    public String getProdScmCode(String prodId, String prodType){
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.plucode from iagent.plubasinfo a where a.nccode = :Prodid ");
        sb.append(" and nvl(a.ncfreename, '-1') = :ProductType");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());
        sqlQuery.setParameter("Prodid", prodId);
        sqlQuery.setParameter("ProductType", prodType);

        return sqlQuery.list().get(0).toString();
    }

    public String getProdSuite(String prodId, String prodType){
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.issuiteplu from iagent.plubasinfo a where a.nccode = :Prodid ");
        sb.append(" and nvl(a.ncfreename, '-1') = :ProductType");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());
        sqlQuery.setParameter("Prodid", prodId);
        sqlQuery.setParameter("ProductType", prodType);

        return sqlQuery.list().get(0).toString();
    }

    public String getProdSpellName(String pluCode){

        StringBuilder sb = new StringBuilder();
        sb.append(" select a.spellname from iagent.plubasinfo a where a.plucode = :pluCode ");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());
        sqlQuery.setParameter("pluCode", pluCode);

        return sqlQuery.list().get(0).toString();

    }
}
