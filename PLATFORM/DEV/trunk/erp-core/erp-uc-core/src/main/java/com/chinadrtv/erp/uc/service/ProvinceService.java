package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.uc.dto.ProvinceDto;

public interface ProvinceService {
      public List<ProvinceDto> getAllProvince();

    public Province get(String id);
}
