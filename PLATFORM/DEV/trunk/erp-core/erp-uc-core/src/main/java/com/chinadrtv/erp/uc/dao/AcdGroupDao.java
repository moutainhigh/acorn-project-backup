package com.chinadrtv.erp.uc.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;

import java.util.List;

/**
 * ACD组数据访问
 * User: gaodejian
 * Date: 13-12-13
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface AcdGroupDao extends GenericDao<AcdGroup, String> {
    List<AcdGroup> getAcdGroupsByDeptNo(String deptNo, Boolean ivr);
    List<AcdGroup> getAllAcdGroupsByDeptNo(Boolean ivr);
}
