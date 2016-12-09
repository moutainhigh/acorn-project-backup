package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface FreightQuotaService {
    FreightQuota findById(Long id);
    void saveFreightQuota(FreightQuota quota);
    void removeFreightQuota(Long id);
    List<FreightQuota> getFreightQuotas(String companyId);
}
