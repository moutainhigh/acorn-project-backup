package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.oms.dao.ShipmentDetailDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 下午7:10
 * To change this template use File | Settings | File Templates.
 */
@Repository("shipmentDetailDao")
public class ShipmentDetailDaoImpl extends GenericDaoHibernate<ShipmentDetail,Long> implements ShipmentDetailDao {
    public ShipmentDetailDaoImpl()
    {
        super(ShipmentDetail.class);
    }

    /**
     * 保存明细
     * @param shipmentDetail
     */
    public void saveShipmentDetail(ShipmentDetail shipmentDetail) {
        this.save(shipmentDetail);
    }

    /**
     *查询减免后的运费
     * @param rShipmentId
     * @return
     */
    public List getCarrier(String rShipmentId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.carrier from acoapp_oms.shipment_detail a where a.shipment_id = :shipmentId");
        Query sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("shipmentId",rShipmentId);
        return sqlQuery.list();
    }

    /**
     * 删除逆向单明细
     * @param shipmentId
     */
    public void deleteDetailByShipmentId(String shipmentId) {
        Query query = getSession().createQuery("delete ShipmentDetail a where a.shipmentId = :ShipmentId");
        query.setParameter("ShipmentId",shipmentId);
        query.executeUpdate();
    }
}
