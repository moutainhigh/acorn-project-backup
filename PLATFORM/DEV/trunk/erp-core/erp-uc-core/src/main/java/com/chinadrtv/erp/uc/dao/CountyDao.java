package com.chinadrtv.erp.uc.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.CountyAll;

import java.util.List;

/**
 * 城市数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface CountyDao extends GenericDao<CountyAll, Integer> {
    List<CountyAll> getAllCounties();
    List<CountyAll> getCounties(Integer cityId);
}
