package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ScmCloseShipment;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-19
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public interface ScmCloseShipmentDao extends GenericDao<ScmCloseShipment,Long> {
    //获取数据
    List<ScmCloseShipment> getScmCloseShipment();
    //数据回写
    boolean updateScmCloseShipment(Long ruid, Date date);
}
