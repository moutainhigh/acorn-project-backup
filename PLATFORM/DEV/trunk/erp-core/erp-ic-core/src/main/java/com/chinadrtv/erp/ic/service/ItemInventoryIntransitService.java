package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.ic.model.NcIntransitItem;

import java.util.List;

/**
 * 库存在途服务
 * User: gaudi.gao
 * Date: 13-5-14
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInventoryIntransitService {
    List<NcIntransitItem> GetNcIntransitItems(String nccode, String ncfreename);
}
