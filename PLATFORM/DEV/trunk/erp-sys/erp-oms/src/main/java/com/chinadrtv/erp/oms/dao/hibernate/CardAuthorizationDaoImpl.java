package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.CardAuthorization;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.CardAuthorizationDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class CardAuthorizationDaoImpl extends GenericDaoHibernate<CardAuthorization, String> implements CardAuthorizationDao{

	public CardAuthorizationDaoImpl() {
	    super(CardAuthorization.class);
	}

    public Long getCardAuthorizationCount(Map<String, Object> params){
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("select count(a.orderid) from CardAuthorization a " +
                    "where a.orderid is not null " +
                    (params.containsKey("cardtype") ? "and a.cardtype  = :cardtype  " : "") +
                    (params.containsKey("cardrightnum") ? "and a.cardrightnum = :cardrightnum " : "") +
                    (params.containsKey("orderid") ? "and a.orderid  = :orderid  " : "") +
                    (params.containsKey("impdt1") ? "and a.impdt >= :impdt1 " : "") +
                    (params.containsKey("impdt2") ? "and a.impdt <= :impdt2 " : "") +
                    (params.containsKey("confirm") ? "and (select count(b.orderid) from Orderhist b where a.orderid=b.orderid and (b.confirm = :confirm or (b.confirm is null and :confirm='0'))) > 0" : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }

            return Long.valueOf(q.list().get(0).toString());
        }
        else
        {
            return 0L;
        }
    }

    public List<CardAuthorization> getCardAuthorizations(Map<String, Object> params, int index, int size){
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery("from CardAuthorization a  " +
                    "where a.orderid is not null " +
                    (params.containsKey("cardtype") ? "and a.cardtype  = :cardtype  " : "") +
                    (params.containsKey("cardrightnum") ? "and a.cardrightnum = :cardrightnum " : "") +
                    (params.containsKey("orderid") ? "and a.orderid  = :orderid  " : "") +
                    (params.containsKey("impdt1") ? "and a.impdt >= :impdt1 " : "") +
                    (params.containsKey("impdt2") ? "and a.impdt <= :impdt2 " : "") +
                    (params.containsKey("confirm") ? "and (select count(b.orderid) from Orderhist b where a.orderid=b.orderid and (b.confirm = :confirm or (b.confirm is null and :confirm='0'))) > 0" : "")
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<CardAuthorization>();
        }
    }

    /**
     * 判断数据是否存在
     * @param orderId
     * @param cardrightnum
     * @return
     */
    public CardAuthorization searchCardAuthorization(String orderId, String cardrightnum) {
        Session session = getSession();
        Query query = session.createQuery(" from CardAuthorization a where a.orderid =:orderId and a.cardrightnum =:cardRightNum");
        query.setString("orderId",orderId);
        query.setString("cardRightNum",cardrightnum);
        return (CardAuthorization)query.uniqueResult();
    }

    /**
     * 重复导入
     * @param c
     * @return
     */
    public int insertCardAuthorization(CardAuthorization c) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into iagent.CARDAUTHORIZATION(orderid,scode,prodprice,cardrightnum,impdt,impusr,orderdetid)");
        sql.append(" values(?,?,?,?,?,?,?)");
        Query sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter(0,c.getOrderid());
        sqlQuery.setParameter(1,c.getScode());
        sqlQuery.setParameter(2,c.getProdprice());
        sqlQuery.setParameter(3,c.getCardrightnum());
        sqlQuery.setParameter(4,c.getImpdt());
        sqlQuery.setParameter(5,c.getImpusr());
        sqlQuery.setParameter(6,c.getOrderdetid());
        int result = sqlQuery.executeUpdate();
        return result;
    }
}
