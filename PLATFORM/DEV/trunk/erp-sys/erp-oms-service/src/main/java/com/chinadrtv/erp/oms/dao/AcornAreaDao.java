package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.AreaAll;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface AcornAreaDao extends GenericDao<AreaAll, Integer> {
    public List<AreaAll> getPageAreaByCountryId(Integer countryId, Integer startPos,Integer pageSize) ;
}
