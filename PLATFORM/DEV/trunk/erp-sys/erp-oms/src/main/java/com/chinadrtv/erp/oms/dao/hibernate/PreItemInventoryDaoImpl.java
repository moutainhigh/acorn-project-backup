package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.PreItemInventory;
import com.chinadrtv.erp.model.PreItemInventory;
import com.chinadrtv.erp.oms.dao.PreItemInventoryDao;
import com.chinadrtv.erp.oms.dao.PreItemInventoryDao;
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
public class PreItemInventoryDaoImpl extends GenericDaoHibernate<PreItemInventory, Long> implements PreItemInventoryDao {
    public PreItemInventoryDaoImpl() {
        super(PreItemInventory.class);
    }

    public List<PreItemInventory> getAllInventoryItems(String sourceId, String skuId, String outSkuId, String startDate, String endDate, int index, int size){
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);

        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("from PreItemInventory c where c.sourceId in (:sourceId) ");
        if(!"".equals(skuId)){
            sb.append("and c.skuId=:skuId ");
        }
        if(!"".equals(outSkuId)){
            sb.append("and c.outSkuId=:outSkuId ");
        }


        if(startDt != null){
            sb.append("and c.created >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.created <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        q.setParameterList("sourceId", sourceId.split(","));

        if(!"".equals(skuId)){
            q.setString("skuId", skuId);
        }

        if(!"".equals(outSkuId)){
            q.setString("outSkuId", outSkuId);
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

    public Long getInventoryItemCount(String sourceId, String skuId, String outSkuId, String startDate, String endDate){
        Date startDt = getDate(startDate);
        Date endDt = getDate(endDate);

        Session session = getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(c.id) from PreItemInventory c where c.sourceId in (:sourceId) ");
        if(!"".equals(skuId)){
            sb.append("and c.skuId=:skuId ");
        }
        if(!"".equals(outSkuId)){
            sb.append("and c.outSkuId=:outSkuId ");
        }
        if(startDt != null){
            sb.append("and c.created >= :startDate ");
        }
        if(endDt != null){
            sb.append("and c.created <= :endDate ");
        }

        Query q = session.createQuery(sb.toString());
        q.setParameterList("sourceId", sourceId.split(","));

        if(!"".equals(skuId)){
            q.setString("skuId", skuId);
        }

        if(!"".equals(outSkuId)){
            q.setString("outSkuId", outSkuId);
        }

        if(startDt != null){
            q.setParameter("startDate", startDt);
        }
        if(endDt != null){
            q.setParameter("endDate", endDt);
        }

        return (Long)q.uniqueResult();
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
