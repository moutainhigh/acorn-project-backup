package com.chinadrtv.erp.oms.service;



import com.chinadrtv.erp.model.CompanyContract;

import java.util.List;
import java.util.Map;

/**
 * 承运商配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyContractService {
    CompanyContract getContractById(Integer contractId);
    List<CompanyContract> getAllContracts();
    List<CompanyContract> getAllContracts(Long warehouseId);
    List<CompanyContract> getAllContracts(Map<String, Object> params, int index, int size);
    Long getContractCount(Map<String, Object> params);
    void saveContract(CompanyContract contract);
    void addContract(CompanyContract contract);
    void removeContract(Integer contractId);
}
