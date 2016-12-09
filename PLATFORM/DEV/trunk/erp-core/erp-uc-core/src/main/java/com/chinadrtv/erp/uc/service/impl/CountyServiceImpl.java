package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.CountyDao;
import com.chinadrtv.erp.uc.dao.ProvinceDao;
import com.chinadrtv.erp.uc.dto.CityDto;
import com.chinadrtv.erp.uc.dto.CountyDto;
import com.chinadrtv.erp.uc.service.CountyService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
@Service("countyService")
public class CountyServiceImpl implements CountyService {
    @Autowired
    private CountyDao countyDao;

    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.agent.countybyCity",expiration=600000)
	public List<CountyDto> getCountryByCityId(@ParameterValueKeyProvider Integer cityId) {
		List<CountyAll> list= countyDao.getCounties(cityId);
		List<CountyDto> listDto= new ArrayList();
		for(CountyAll pojo:list){
			CountyDto dto=new CountyDto();
			dto.setCountyName(pojo.getCountyname());
			dto.setCountyId(pojo.getCountyid());
			dto.setCountyCode(pojo.getCode());
            dto.setAreaCode(pojo.getAreacode());
			dto.setValue(pojo.getCode()+pojo.getAreacode()+pojo.getCountyname()+AddressConstant.ADDRESS_SPLIT+StringUtil.nullToBlank(pojo.getZipcode())+AddressConstant.ADDRESS_SPLIT+pojo.getProvid()+AddressConstant.ADDRESS_SPLIT+pojo.getCityid());
			listDto.add(dto);
		}
		return listDto;
	}

	@ReadThroughAssignCache(assignedKey = "allCountry", namespace = "com.chinadrtv.erp.uc.CountyService.", expiration = 600000)
	public List<CountyDto> getCountryAll() {
		List<CountyAll> list= countyDao.getAllCounties();
		List<CountyDto> listDto= new ArrayList();
		for(CountyAll pojo:list){
			CountyDto dto=new CountyDto();
			dto.setCountyName(pojo.getCountyname());
			dto.setCountyId(pojo.getCountyid());
			dto.setCountyCode(pojo.getCode());
            dto.setAreaCode(pojo.getAreacode());
			dto.setValue(pojo.getCode()+pojo.getAreacode()+pojo.getCountyname()+AddressConstant.ADDRESS_SPLIT+StringUtil.nullToBlank(pojo.getZipcode())+AddressConstant.ADDRESS_SPLIT+pojo.getProvid()+AddressConstant.ADDRESS_SPLIT+pojo.getCityid());
			listDto.add(dto);
		}
		return listDto;
	}
}
