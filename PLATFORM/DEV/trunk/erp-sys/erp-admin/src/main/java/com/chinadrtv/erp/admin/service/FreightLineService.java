package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface FreightLineService {
    FreightLine findById(Long id);
    void saveFreightLine(FreightLine line);
    void removeFreightLine(Long id);
    List<FreightLine> getFreightLines(String companyId);
    List<FreightLine> getLegFreightLines(Long legId);
    List<FreightLine> getQuotaFreightLines(Long quotaId);
    List<FreightLine> getVersionFreightLines(Long versionId);
}
