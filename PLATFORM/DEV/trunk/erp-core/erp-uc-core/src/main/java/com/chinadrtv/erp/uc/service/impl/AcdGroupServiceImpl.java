package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.uc.dao.AcdGroupDao;
import com.chinadrtv.erp.uc.service.AcdGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("acdGroupService")
public class AcdGroupServiceImpl implements AcdGroupService {

    @Autowired
    private AcdGroupDao acdGroupDao;

    public List<AcdGroup> getAllAcdGroups(){
        return acdGroupDao.getAll();
    }

    public List<AcdGroup> getAcdGroupsByDeptNo(String deptNo, Boolean ivr) {
        return acdGroupDao.getAcdGroupsByDeptNo(deptNo, ivr);
    }
}
