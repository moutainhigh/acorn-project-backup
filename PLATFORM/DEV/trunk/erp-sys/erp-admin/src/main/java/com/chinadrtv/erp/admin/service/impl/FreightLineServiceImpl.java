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
@Service("freightLineService")
public class FreightLineServiceImpl implements FreightLineService {
    @Autowired
    private FreightLineDao dao;

    public FreightLine findById(Long id){
        return dao.get(id);
    }

    public void saveFreightLine(FreightLine line){
        dao.saveOrUpdate(line);
    }

    public void removeFreightLine(Long id) {
        dao.remove(id);
    }

    public List<FreightLine> getFreightLines(String companyId) {
        return dao.getFreightLines(companyId);
    }

    public List<FreightLine> getLegFreightLines(Long legId)
    {
        return dao.getLegFreightLines(legId);
    }

    public List<FreightLine> getQuotaFreightLines(Long quotaId){
        return dao.getQuotaFreightLines(quotaId);
    }
    public List<FreightLine> getVersionFreightLines(Long versionId){
        return dao.getVersionFreightLines(versionId);
    }
}
