package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.CityDao;
import com.chinadrtv.erp.uc.dto.CityDto;
import com.chinadrtv.erp.uc.dto.ProvinceDto;
import com.chinadrtv.erp.uc.service.CityService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
@Service("cityService")
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.agent.citybyProvinceId",expiration=600000)
	public List<CityDto> getCityByProvinceId(@ParameterValueKeyProvider String provinceId) {
		List<CityAll> list= cityDao.getCities(provinceId);
		List<CityDto> listDto= new ArrayList();
		for(CityAll pojo:list){
			CityDto dto=new CityDto();
			dto.setCityName(pojo.getCityname());
			dto.setCityId(pojo.getCityid());
			dto.setCityCode(pojo.getCode());
			dto.setAreaCode(pojo.getAreacode());
			dto.setProvid(pojo.getProvid());
			dto.setValue(pojo.getCode()+pojo.getAreacode()+pojo.getCityname()+AddressConstant.ADDRESS_SPLIT+StringUtil.nullToBlank(pojo.getZipcode())+AddressConstant.ADDRESS_SPLIT+pojo.getProvid());
			listDto.add(dto);
		}
		return listDto;
	}
	
    @ReadThroughAssignCache(assignedKey = "allCity", namespace = "com.chinadrtv.erp.uc.CountyService.", expiration = 600000)
	public List<CityDto> getCityAll() {
		List<CityAll> list= cityDao.getAllCities();
		List<CityDto> listDto= new ArrayList();
		for(CityAll pojo:list){
			CityDto dto=new CityDto();
			dto.setCityName(pojo.getCityname());
			dto.setCityId(pojo.getCityid());
			dto.setCityCode(pojo.getCode());
			dto.setAreaCode(pojo.getAreacode());
			dto.setProvid(pojo.getProvid());
			dto.setValue(pojo.getCode()+pojo.getAreacode()+pojo.getCityname()+AddressConstant.ADDRESS_SPLIT+StringUtil.nullToBlank(pojo.getZipcode())+AddressConstant.ADDRESS_SPLIT+pojo.getProvid());
			listDto.add(dto);
		}
		return listDto;
	}

    @Override
    public CityAll getByAreaCode(String areacode) {
        return cityDao.getByAreaCode(areacode);
    }

    @Override
    public CityAll get(Integer id) {
        return cityDao.get(id);
    }

}
