package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.oms.dao.CompanyContractDao;
import com.chinadrtv.erp.oms.service.CompanyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 承运商配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@Service("companyContractService")
public class CompanyContractServiceImpl implements CompanyContractService {
    @Autowired
    private CompanyContractDao contractDao;

    public CompanyContract getContractById(Integer contractId){
        return contractDao.get(contractId);
    }

    public List<CompanyContract> getAllContracts(){
        return contractDao.getAllContracts();
    }
    public List<CompanyContract> getAllContracts(Long warehouseId){
        return contractDao.getAllContracts(warehouseId);
    }

    public List<CompanyContract> getAllContracts(Map<String, Object> params, int index, int size){
       return contractDao.getAllContracts(params, index, size);
    }
    public Long getContractCount(Map<String, Object> params){
        return contractDao.getContractCount(params);
    }
    public void saveContract(CompanyContract contract){
        contractDao.saveOrUpdate(contract);
    }
    public void addContract(CompanyContract contract){
        contractDao.save(contract);
    }
    public void removeContract(Integer contractId){
        contractDao.remove(contractId);
    }
}
