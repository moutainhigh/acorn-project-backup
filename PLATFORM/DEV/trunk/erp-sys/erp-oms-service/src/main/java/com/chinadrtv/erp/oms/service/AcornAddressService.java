package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.oms.util.AcornCountry;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface AcornAddressService {
    public String findProviceByName(String name);
    public Integer findCityByName(String proviceId,String cityName);
    public List<AcornCountry> findCountysByCity(Integer cityId);
}
