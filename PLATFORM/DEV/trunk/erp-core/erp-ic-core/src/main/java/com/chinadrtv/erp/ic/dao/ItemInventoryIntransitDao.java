package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.ic.model.NcIntransitItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryIntransit;

import java.util.List;

/**
 * 在途库存明细
 * User: gaudi.gao
 * Date: 13-7-31
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInventoryIntransitDao extends GenericDao<ItemInventoryIntransit, Long> {
    List<NcIntransitItem> GetNcIntransitItems(String nccode, String ncfreename);
}
