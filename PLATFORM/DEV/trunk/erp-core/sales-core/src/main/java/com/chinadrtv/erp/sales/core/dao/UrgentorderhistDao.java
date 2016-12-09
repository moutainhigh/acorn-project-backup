package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Urgentorderhist;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface UrgentorderhistDao extends GenericDao<Urgentorderhist,String> {
    void deleteUrgentOrder(String orderId);
}
