package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.uc.dto.CityDto;
/**
 * 城市服务
 * @author haoleitao
 *
 */
public interface CityService {
      public List<CityDto> getCityByProvinceId(String provinceId);

    public CityAll get(Integer id);

    public List<CityDto> getCityAll();

    CityAll getByAreaCode(String areacode);
}
