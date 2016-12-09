package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OmsBackorder;
import com.chinadrtv.erp.oms.dao.OmsBackorderDao;
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
public class OmsBackorderDaoImpl extends GenericDaoHibernate<OmsBackorder, Long> implements OmsBackorderDao {
    public OmsBackorderDaoImpl() {
        super(OmsBackorder.class);
    }


    public Long getBackorderCount(String orderId, String prodId, String status, String startDate, String endDate) {
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);

        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(c.id) from OmsBackorder c where 1=1 ");
        if(!"".equals(orderId)) {
            sb.append("and c.orderId = :orderId ");
        }
        if(!"".equals(prodId)) {
            sb.append("and c.prodId = :prodId ");
        }
        if(!"".equals(status)) {
            sb.append("and c.status = :status ");
        }
        if(startDt != null){
            sb.append("and c.created >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.created <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        if(!"".equals(orderId)) {
            q.setString("orderId", orderId);
        }
        if(!"".equals(prodId)) {
            q.setString("prodId", prodId);
        }
        if(!"".equals(status)) {
            q.setString("status", status);
        }
        if(startDt != null){
            q.setParameter("startDate", startDt);
        }
        if(endDt != null){
            q.setParameter("endDate", endDt);
        }
        return (Long)q.uniqueResult();
    }

    public void deferBackorder(Long id, Double days){
        Session session = getSession();
        Query q = session.createSQLQuery("update omsbackorder set expired = expired + (:days) where id=:id");
        q.setLong("id", id);
        q.setDouble("days", days);
        q.executeUpdate();
    }

    public List<OmsBackorder> getAllBackorders(String orderId, String prodId, String status, String startDate, String endDate, int index, int size) {
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);

        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("from OmsBackorder c where 1=1 ");
        if(!"".equals(orderId)) {
            sb.append("and c.orderId = :orderId ");
        }
        if(!"".equals(prodId)) {
            sb.append("and c.prodId = :prodId ");
        }
        if(!"".equals(status)) {
            sb.append("and c.status = :status ");
        }

        if(startDt != null){
            sb.append("and c.created >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.created <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        if(!"".equals(orderId)) {
            q.setString("orderId", orderId);
        }

        if(!"".equals(prodId)) {
        q.setString("prodId", prodId);
        }

        if(!"".equals(status)) {
            q.setString("status", status);
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
}
