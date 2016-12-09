package com.chinadrtv.erp.oms.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.CityAll;

import java.util.List;

/**
 * 城市数据访问
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface CityDao extends GenericDao<CityAll, Long> {
    List<CityAll> getAllCities();
    List<CityAll> getCities(String provinceId);
}
