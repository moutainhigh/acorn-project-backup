package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.AreaDao;
import com.chinadrtv.erp.uc.dto.AreaDto;
import com.chinadrtv.erp.uc.dto.CountyDto;
import com.chinadrtv.erp.uc.service.AreaService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
@Service("areaService")
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.agent.areabyCounty",expiration=60000)
	public List<AreaDto> getAreaByCountryId(@ParameterValueKeyProvider Integer countryId) {
	
		List<AreaAll> list= areaDao.getAreaByCountryId(countryId);
		List<AreaDto> listDto= new ArrayList();
		for(AreaAll pojo:list){
			AreaDto dto=new AreaDto();
			dto.setAreaName(pojo.getAreaname());
			dto.setAreaId(pojo.getAreaid());
			dto.setAreaCode(pojo.getCode());
			dto.setValue(pojo.getCode()+pojo.getAreaname()+AddressConstant.ADDRESS_SPLIT+StringUtil.nullToBlank(pojo.getZipcode())+AddressConstant.ADDRESS_SPLIT+pojo.getAreaid());
			listDto.add(dto);
		}
		
		return listDto;
	}

    @Override
    public AreaAll get(Integer areaid) {
        return areaDao.get(areaid);
    }

}
