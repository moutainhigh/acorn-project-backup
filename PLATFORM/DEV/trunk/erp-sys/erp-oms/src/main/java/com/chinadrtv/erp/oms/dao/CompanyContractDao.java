package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.CompanyContract;

import java.util.List;
import java.util.Map;

/**
 * 送货公司访问接口(OMS)
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyContractDao extends GenericDao<CompanyContract, Integer> {
    List<CompanyContract> getAllContracts();
    List<CompanyContract> getAllContracts(Long warehouseId);
    List<CompanyContract> getAllContracts(Map<String, Object> params, int index, int size);
    Long getContractCount(Map<String, Object> params);
}
