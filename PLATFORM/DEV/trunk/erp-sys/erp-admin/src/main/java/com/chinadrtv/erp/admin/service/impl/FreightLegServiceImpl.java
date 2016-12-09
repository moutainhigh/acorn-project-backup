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
@Service("freightLegService")
public class FreightLegServiceImpl implements FreightLegService {
    @Autowired
    private FreightLegDao dao;

    public FreightLeg findById(Long id){
        return dao.get(id);
    }

    public void saveFreightLeg(FreightLeg leg){
        dao.saveOrUpdate(leg);
    }

    public void removeFreightLeg(Long id) {
        dao.remove(id);
    }

    public List<FreightLeg> getFreightLegs(String companyId) {
        return dao.getFreightLegs(companyId);
    }
}
