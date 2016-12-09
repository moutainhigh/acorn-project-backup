package com.chinadrtv.erp.tc.core.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.tc.core.dao.AreaGroupDao;
import com.chinadrtv.erp.tc.core.service.AreaGroupService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-10
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AreaGroupServiceImpl extends GenericServiceImpl<AreaGroup,Long> implements AreaGroupService {

    @Autowired
    private AreaGroupDao areaGroupDao;

    @Override
    protected GenericDao<AreaGroup,Long> getGenericDao(){
        return areaGroupDao;
    }

    public AreaGroup getFromAreaGroupDetail(AreaGroupDetail areaGroupDetail)
    {
        List<Long> areaGroupIdList =areaGroupDao.getAreaGroupIdFromDetail(areaGroupDetail);
        if(areaGroupIdList!=null&&areaGroupIdList.size()>0)
        {
            Long areaGroupId=areaGroupIdList.get(0);
            List<AreaGroup> areaGroupList=areaGroupDao.getAllAreaGroup();
            for(AreaGroup areaGroup:areaGroupList)
            {
                if(areaGroup.getId().equals(areaGroupId))
                {
                    return areaGroup;
                }
            }
        }
        return null;
        /*
        List<AreaGroup> areaGroupList=areaGroupDao.getAllAreaGroup();
        for(AreaGroup areaGroup:areaGroupList)
        {
            Set<AreaGroupDetail> areaGroupDetailSet= areaGroupDao.getAreaGroupDetails(areaGroup);
            if(areaGroupDetailSet!=null)
            {
                if(areaGroupDetailSet.contains(areaGroupDetail))
                    return areaGroup;
            }

        }
        return null;*/
    }

    public Set<AreaGroupDetail> getAreaGroupDetails(AreaGroup areaGroup)
    {
        return areaGroupDao.getAreaGroupDetails(areaGroup);
    }

    public List<Long> getAreaGroupsFromDetail(AreaGroupDetail areaGroupDetail)
    {
        return areaGroupDao.getAreaGroupIdFromDetail(areaGroupDetail);
    }
}
