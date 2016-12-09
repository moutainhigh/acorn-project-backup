package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.uc.dto.CityDto;

import java.util.List;

/**
 * ACD组服务
 * @author gaudi
 */
public interface AcdGroupService {
    List<AcdGroup> getAllAcdGroups();
    List<AcdGroup> getAcdGroupsByDeptNo(String deptNo, Boolean ivr);
}
