package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.*;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
public interface FreightLegService {
    FreightLeg findById(Long id);
    void saveFreightLeg(FreightLeg leg);
    void removeFreightLeg(Long id);
    List<FreightLeg> getFreightLegs(String companyId);
}
