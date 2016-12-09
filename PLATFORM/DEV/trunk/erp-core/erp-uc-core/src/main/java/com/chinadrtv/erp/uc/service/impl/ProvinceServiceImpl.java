package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dao.ProvinceDao;
import com.chinadrtv.erp.uc.dto.ProvinceDto;
import com.chinadrtv.erp.uc.service.ProvinceService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceDao provinceDao;
    @ReadThroughAssignCache(assignedKey = "allProvince", namespace = "com.chinadrtv.erp.uc.CountyService.", expiration = 600000)
	public List<ProvinceDto> getAllProvince() {
		// TODO Auto-generated method stub
		List<Province> list= provinceDao.getAllProvinces();
		List<ProvinceDto> listDto= new ArrayList();
		for(Province pojo:list){
			ProvinceDto dto=new ProvinceDto();
			dto.setProvinceName(pojo.getChinese());
			dto.setProvinceCode(pojo.getCode());
			dto.setProvinceId(pojo.getProvinceid());
			dto.setValue(pojo.getCode()+pojo.getChinese()+AddressConstant.ADDRESS_SPLIT+pojo.getProvinceid());
			dto.setLabel(pojo.getChinese());
			listDto.add(dto);
		}
		return listDto;
	}

    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.agent.Province",expiration=600000)
    public Province get(@ParameterValueKeyProvider String id) {
        return provinceDao.get(id);
    }

}
