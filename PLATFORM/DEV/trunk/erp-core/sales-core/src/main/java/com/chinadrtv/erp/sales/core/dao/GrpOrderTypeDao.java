package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.GrpOrderType;
import com.chinadrtv.erp.model.agent.MediaDnis;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface GrpOrderTypeDao extends GenericDao<GrpOrderType,Long> {

    public List<MediaDnis> getOrderTypeByN400(String n400);
}
