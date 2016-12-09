package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.AreaGroupDao;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-5
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AreaGroupDaoImpl extends GenericDaoHibernateBase<AreaGroup,Long> implements AreaGroupDao {

    public AreaGroupDaoImpl()
    {
        super(AreaGroup.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @ReadThroughAssignCache(namespace= CacheNames.CACHE_AREAGROUP, assignedKey = CacheNames.All, expiration=3000)
    public List<AreaGroup> getAllAreaGroup()
    {
        AreaGroup areaGroup=new AreaGroup();
        areaGroup.setActive(true);
        List<AreaGroup> areaGroupList=this.hibernateTemplate.findByExample(areaGroup);

        List<AreaGroup> areaGroupList1=new ArrayList<AreaGroup>();

        for(AreaGroup areaGroupNew : areaGroupList)
        {
            boolean bFind=false;
            for (AreaGroup areaGroupCache: areaGroupList1)
            {
                if(areaGroupCache.getId().equals(areaGroupNew.getId()))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                areaGroupList1.add(areaGroupNew);
            }
        }
        //List<AreaGroup> areaGroupList=this.findList("from AreaGroup where isActive=true");
        return areaGroupList1;
    }

    @ReadThroughSingleCache(namespace = CacheNames.CACHE_AREAGROUP_DETAIL_LIST, expiration=3000)
    public Set<AreaGroupDetail> getAreaGroupDetails(@ParameterValueKeyProvider final AreaGroup areaGroup)
    {
        ///System.out.println("just to fetch AreaGroupDetail:"+areaGroup.getId());
        log.debug("just to fetch AreaGroupDetail"+areaGroup.getId());

        return this.hibernateTemplate.executeWithNativeSession(new HibernateCallback<Set<AreaGroupDetail>>() {
            public Set<AreaGroupDetail> doInHibernate(Session session) throws HibernateException {
                AreaGroup areaGroup1 = (AreaGroup) session.merge(areaGroup);

                if (areaGroup1 != null)
                {
                    if (areaGroup1.getAreaGroupDetails() != null && areaGroup1.getAreaGroupDetails().size() > 0)
                    {
                        areaGroup.setAreaGroupDetails(areaGroup1.getAreaGroupDetails());
                    }
                    else
                    {
                        areaGroup.setAreaGroupDetails(new HashSet<AreaGroupDetail>(0));
                    }
                }
                else
                {
                    areaGroup.setAreaGroupDetails(new HashSet<AreaGroupDetail>(0));
                }
                return areaGroup.getAreaGroupDetails();
            }
        });
    }


    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.model.AreaGroupDetail", expiration=3000)
    public List<Long> getAreaGroupIdFromDetail(@ParameterValueKeyProvider AreaGroupDetail areaGroupDetail)
    {
        List<Long> areaGroupIdList=new ArrayList<Long>();
        if(areaGroupDetail==null || areaGroupDetail.getProvinceId()==null|| areaGroupDetail.getCityId()==null
                ||areaGroupDetail.getCountyId()==null||areaGroupDetail.getAreaId()==null)
        {
            return areaGroupIdList;
        }
        Query query=this.getSession().createQuery("from AreaGroupDetail where provinceId=:provinceId and cityId=:cityId and countyId=:countyId and areaId=:areaId");
        query.setParameter("provinceId",areaGroupDetail.getProvinceId());
        query.setParameter("cityId",areaGroupDetail.getCityId());
        query.setParameter("countyId",areaGroupDetail.getCountyId());
        query.setParameter("areaId",areaGroupDetail.getAreaId());
        List<AreaGroupDetail> areaGroupDetailList= query.list();

        if(areaGroupDetailList!=null)
        {
            for(AreaGroupDetail areaGroupDetail1:areaGroupDetailList)
            {
                if(areaGroupDetail1.getAreaGroup()!=null&&areaGroupDetail1.getAreaGroup().getId()!=null)
                {
                    if(!areaGroupIdList.contains(areaGroupDetail1.getAreaGroup().getId()))
                    {
                        areaGroupIdList.add(areaGroupDetail1.getAreaGroup().getId());
                    }
                }
            }
        }
        /*if(areaGroupDetailList!=null&&areaGroupDetailList.size()>0)
        {
            return areaGroupDetailList.get(0);
        }*/
        return areaGroupIdList;
        //this.find("from AreaGroupDetail where provinceId=:provinceId and cityId=:cityId and countyId=:countyId and areaId=:areaId",new ParameterString("",""),new ParameterString("",""),new ParameterString("",""),new ParameterString("",""));
    }
}
