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
@Service("freightVersionService")
public class FreightVersionServiceImpl implements FreightVersionService {
    @Autowired
    private FreightVersionDao dao;

    public FreightVersion findById(Long id){
        return dao.get(id);
    }

    public void saveFreightVersion(FreightVersion version){
        dao.saveOrUpdate(version);
    }

    public void removeFreightVersion(Long id) {
        dao.remove(id);
    }

    public List<FreightVersion> getFreightVersions(String companyId) {
        return dao.getFreightVersions(companyId);
    }
}
