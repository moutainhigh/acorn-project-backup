package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.ic.model.ScmOutNotice;
import com.chinadrtv.erp.model.inventory.WmsShipmentHeaderHis;

import java.util.Date;
import java.util.List;

/**
 * User: liukuan
 * Date: 13-2-5
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderHisDao extends GenericDao<WmsShipmentHeaderHis,Long> {

    void updateShipmentHeaderHis(Long ruid, Date date);

    List<ScmOutNotice> getScmOutNotice();
}
