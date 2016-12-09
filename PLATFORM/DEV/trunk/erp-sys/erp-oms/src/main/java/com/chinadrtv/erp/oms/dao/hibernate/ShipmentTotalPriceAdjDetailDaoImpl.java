package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdjDetail;
import com.chinadrtv.erp.oms.dao.ShipmentTotalPriceAdjDetailDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
@Repository("shipmentTotalPriceAdjDetailDao")
public class ShipmentTotalPriceAdjDetailDaoImpl extends GenericDaoHibernate<ShipmentTotalPriceAdjDetail,Long> implements ShipmentTotalPriceAdjDetailDao {
    public ShipmentTotalPriceAdjDetailDaoImpl(){
        super(ShipmentTotalPriceAdjDetail.class);
    }

    @Override
    public void saveShipmentTotalPriceAdjDetail(ShipmentTotalPriceAdjDetail shipmentTotalPriceAdjDetail) {
        this.saveOrUpdate(shipmentTotalPriceAdjDetail);
    }


}
