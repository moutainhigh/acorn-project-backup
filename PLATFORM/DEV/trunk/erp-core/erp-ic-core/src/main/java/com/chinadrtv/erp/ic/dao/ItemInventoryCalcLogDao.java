/**
 *
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午3:31:38
 */
package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.model.inventory.ItemInventoryCalcLog;

/**
 *  库存计算日志数据访问
 */
public interface ItemInventoryCalcLogDao {

    ItemInventoryCalcLog createUnhandledCalcLog(String batchId, String batchUser);
    void saveLog(ItemInventoryCalcLog log);
}
