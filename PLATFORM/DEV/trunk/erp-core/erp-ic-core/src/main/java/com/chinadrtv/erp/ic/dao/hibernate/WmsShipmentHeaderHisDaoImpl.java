package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.WmsShipmentHeaderHisDao;
import com.chinadrtv.erp.model.inventory.WmsShipmentHeaderHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 上午9:46
 * To change this template use File | Settings | File Templates.
 */
@Repository("wmsShipmentHeaderHisDao")
public class WmsShipmentHeaderHisDaoImpl extends GenericDaoHibernateBase<WmsShipmentHeaderHis,Long> implements WmsShipmentHeaderHisDao {
    public WmsShipmentHeaderHisDaoImpl()
    {
        super(WmsShipmentHeaderHis.class);
    }
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /**
     * 获取未处理出货单
     * @return
     */
    public List<WmsShipmentHeaderHis> getUnhandledShipments() {
        Session session = getSession();
        Query query = session.createQuery("from WmsShipmentHeaderHis a where a.upuser='aco_transf_scm' and a.batchId is null");
        query.setMaxResults(100); //最多一千笔,否则处理时间很长
        return query.list();
    }

    /**
     *
     * @param warehouse
     * @param shipmentId
     * @param orderTyper
     * @param productCode
     * @return
     */
    public Double getTotalQty(String warehouse, String shipmentId, String orderTyper, String productCode) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select sum(b.total_qty) as total_qty from WMS_SHIPMENT_HEADER_HIS a "+
                        "left join WMS_SHIPMENT_DETAIL1_HIS b on a.shipment_id = b.shipment_id and a.revison = b.revision " +
                        "where a.warehouse=:warehouse and a.shipment_id=:shipmentId and a.order_typer=:orderTyper and b.item=:productCode");
        query.setString("warehouse", warehouse);
        query.setString("shipmentId", shipmentId);
        query.setString("orderTyper", orderTyper);
        query.setString("productCode", productCode);
        Object result = query.uniqueResult();
        return result != null ? Double.valueOf(result.toString()) : 0.0;
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param shipment
     */
    public void batchLog(WmsShipmentHeaderHis shipment){
        //saveOrUpdate(shipment);

        Session session = getSession();
        session.evict(shipment);

        Query query = session.createQuery("update WmsShipmentHeaderHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", shipment.getRuid());
        query.setParameter("batchId", shipment.getBatchId());
        query.setParameter("batchDate", shipment.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，出库单通知单被多次执行："+shipment.getShipmentId());
        }
    }
}
