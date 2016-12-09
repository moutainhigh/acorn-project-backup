package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.dto.CountyDto;

/**
 * 区域服务 
 * @author haoleitao
 *
 */
public interface CountyService {
	/**
	 * 获取城市的区域
	 * @param cityID
	 * @return
	 */
     List<CountyDto> getCountryByCityId(Integer cityID);
     /**
      * 获取的区域
      * @return
      */
     public List<CountyDto> getCountryAll(); 
}
