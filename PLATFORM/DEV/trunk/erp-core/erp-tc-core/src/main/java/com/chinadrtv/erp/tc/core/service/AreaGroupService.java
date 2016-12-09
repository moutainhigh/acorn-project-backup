package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
public interface AreaGroupService extends GenericService<AreaGroup,Long> {
    AreaGroup getFromAreaGroupDetail(AreaGroupDetail areaGroupDetail);

    Set<AreaGroupDetail> getAreaGroupDetails(AreaGroup areaGroup);

    List<Long> getAreaGroupsFromDetail(AreaGroupDetail areaGroupDetail);
}
