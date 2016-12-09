package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ScmOnhandAdjustmentHis;

import java.util.List;

/**
 * User: gaodejian
 * Date: 13-2-19
 * Time: 下午12:57
 * To change this template use File | Settings | File Templates.
 */
public interface ScmOnhandAdjustmentHisDao extends GenericDao<ScmOnhandAdjustmentHis, Long> {
    List<ScmOnhandAdjustmentHis> getAdjustedItems();
    void batchLog(ScmOnhandAdjustmentHis adjustment);
}
