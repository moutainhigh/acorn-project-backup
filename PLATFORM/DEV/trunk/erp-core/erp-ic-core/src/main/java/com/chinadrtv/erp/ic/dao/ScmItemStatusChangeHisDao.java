package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ScmItemStatusChangeHis;

import java.util.List;

/**
 * User: gaodejian
 * Date: 13-2-19
 * Time: 下午12:57
 * To change this template use File | Settings | File Templates.
 */
public interface ScmItemStatusChangeHisDao extends GenericDao<ScmItemStatusChangeHis, Long> {
    List<ScmItemStatusChangeHis> getStatusChangedItems();
    void batchLog(ScmItemStatusChangeHis itemStatus);
}
