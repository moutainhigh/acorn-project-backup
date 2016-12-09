package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("freightQuotaService")
public class FreightQuotaServiceImpl implements FreightQuotaService {
    @Autowired
    private FreightQuotaDao dao;

    public FreightQuota findById(Long id){
        return dao.get(id);
    }

    public void saveFreightQuota(FreightQuota quota){
        dao.saveOrUpdate(quota);
    }

    public void removeFreightQuota(Long id) {
        dao.remove(id);
    }

    public List<FreightQuota> getFreightQuotas(String companyId) {
        return dao.getFreightQuotas(companyId);
    }
}
