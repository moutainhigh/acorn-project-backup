package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OrderSettlement;
import com.chinadrtv.erp.oms.dao.OrderSettlementDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单结算单数据访问
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderSettlementDaoImpl extends GenericDaoHibernate<OrderSettlement, Long> implements OrderSettlementDao {
    public OrderSettlementDaoImpl() {
        super(OrderSettlement.class);
    }

    public List<OrderSettlement> getAllSettlements()
    {
        return getAll();
    }

    public List<OrderSettlement> getAllSettlements(int index, int size){
        Session session = getSession();
        Query query = session.createQuery("from OrderSettlement");
        query.setFirstResult(index);
        query.setMaxResults(size);
        return query.list();
    }

    public List<OrderSettlement> getAllSettlements(String sourceId, int index, int size){
        Session session = getSession();
        Query query = session.createQuery("from OrderSettlement a where a.sourceId in (:sourceId)");
        query.setParameterList("sourceId", sourceId.split(","));
        query.setFirstResult(index);
        query.setMaxResults(size);
        return query.list();
    }

    public List<OrderSettlement> getAllSettlements(String sourceId, String tradeId, String shipmentId, String startDate, String endDate, int index, int size){
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);

        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("from OrderSettlement c where c.sourceId in (:sourceId) ");
        if(!"".equals(tradeId))  {
            sb.append("and c.tradeId=:tradeId ");
        }
        if(!"".equals(shipmentId))  {
            sb.append("and c.shipmentId=:shipmentId ");
        }
        if(startDt != null){
            sb.append("and c.crdt >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.crdt <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        q.setParameterList("sourceId", sourceId.split(","));
        if(!"".equals(tradeId))  {
            q.setString("tradeId", tradeId);
        }
        if(!"".equals(shipmentId))  {
            q.setString("shipmentId", shipmentId);
        }
        if(startDt != null){
            q.setParameter("startDate", startDt);
        }
        if(endDt != null){
            q.setParameter("endDate", endDt);
        }

        q.setFirstResult(index);
        q.setMaxResults(size);
        return q.list();
    }

    public Long getSettlementCount(){
        Session session = getSession();
        Query query = session.createQuery("select count(a.ruId) from OrderSettlement a");
        return (Long)query.uniqueResult();
    }

    public Long getSettlementCount(String sourceId, String tradeId, String shipmentId, String startDate, String endDate){
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);


        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(c.ruId) from OrderSettlement c where c.sourceId in (:sourceId) ");

        if(!"".equals(tradeId))  {
            sb.append("and c.tradeId=:tradeId ");
        }
        if(!"".equals(shipmentId))  {
            sb.append("and c.shipmentId=:shipmentId ");
        }

        if(startDt != null){
            sb.append("and c.crdt >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.crdt <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        q.setParameterList("sourceId", sourceId.split(","));

        if(!"".equals(tradeId))  {
            q.setString("tradeId", tradeId);
        }
        if(!"".equals(shipmentId))  {
            q.setString("shipmentId", shipmentId);
        }

        if(startDt != null){
            q.setParameter("startDate", startDt);
        }
        if(endDt != null){
            q.setParameter("endDate", endDt);
        }
        return (Long)q.uniqueResult();
    }

    public Long getSettlementCount(String sourceId){
        Session session = getSession();
        Query query = session.createQuery("select count(a.ruId) from OrderSettlement a where a.sourceId in (:sourceId)");
        query.setString("sourceId", sourceId);
        return (Long)query.uniqueResult();
    }

    private Date getDate(String dateString){
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateString);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public void approveSettlement(Long id){
        Session session = getSession();
        Query q = session.createQuery("update OrderSettlement a set a.isrecognition='1' where a.ruId=:ruId");
        q.setLong("ruId", id);
        q.executeUpdate();
    }
}
