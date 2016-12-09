package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.WmsReceiptHeaderHisDao;
import com.chinadrtv.erp.model.inventory.WmsReceiptHeaderHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品档案
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class WmsReceiptHeaderHisDaoImpl extends GenericDaoHibernateBase<WmsReceiptHeaderHis, Long> implements WmsReceiptHeaderHisDao {

    public WmsReceiptHeaderHisDaoImpl() {
        super(WmsReceiptHeaderHis.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<WmsReceiptHeaderHis> getUnhandledReceipts() {
        Session session = getSession();
        Query query = session.createQuery("from WmsReceiptHeaderHis a where a.batchId is null");
        query.setMaxResults(100); //最多一千笔,否则处理时间很长
        return query.list();
    }

    public Double getTotalQty(String warehouse, String receiptId,String receiptType, String productCode){

        Session session = getSession();
        Query query = session.createSQLQuery(
                "select sum(b.total_qty) as total_qty from WMS_RECEIPT_HEADER_HIS a "+
                "left join WMS_RECEIPT_DETAIL_HIS b on a.receipt_id = b.receipt_id " +
                "where a.warehouse=:warehouse and a.receipt_Id=:receiptId and a.receipt_type=:receiptType and b.item=:productCode");
        query.setString("warehouse", warehouse);
        query.setString("receiptId", receiptId);
        query.setString("receiptType", receiptType);
        query.setString("productCode", productCode);
        Object result = query.uniqueResult();
        return result != null ? Double.valueOf(result.toString()) : 0.0;
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param receipt
     */
    public void batchLog(WmsReceiptHeaderHis receipt){
        //saveOrUpdate(receipt);

        Session session = getSession();
        session.evict(receipt);

        Query query = session.createQuery("update WmsReceiptHeaderHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", receipt.getRuid());
        query.setParameter("batchId", receipt.getBatchId());
        query.setParameter("batchDate", receipt.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，采购入库单通知单被多次执行："+receipt.getReceiptId());
        }
    }

    public WmsReceiptHeaderHis getArrivedReceipt(String receiptId) {
        Session session = getSession();
        Query query = session.createQuery("from WmsReceiptHeaderHis a where a.receiptId = :receiptId");
        query.setParameter("receiptId",receiptId);
        List<WmsReceiptHeaderHis> results = query.list();
        return results != null && results.size() > 0 ? results.get(0) : null;
    }

    public List<WmsReceiptHeaderHis> getArrivedReceipts(List<String> receiptIds){
        Session session = getSession();
        /* IN 子句中的LIST个数最长为1000 */
        List<WmsReceiptHeaderHis> results = new ArrayList<WmsReceiptHeaderHis>();
        int index = 0;
        int step = 1000;
        while(index < receiptIds.size())  {
            Query query = session.createQuery("from WmsReceiptHeaderHis a where a.receiptId in (:receiptIds)");
            query.setParameterList("receiptIds", receiptIds.subList(index, Math.min(index + step - 1, receiptIds.size())));
            results.addAll(query.list());
            index += step;
        }
        return results;
    }
}
