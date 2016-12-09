package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dao.AcornAreaDao;
import com.chinadrtv.erp.oms.dao.AcornCityDao;
import com.chinadrtv.erp.oms.dao.AcornCountryDao;
import com.chinadrtv.erp.oms.dao.AcornProvinceDao;
import com.chinadrtv.erp.oms.service.AcornAddressService;
import com.chinadrtv.erp.oms.util.AcornCountry;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class AcornAddressServiceImpl implements AcornAddressService {
    @Autowired
    protected AcornAreaDao acornAreaDao;

    @Autowired
    protected AcornProvinceDao acornProvinceDao;

    @Autowired
    protected AcornCityDao acornCityDao;

    @Autowired
    protected AcornCountryDao acornCountryDao;

    @Override
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.service.impl.AcornAddressServiceImpl.findProviceByName", expiration=3600)
    public String findProviceByName(@ParameterValueKeyProvider String name) {
        Province province=acornProvinceDao.findProvinceByName(name);
        if(province!=null)
            return province.getProvinceid();
        return null;
    }

    @Override
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.service.impl.AcornAddressServiceImpl.findCityByName", expiration=3600)
    public Integer findCityByName(@ParameterValueKeyProvider(order=0) String proviceId, @ParameterValueKeyProvider(order=1) String cityName) {
        CityAll cityAll=acornCityDao.findCityByName(proviceId,cityName);
        if(cityAll!=null)
            return cityAll.getCityid();
        return null;
    }

    @Override
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.service.impl.AcornAddressServiceImpl.findCountysByCity", expiration=3600)
    public List<AcornCountry> findCountysByCity(@ParameterValueKeyProvider Integer cityId) {
        List<CountyAll> countyAllList=acornCountryDao.findCountysByCity(cityId);
        if(countyAllList!=null)
        {
            List<AcornCountry> list=new ArrayList<AcornCountry>();
            for(CountyAll countyAll:countyAllList)
            {
                list.add(new AcornCountry(countyAll.getCountyid(),countyAll.getCountyname()));
            }
            return list;
        }
        return null;
    }
}
