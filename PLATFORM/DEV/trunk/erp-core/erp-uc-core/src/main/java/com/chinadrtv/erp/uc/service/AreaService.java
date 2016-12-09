package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.uc.dto.AreaDto;


/**
 * 地区服务
 * @author haoleitao
 *
 */
public interface AreaService {
	 /**
	  * 获取 country下的区域
	  * @param countryId :
	  * @return
	  */
	public List<AreaDto> getAreaByCountryId(Integer countryId);

    AreaAll get(Integer areaid);
}
