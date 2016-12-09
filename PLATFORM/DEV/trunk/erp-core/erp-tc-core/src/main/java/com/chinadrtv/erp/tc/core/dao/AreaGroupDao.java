package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-5
 * To change this template use File | Settings | File Templates.
 */
public interface AreaGroupDao extends GenericDao<AreaGroup, Long> {

    List<AreaGroup> getAllAreaGroup();

    Set<AreaGroupDetail> getAreaGroupDetails(AreaGroup areaGroup);

    List<Long> getAreaGroupIdFromDetail(AreaGroupDetail areaGroupDetail);

}
