package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ItemInventorySnapshot;

/**
 * 结算单数据访问接口
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInventorySnapshotDao extends GenericDao<ItemInventorySnapshot,Long> {
    void executeSnapshot(String snapshotId, String snapshotBy);
}
