package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PreTrade;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-7-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeDao extends GenericDao<PreTrade,Long> {
    PreTrade findTradeByNetOrderId(String netOrderId);
}
