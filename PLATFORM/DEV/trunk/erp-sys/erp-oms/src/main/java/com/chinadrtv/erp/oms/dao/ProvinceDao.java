package com.chinadrtv.erp.oms.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Province;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface ProvinceDao extends GenericDao<Province, String> {
    List<Province> getAllProvinces();
}
