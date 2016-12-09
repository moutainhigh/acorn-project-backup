package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdj;
import com.chinadrtv.erp.oms.dao.ShipmentTotalPriceAdjDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
@Repository("shipmentTotalPriceAdjDao")
public class ShipmentTotalPriceAdjDaoImpl extends GenericDaoHibernate<ShipmentTotalPriceAdj,Long> implements ShipmentTotalPriceAdjDao {
    public ShipmentTotalPriceAdjDaoImpl(){
        super(ShipmentTotalPriceAdj.class);
    }

    @Override
    public void saveShipmentTotalPriceAdj(ShipmentTotalPriceAdj shipmentTotalPriceAdj) {
        this.saveOrUpdate(shipmentTotalPriceAdj);
    }


}
