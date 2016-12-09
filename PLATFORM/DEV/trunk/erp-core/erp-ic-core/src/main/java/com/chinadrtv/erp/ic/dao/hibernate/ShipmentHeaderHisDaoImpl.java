package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.model.ScmOutNotice;
import com.chinadrtv.erp.model.inventory.WmsShipmentHeaderHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * User: liukuan
 * Date: 13-2-4
 * Time: 下午6:10
 * To change this template use File | Settings | File Templates.
 */
@Repository("shipmentHeaderHisDaoImpl")
public class ShipmentHeaderHisDaoImpl extends GenericDaoHibernateBase<WmsShipmentHeaderHis,Long> implements ShipmentHeaderHisDao {
    public ShipmentHeaderHisDaoImpl()
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
     * 数据回写
     * @param ruid
     * @param date
     */
    public void updateShipmentHeaderHis(Long ruid, Date date) {
        /*
        WmsShipmentHeaderHis shipmentHeaderHis = getHibernateTemplate()
                .load(WmsShipmentHeaderHis.class,ruid);
        shipmentHeaderHis.setBatchId(""+date.getTime());
        shipmentHeaderHis.setBatchDate(date);
        this.getHibernateTemplate().update(shipmentHeaderHis);
        */

        Session session = getSession();
        Query query = session.createQuery("update WmsShipmentHeaderHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", ruid);
        query.setParameter("batchId", String.valueOf(date.getTime()));
        query.setParameter("batchDate", date);
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，ShipmentHeaderHisDaoImpl：RUID("+ruid+")");
        }
    }




    /**
     * 获取Scm出库信息
     * @return
     */
    public List<ScmOutNotice> getScmOutNotice() {
        List<ScmOutNotice> list;
        StringBuilder sb = new StringBuilder();
        sb.append("select a.ruid,a.warehouse,b.status,a.order_typer as orderTyper,a.shipment_id as shipmentId,a.order_datetime as orderDateTime,b.item,b.total_qty as totalQty");
        sb.append(" from wms_shipment_header_his a join wms_shipment_detail1_his b");
        sb.append(" on a.shipment_id = b.shipment_id and a.revison = b.revision where a.upuser='aco_transf_scm' and a.batch_id is null");

        Query q = getSession().createSQLQuery(sb.toString())
                .addScalar("ruid", LongType.INSTANCE)
                .addScalar("warehouse",StringType.INSTANCE)
                .addScalar("status",StringType.INSTANCE)
                .addScalar("orderTyper", IntegerType.INSTANCE)
                .addScalar("shipmentId",StringType.INSTANCE)
                .addScalar("orderDateTime", DateType.INSTANCE)
                .addScalar("item", StringType.INSTANCE)
                .addScalar("totalQty", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ScmOutNotice.class));
        list = q.list();
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
