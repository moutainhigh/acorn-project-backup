package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface FreightVersionService {
    FreightVersion findById(Long id);
    void saveFreightVersion(FreightVersion version);
    void removeFreightVersion(Long id);
    List<FreightVersion> getFreightVersions(String companyId);
}
